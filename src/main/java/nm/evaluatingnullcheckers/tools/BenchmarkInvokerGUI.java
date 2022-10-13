package nm.evaluatingnullcheckers.tools;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.*;
import java.lang.annotation.Annotation;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * Utility class for testing benchmarking tools
 */
public class BenchmarkInvokerGUI {

    private final ArrayList<Class<?>> annotationClasses;
    private HashMap<String, Boolean> enabledBenches;
    private JTable benchSortTable;
    private boolean blockingBench = false;
    private int lastBenchIndex = -1;
    private HashMap<String, Boolean> enabledCheckers;
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
    private final JFrame masterFrame;


    public BenchmarkInvokerGUI() {
        benchmarkThread = new Thread();
        annotationClasses = new ArrayList<>();
        annotationClasses.addAll(Arrays.asList(new Class<?>[]{BenchmarkAnnotations.Annotation.class,
                BenchmarkAnnotations.AnalysisScope.class, BenchmarkAnnotations.VariableScope.class,
                BenchmarkAnnotations.VariableType.class, BenchmarkAnnotations.ExpectedNPE.class,BenchmarkAnnotations.Style.class}));
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
        logArea.setLineWrap(true);
        logScrollPane = new JScrollPane(logArea);
        logScrollPane.setBorder(BorderFactory.createTitledBorder("Log"));
        logScrollPane.setVisible(false);
        logStream = new PrintStream(new OutputStream() {
            @Override
            public void write(int b) {
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

    private JPanel configPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.weightx = 1.0;
        constraints.weighty = 1.0;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 0;
        panel.add(configSortPanel(), constraints);
        constraints.weightx = 0.5;
        constraints.weighty = 1.0;
        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.BOTH;
        panel.add(logPane(), constraints);
        return panel;
    }

    private JPanel controls() {
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
        constraints.anchor = GridBagConstraints.CENTER;
        constraints.fill = GridBagConstraints.VERTICAL;
        constraints.gridx = 1;
        constraints.gridy = 0;
        JButton toggleLog = new JButton("Show/Hide Log");
        toggleLog.addActionListener(e -> {
            logScrollPane.setVisible(!logScrollPane.isVisible());
            masterFrame.getContentPane().revalidate();
            masterFrame.getContentPane().repaint();
        });
        panel.add(toggleLog, constraints);
        return panel;
    }

    private JButton executeButton() {
        executeBenchmark = new JButton("Evaluate Null Checkers \u2713");
        executeBenchmark.addActionListener(e -> {
            if (!executing && validConfiguration && !benchmarkThread.isAlive()) {
                executing = true;
                Runnable r = () -> {
                    invokeBenchmark();
                    executing = false;
                    validate();
                };
                benchmarkThread = new Thread(r);
                benchmarkThread.start();
                validate();
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
                            benchWriter.write("nm/evaluatingnullcheckers/benchmarks/" + e + ".java" + "\n");
                        } catch (IOException ex) {
                            ex.printStackTrace(logStream);
                        }
                    }
            );
            benchWriter.close();
            FileWriter checkerWriter = new FileWriter(checkerList);
            enabledCheckers.keySet().stream().filter(e -> enabledCheckers.get(e)).forEach(
                    e -> {
                        try {
                            checkerWriter.write(e + "\n");
                        } catch (IOException ex) {
                            ex.printStackTrace(logStream);
                        }
                    }
            );
            checkerWriter.close();
            BenchmarkInvokerCLI.auxilliaryMain(benchmarkList.getAbsolutePath(),
                    checkerList.getAbsolutePath(),
                    logFolder,
                    timestamp,
                    //Results outputting will be handled by GUI
                    null,
                    logStream);

            enabledFormats.keySet().stream().filter(e -> enabledFormats.get(e)).forEach(
                    e -> {
                        try {
                            ResultsOutputHandler.handleOutput(logFolder, timestamp, e);
                            logStream.println("Results output available at: " + logFolder + "/results" + timestamp + "." + e.toLowerCase());
                        }catch(IllegalArgumentException ex){
                            logStream.println(ex.getMessage());
                        }
                    }
            );

        } catch (IOException e) {
            e.printStackTrace(logStream);
        } catch (NoClassDefFoundError e) {
            e.printStackTrace(logStream);
        } finally {
            //Cleanup
            benchmarkList.delete();
            checkerList.delete();
        }

    }

    private void validate() {
        validConfiguration = false;
        enabledBenches.values().stream().filter(b -> b).findFirst()
                .flatMap(b -> enabledCheckers.values().stream().filter(c -> c).findFirst())
                .flatMap(c -> enabledFormats.values().stream().filter(f -> f).findFirst())
                .ifPresent((f) -> validConfiguration = !executing);
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
                .getMetadata(new ArrayList<>(InvokerUtils.getAllBenchmarkClasses().stream()
                        .map(c -> c.getSimpleName()).collect(Collectors.toList())));
        enabledBenches = new HashMap<>();
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
        String[] columnNames = Stream.concat(Arrays.stream(new String[]{"Enabled", "Name"}),
                annotationClasses.stream().map(Class::getSimpleName)).collect(Collectors.toList()).toArray(new String[]{});
        benchSortTable = new JTable(data, columnNames);
        benchSortTable.setModel(new DefaultTableModel(data, columnNames) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        });
        benchSortTable.setAutoCreateRowSorter(true);
        benchSortTable.getTableHeader().setReorderingAllowed(false);

        benchSortTable.getSelectionModel().addListSelectionListener(e -> {
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
        enabledCheckers = new HashMap<>();
        InvokerUtils.getAvailableCheckers().stream().forEach(c -> enabledCheckers.put(c, true));
        String[] columnNames = new String[]{"Enabled", "Name"};
        String[][] data = new String[InvokerUtils.getAvailableCheckers().size()][2];
        for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
                if (j == 0) {
                    data[i][j] = enabledCheckers.get(InvokerUtils.getAvailableCheckers().get(i)) ? "\u2713" : "\u2613";
                } else {
                    data[i][j] = InvokerUtils.getAvailableCheckers().get(i);
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
        checkerSortTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && !blockingChecker) {
                blockingChecker = true;
                if (e.getFirstIndex() == lastCheckerIndex) {
                    lastCheckerIndex = e.getLastIndex();
                } else {
                    lastCheckerIndex = e.getFirstIndex();
                }
                String checkerName = checkerSortTable.getValueAt(lastCheckerIndex, 1).toString();
                enabledCheckers.put(checkerName, !enabledCheckers.get(checkerName));
                updateCheckerTableData();
                blockingChecker = false;
            }
        });

        return new JScrollPane(checkerSortTable);
    }

    private void updateCheckerTableData() {
        DefaultTableModel sortTableModel = (DefaultTableModel) checkerSortTable.getModel();
        for (int i = 0; i < sortTableModel.getRowCount(); i++) {
            sortTableModel.setValueAt(enabledCheckers.get(((String) sortTableModel.getValueAt(i, 1))) ? "\u2713" : "\u2613", i, 0);
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
        selectAll.addActionListener(actionEvent -> {
            enabledBenches.keySet().forEach(e -> enabledBenches.put(e, true));
            updateBenchmarkTableData();
        });
        buttonPanel.add(selectAll, c);
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton clearSelect = new JButton("Disable All");
        clearSelect.addActionListener(actionEvent -> {
            enabledBenches.keySet().forEach(e -> enabledBenches.put(e, false));
            updateBenchmarkTableData();
        });
        buttonPanel.add(clearSelect, c);
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.anchor = GridBagConstraints.SOUTH;
        buttonPanel.add(filterBenchmarks(), c);
        return buttonPanel;

    }

    private JPanel filterBenchmarks() {
        JPanel filterPanel = new JPanel();
        JComboBox<Object> filterBy = new JComboBox<>(
                IntStream.range(0, benchSortTable.getColumnCount() - 1).mapToObj(i -> benchSortTable.getColumnName(i + 1)).toArray()
        );
        filterPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), "Filter"));
        filterPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        JTextField filter = new JTextField();
        filter.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                //filterBenchmarks((str)->str.toLowerCase().contains(filter.getText().toLowerCase()),filterBy.getSelectedIndex() + 1);
            }

            @Override
            public void keyPressed(KeyEvent e) {
                filterBenchmarks((str) -> str.toLowerCase().contains(filter.getText().toLowerCase()), filterBy.getSelectedIndex() + 1);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                filterBenchmarks((str) -> str.toLowerCase().contains(filter.getText().toLowerCase()), filterBy.getSelectedIndex() + 1);
            }
        });
        filterPanel.add(filter, c);
        c.weightx = 1.0;
        c.weighty = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        filterPanel.add(filterBy, c);
        filterBy.addPropertyChangeListener(new PropertyChangeListener() {
            @Override
            public void propertyChange(PropertyChangeEvent evt) {
                filterBenchmarks((str)->str.toLowerCase().contains(filter.getText().toLowerCase()),filterBy.getSelectedIndex() + 1);
            }
        });
        return filterPanel;

    }

    private void filterBenchmarks(Predicate<String> pred, int column) {
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(benchSortTable.getModel());
        sorter.setRowFilter(new RowFilter<>() {
            @Override
            public boolean include(Entry<? extends TableModel, ? extends Integer> entry) {
                int row = entry.getIdentifier();
                return pred.test(entry.getModel().getValueAt(row, column).toString());
            }
        });
        benchSortTable.setRowSorter(sorter);
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
        selectAll.addActionListener(actionEvent -> {
            enabledCheckers.keySet().forEach(e -> enabledCheckers.put(e, true));
            updateCheckerTableData();
        });
        buttonPanel.add(selectAll, c);
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton clearSelect = new JButton("Disable All");
        clearSelect.addActionListener(actionEvent -> {
            enabledCheckers.keySet().forEach(e -> enabledCheckers.put(e, false));
            updateCheckerTableData();
        });
        buttonPanel.add(clearSelect, c);

        return buttonPanel;

    }

    private JScrollPane formatSortTable() {
        enabledFormats = new HashMap<>();
        List<String> formats = new ArrayList<>();
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
        formats = formats.stream().sorted(Comparator.naturalOrder()).collect(Collectors.toList());
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
        formatSortTable.getSelectionModel().addListSelectionListener(e -> {
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
        selectAll.addActionListener(actionEvent -> {
            enabledFormats.keySet().forEach(e -> enabledFormats.put(e, true));
            updateFormatTableData();
        });
        buttonPanel.add(selectAll, c);
        c.weighty = 1.0;
        c.weightx = 1.0;
        c.gridx = 0;
        c.gridy = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        JButton clearSelect = new JButton("Disable All");
        clearSelect.addActionListener(actionEvent -> {
            enabledFormats.keySet().forEach(e -> enabledFormats.put(e, false));
            updateFormatTableData();
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
        new BenchmarkInvokerGUI();
    }

}
