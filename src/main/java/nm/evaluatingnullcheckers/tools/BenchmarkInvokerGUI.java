package nm.evaluatingnullcheckers.tools;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;
import org.apache.maven.shared.invoker.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class BenchmarkInvokerGUI {

    private ArrayList<Class<?>> annotationClasses;
    private HashMap<String, Boolean> enabledBenches;
    private JTable benchSortTable;
    private boolean blockingBench = false;
    private int lastBenchIndex = -1;
    private HashMap<InvokerUtils.KnownChecker, Boolean> enabledCheckers;
    private JTable checkerSortTable;
    private boolean blockingChecker = false;
    private int lastCheckerIndex = -1;
    private HashMap<String, Boolean> enabledFormats;
    private JTable formatSortTable;
    private boolean blockingFormat = false;
    private int lastFormatIndex = -1;
    private JButton executeBenchmark;
    private boolean validConfiguration = true;
    private boolean executing = false;
    private JTextArea logArea;
    private JScrollPane logScrollPane;
    private PrintStream logStream;
    private static final SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd'T'HHmmss");
    private static final SimpleDateFormat logTimeFormat = new SimpleDateFormat("HH:mm:ss");
    private Thread benchmarkThread;
    private JFrame masterFrame;


    public BenchmarkInvokerGUI() {
        benchmarkThread = new Thread();
        annotationClasses = new ArrayList<Class<?>>();
        annotationClasses.addAll(Arrays.asList(new Class<?>[]{BenchmarkAnnotations.Annotation.class,
                BenchmarkAnnotations.AnalysisScope.class, BenchmarkAnnotations.VariableScope.class,
                BenchmarkAnnotations.VariableType.class, BenchmarkAnnotations.ExpectedNPE.class}));
        masterFrame = new JFrame();
        Container pane = masterFrame.getContentPane();
        setupGUI(pane);
        masterFrame.setSize(500, 500);
        masterFrame.setVisible(true);
        masterFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
    }

    private void setupGUI(Container pane) {
        pane.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        pane.add(configPanel(), constraints);
        constraints.weightx = 1.0;
        constraints.weighty = 0.1;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        pane.add(controls(), constraints);

    }

    private JScrollPane logPane() {
        logArea = new JTextArea();
        logArea.setEditable(false);
        logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(BorderFactory.createTitledBorder("Log"));
        logScrollPane.setVisible(false);
        logStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) throws IOException {
                logArea.append(new String(new byte[]{(byte) b}));
            }
        }) {
            @Override
            public void println(String x) {
                super.println("[" + logTimeFormat.format(new Timestamp(new Date().getTime())) + "] " + x);
            }
        };
        System.setOut(logStream);
        return logScrollPane;
    }

    private JPanel configPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(configSortPanel(), constraints);
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(logPane(), constraints);
        return panel;
    }

    private JPanel controls(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(executeButton(), constraints);
        constraints.weightx = 1.0;
        constraints.weightx = 1.0;
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        JButton toggleLog = new JButton("Show/Hide Log");
        toggleLog.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                logScrollPane.setVisible(!logScrollPane.isVisible());
                masterFrame.getContentPane().revalidate();
                masterFrame.getContentPane().repaint();
            }
        });
        panel.add(toggleLog,constraints);
        return panel;
    }

    private JButton executeButton() {
        executeBenchmark = new JButton("Evaluate Null Checkers \u2713");
        executeBenchmark.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!executing && validConfiguration && !benchmarkThread.isAlive()) {
                    executing = true;
                    Runnable r = new Runnable() {
                        @Override
                        public void run() {
                            invokeBenchmark();
                            executing = false;
                            validate();
                        }
                    };
                    benchmarkThread = new Thread(r);
                    benchmarkThread.start();
                    validate();
                }
            }
        });
        return executeBenchmark;
    }

    private void invokeBenchmark() {
        File benchmarkList = new File("benchmarklist.txt");
        File checkerList = new File("checkerlist.txt");
        try {
            String timestamp = format.format(new Timestamp(new Date().getTime()));
            String logFolder = "log/log" + timestamp;
            FileWriter benchWriter = new FileWriter(benchmarkList);
            enabledBenches.keySet().stream().filter(e -> enabledBenches.get(e)).forEach(
                    e -> {
                        try {
                            benchWriter.write("nm/evaluatingnullcheckers/benchmarks/" +e +".java" + "\n");
                        } catch (IOException ex) {
                        }
                    }
            );
            benchWriter.close();
            FileWriter checkerWriter = new FileWriter(checkerList);
            enabledCheckers.keySet().stream().filter(e -> enabledCheckers.get(e)).forEach(
                    e -> {
                        try {
                            checkerWriter.write(e.name().toLowerCase() + "\n");
                        } catch (IOException ex) {
                        }
                    }
            );
            checkerWriter.close();
            Process p = new ProcessBuilder(System.getProperty("user.dir") + "/run.sh", "-b " + "benchmarklist.txt", "-c " + "checkerlist.txt",
                    "-l " + logFolder).start();
            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            try {
                for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                    logStream.println(line);
                }
            } catch (IOException e) {
                e.printStackTrace(logStream);
            };
            p.waitFor();
            logStream.println("Raw checker output available at: " + logFolder + "/checkeroutput");
            InvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File(System.getProperty("user.dir") + "/pom.xml"));
            request.setGoals(Arrays.asList("clean", "compile"));
            request.addArg("-P compiletools");
            request.addArg("-q");
            request.setBatchMode(true);
            Invoker invoker = new DefaultInvoker();
            invoker.execute(request);
            String[] parserArgs = {logFolder, logFolder + "/checkeroutput" + timestamp + ".json"};
            CheckerOutputParser.main(parserArgs);
            logStream.println(
                    "Parsed checker output available at: " + logFolder + "/checkeroutput" + timestamp + ".json");
            String[] evaluatorArgs = {logFolder + "/checkeroutput" + timestamp + ".json",
                    logFolder + "/results" + timestamp + ".json"};
            CheckerEvaluator.main(evaluatorArgs);
            logStream.println("Evaluator output available at: " + logFolder + "/results" + timestamp + ".json");
            HashMap<InvokerUtils.KnownChecker, CheckerResult> results = InvokerUtils
                    .deserialiseResults(new File(logFolder + "/results" + timestamp + ".json"));
            enabledFormats.keySet().stream().filter(e -> enabledFormats.get(e)).forEach(
                    e -> {
                        try {
                            Class<? extends ResultsOutput> outclass = (Class<? extends ResultsOutput>) Class.forName("nm.evaluatingnullcheckers.tools.ResultsOutput" + e);
                            ResultsOutput o = outclass.getDeclaredConstructor().newInstance();
                            String outFilePath = logFolder + "/resultsoutput" + timestamp + "." + e.toLowerCase();
                            File outFile = new File(outFilePath);
                            FileWriter outWriter = new FileWriter(outFile);
                            try {
                                outWriter.write((String) o.outputResults(results));
                                outWriter.close();
                            } catch (ClassCastException ex) {
                                try {
                                    outWriter.close();
                                    XSSFWorkbook workbook = (XSSFWorkbook) o.outputResults(results);
                                    FileOutputStream fOut = new FileOutputStream(outFile);
                                    workbook.write(fOut);
                                    fOut.close();
                                } catch (ClassCastException ex2) {
                                    //Additional Formats go here...
                                }
                            }
                            logStream.println(
                                    "Results available at: " + outFilePath);
                        } catch (ClassNotFoundException ex) {
                            ex.printStackTrace(logStream);
                        } catch (InvocationTargetException ex) {
                            ex.printStackTrace(logStream);
                        } catch (InstantiationException ex) {
                            ex.printStackTrace(logStream);
                        } catch (IllegalAccessException ex) {
                            ex.printStackTrace(logStream);
                        } catch (NoSuchMethodException ex) {
                            ex.printStackTrace(logStream);
                        } catch (IOException ex) {
                            ex.printStackTrace(logStream);
                        }
                    }
            );

        } catch (IOException e) {
            e.printStackTrace(logStream);
        } catch (InterruptedException e) {
            e.printStackTrace(logStream);
        } catch (MavenInvocationException e) {
            e.printStackTrace(logStream);
        } catch(NoClassDefFoundError e){
            e.printStackTrace(logStream);
        } finally{
            //Cleanup
            benchmarkList.delete();
            checkerList.delete();
        }

    }

    private void validate() {
        validConfiguration = false;
        enabledBenches.values().stream().filter(b -> b).findFirst().ifPresent(
                (b) -> enabledCheckers.values().stream().filter(c -> c).findFirst().ifPresent(
                        (c) -> enabledFormats.values().stream().filter(f -> f).findFirst().ifPresent(
                                (f) -> validConfiguration = !executing
                        )
                )
        );
        executeBenchmark.setText("Evaluate Null Checkers " + (validConfiguration ? "\u2713" : "\u2613"));
    }

    private JPanel configSortPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(benchmarkPanel(), constraints);
        constraints.weightx = 1.0;
        constraints.weighty = 0.2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;
        panel.add(checkerPanel(), constraints);
        constraints.weightx = 1.0;
        constraints.weighty = 0.2;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 2;
        panel.add(formatPanel(), constraints);
        return panel;
    }

    private JPanel benchmarkPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Benchmarks"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(benchmarkSortPanel(), constraints);
        constraints.weightx = 0.1;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(benchmarkListButtons(), constraints);

        return panel;
    }

    private JPanel checkerPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Checkers"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(checkerSortTable(), constraints);
        constraints.weightx = 0.1;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(checkerListButtons(), constraints);
        return panel;

    }

    private JPanel formatPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Formats"));
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(formatSortTable(), constraints);
        constraints.weightx = 0.1;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 1;
        constraints.gridy = 0;
        panel.add(formatListButtons(), constraints);
        return panel;
    }

    private JScrollPane benchmarkSortPanel() {
        HashMap<String, ArrayList<Annotation>> metadata = InvokerUtils
                .getMetadata(new ArrayList<String>(BenchmarkSpace.getAllBenchmarkClasses().stream()
                        .map(c -> c.getSimpleName()).collect(Collectors.toList())));
        enabledBenches = new HashMap<String, Boolean>();
        metadata.keySet().stream().forEach(e -> enabledBenches.put(e, true));

        List<List<String>> listValuesHorizontal = metadata.keySet().stream()
                .map(name -> Stream.concat(Arrays.stream(new String[]{name}),
                                metadata.get(name).stream()
                                        .sorted((a1, a2) -> annotationClasses.indexOf(a1.annotationType())
                                                - annotationClasses.indexOf(a2.annotationType()))
                                        .map(a -> a.annotationType().getSimpleName()))
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
        String[][] data = new String[listValuesHorizontal.size()][listValuesHorizontal.get(0).size() + 1];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (j == 0) {
                    data[i][j] = enabledBenches.get(listValuesHorizontal.get(i).get(0)) ? "\u2713" : "\u2613";
                } else {
                    data[i][j] = listValuesHorizontal.get(i).get(j - 1);
                }
            }
        }
        String[] columnNames = Stream.concat(Arrays.asList(new String[]{"Enabled", "Name"}).stream(),
                annotationClasses.stream().map(c -> c.getSimpleName())).collect(Collectors.toList()).toArray(new String[]{});
        benchSortTable = new JTable(data, columnNames);
        benchSortTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        benchSortTable.setAutoCreateRowSorter(true);
        benchSortTable.getTableHeader().setReorderingAllowed(false);

        benchSortTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !blockingBench) {
                    blockingBench = true;
                    if (e.getFirstIndex() == lastBenchIndex) {
                        lastBenchIndex = e.getLastIndex();
                    } else {
                        lastBenchIndex = e.getFirstIndex();
                    }
                    String benchName = benchSortTable.getValueAt(lastBenchIndex, 1).toString();
                    enabledBenches.put(benchName, !enabledBenches.get(benchName));
                    updateBenchmarkTableData();
                    blockingBench = false;
                }
            }
        });
        return new JScrollPane(benchSortTable);
    }

    private void updateBenchmarkTableData() {
        DefaultTableModel sortTableModel = (DefaultTableModel) benchSortTable.getModel();
        for (int i = 0; i < sortTableModel.getRowCount(); i++) {
            sortTableModel.setValueAt(enabledBenches.get((String) sortTableModel.getValueAt(i, 1)) ? "\u2713" : "\u2613", i, 0);
        }
        benchSortTable.setModel(sortTableModel);
        sortTableModel.fireTableDataChanged();
        validate();
    }

    private JScrollPane checkerSortTable() {
        enabledCheckers = new HashMap<InvokerUtils.KnownChecker, Boolean>();
        Arrays.stream(InvokerUtils.KnownChecker.values()).forEach(c -> enabledCheckers.put(c, true));
        String[] columnNames = new String[]{"Enabled", "Name"};
        String[][] data = new String[InvokerUtils.KnownChecker.values().length][2];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (j == 0) {
                    data[i][j] = enabledCheckers.get(InvokerUtils.KnownChecker.values()[i]) ? "\u2713" : "\u2613";
                } else {
                    data[i][j] = InvokerUtils.KnownChecker.values()[i].name();
                }
            }
        }
        checkerSortTable = new JTable(data, columnNames);
        checkerSortTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        checkerSortTable.setAutoCreateRowSorter(true);
        checkerSortTable.getTableHeader().setReorderingAllowed(false);
        checkerSortTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !blockingChecker) {
                    blockingChecker = true;
                    if (e.getFirstIndex() == lastCheckerIndex) {
                        lastCheckerIndex = e.getLastIndex();
                    } else {
                        lastCheckerIndex = e.getFirstIndex();
                    }
                    String checkerName = checkerSortTable.getValueAt(lastCheckerIndex, 1).toString();
                    InvokerUtils.KnownChecker checker = InvokerUtils.KnownChecker.valueOf(checkerName);
                    enabledCheckers.put(checker, !enabledCheckers.get(checker));
                    updateCheckerTableData();
                    blockingChecker = false;
                }
            }
        });

        return new JScrollPane(checkerSortTable);
    }

    private void updateCheckerTableData() {
        DefaultTableModel sortTableModel = (DefaultTableModel) checkerSortTable.getModel();
        for (int i = 0; i < sortTableModel.getRowCount(); i++) {
            sortTableModel.setValueAt(enabledCheckers.get(InvokerUtils.KnownChecker.valueOf((String) sortTableModel.getValueAt(i, 1))) ? "\u2713" : "\u2613", i, 0);
        }
        checkerSortTable.setModel(sortTableModel);
        sortTableModel.fireTableDataChanged();
        validate();
    }

    private JPanel benchmarkListButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton selectAll = new JButton("Enable All");
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enabledBenches.keySet().forEach(e -> enabledBenches.put(e, true));
                updateBenchmarkTableData();
            }
        });
        buttonPanel.add(selectAll, c);
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton clearSelect = new JButton("Disable All");
        clearSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enabledBenches.keySet().forEach(e -> enabledBenches.put(e, false));
                updateBenchmarkTableData();
            }
        });
        buttonPanel.add(clearSelect, c);
        return buttonPanel;

    }

    private JPanel checkerListButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton selectAll = new JButton("Enable All");
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enabledCheckers.keySet().forEach(e -> enabledCheckers.put(e, true));
                updateCheckerTableData();
            }
        });
        buttonPanel.add(selectAll, c);
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton clearSelect = new JButton("Disable All");
        clearSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enabledCheckers.keySet().forEach(e -> enabledCheckers.put(e, false));
                updateCheckerTableData();
            }
        });
        buttonPanel.add(clearSelect, c);

        return buttonPanel;

    }

    private JScrollPane formatSortTable() {
        enabledFormats = new HashMap<String, Boolean>();
        List<String> formats = new ArrayList<String>();
        String packageName = "nm.evaluatingnullcheckers.tools";
        InputStream stream = ClassLoader.getSystemClassLoader().getResourceAsStream(packageName.replaceAll("[.]", "/"));
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        try {
            for (String str = reader.readLine(); str != null; str = reader.readLine()) {
                if (str.contains("ResultsOutput") && !str.contains("ResultsOutput.") && !str.contains("$") && !str.contains("Handler")) {
                    String format = str.split("ResultsOutput")[1].replace(".class", "");
                    formats.add(format);
                    enabledFormats.put(format, false);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (enabledFormats.containsKey("CSV")) {
            enabledFormats.put("CSV", true);
        }
        formats = formats.stream().sorted((e1, e2) -> e1.compareTo(e2)).collect(Collectors.toList());
        String[] columnNames = new String[]{"Enabled", "Format"};
        String[][] data = new String[enabledFormats.keySet().size()][2];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (j == 0) {
                    data[i][j] = enabledFormats.get(formats.get(i)) ? "\u2713" : "\u2613";
                } else {
                    data[i][j] = formats.get(i);
                }
            }
        }
        formatSortTable = new JTable(data, columnNames);
        formatSortTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        formatSortTable.setAutoCreateRowSorter(true);
        formatSortTable.getTableHeader().setReorderingAllowed(false);
        formatSortTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && !blockingFormat) {
                    blockingFormat = true;
                    if (e.getFirstIndex() == lastFormatIndex) {
                        lastFormatIndex = e.getLastIndex();
                    } else {
                        lastFormatIndex = e.getFirstIndex();
                    }
                    String format = formatSortTable.getValueAt(lastFormatIndex, 1).toString();
                    enabledFormats.put(format, !enabledFormats.get(format));
                    updateFormatTableData();
                    blockingFormat = false;
                }
            }
        });

        return new JScrollPane(formatSortTable);
    }

    private JPanel formatListButtons() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton selectAll = new JButton("Enable All");
        selectAll.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enabledFormats.keySet().forEach(e -> enabledFormats.put(e, true));
                updateFormatTableData();
            }
        });
        buttonPanel.add(selectAll, c);
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton clearSelect = new JButton("Disable All");
        clearSelect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                enabledFormats.keySet().forEach(e -> enabledFormats.put(e, false));
                updateFormatTableData();
            }
        });
        buttonPanel.add(clearSelect, c);

        return buttonPanel;

    }

    private void updateFormatTableData() {
        DefaultTableModel sortTableModel = (DefaultTableModel) formatSortTable.getModel();
        for (int i = 0; i < sortTableModel.getRowCount(); i++) {
            sortTableModel.setValueAt(enabledFormats.get((String) sortTableModel.getValueAt(i, 1)) ? "\u2713" : "\u2613", i, 0);
        }
        formatSortTable.setModel(sortTableModel);
        sortTableModel.fireTableDataChanged();
        validate();
    }


    public static void main(String[] arg) {
        BenchmarkInvokerGUI b = new BenchmarkInvokerGUI();
    }

}
