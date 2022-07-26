package nm.evaluatingnullcheckers.tools;

import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.WindowConstants;

import nm.evaluatingnullcheckers.annotations.BenchmarkAnnotations;

public class BenchmarkInvokerGUI {

	private ArrayList<Class<?>> annotationClasses;

	public BenchmarkInvokerGUI() {
		annotationClasses = new ArrayList<Class<?>>();
		annotationClasses.addAll(Arrays
				.asList(new Class<?>[] { BenchmarkAnnotations.Annotation.class,
						BenchmarkAnnotations.AnalysisScope.class,
						BenchmarkAnnotations.VariableScope.class,
						BenchmarkAnnotations.VariableType.class, 
						BenchmarkAnnotations.ExpectedNPE.class }));
		JFrame f = new JFrame();
		f.add(sortPanel(0, 0, 1000, 1000));
		f.setSize(1000, 1000);
		f.setLayout(null);
		f.setVisible(true);
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	}

	private JPanel sortPanel(int x, int y, int width, int height) {
		int buttonheight = 40;
		JPanel sort = new JPanel();
		sort.setLayout(null);
		sort.setBounds(x, y, width, height);

		JPanel buttonPanel = new JPanel();
		buttonPanel.setLayout(null);
		buttonPanel.setBounds(x, y, width, buttonheight);

		ArrayList<String> buttonNames = new ArrayList<String>(Arrays.asList(new String[] { "Name" }));
		buttonNames.addAll(annotationClasses.stream().map(c -> c.getSimpleName()).collect(Collectors.toList()));
		int itemwidth = width / buttonNames.size();
		List<JButton> buttons = buttonNames.stream()
				.map(name -> makeButton(name, x + buttonNames.indexOf(name) * itemwidth, y, itemwidth, buttonheight))
				.collect(Collectors.toList());
		for (JButton b : buttons) {
			buttonPanel.add(b);
		}
		sort.add(buttonPanel);

		JScrollPane listPanel = new JScrollPane();
		HashMap<String, ArrayList<Annotation>> metadata = InvokerUtils.getMetadata(new ArrayList<String>(
				BenchmarkSpace.getAllBenchmarkClasses().stream().map(c -> c.getSimpleName()).collect(Collectors.toList())));
		List<List<String>> listValues = buttonNames.stream()
				.map(index -> metadata.keySet().stream()
						.map(name -> Stream.concat(Arrays.asList(new String[] { name }).stream(),
								metadata.get(name).stream()
										.sorted((a1, a2) -> annotationClasses.indexOf(a1.annotationType())
												- annotationClasses.indexOf(a2.annotationType()))
										.map(a -> a.annotationType().getSimpleName()))
								.collect(Collectors.toList()))
						.map(elem -> elem.get(buttonNames.indexOf(index))).collect(Collectors.toList()))
				.collect(Collectors.toList());
		List<JList<Object>> categoryLists = listValues.stream().map(v -> makeList(v,listValues.indexOf(v) * itemwidth,0,itemwidth,height-buttonheight)).collect(Collectors.toList());
		for(JList<Object> l : categoryLists) {
			listPanel.add(l);
		}
		listPanel.setBounds(0,buttonheight,width,height-buttonheight);
		sort.add(listPanel);


		
		return sort;
	}

	private JList<Object> makeList(List<String> values, int x, int y, int width, int height) {
		
		JList<Object> list = new JList<Object>( values.toArray());
		list.setBounds(x, y, width, height);
		return list;
	}

	private JButton makeButton(String name, int x, int y, int width, int height) {
		JButton button = new JButton(name);
		button.setBorder(null);
		button.setBounds(x, y, width, height);
		return button;
	}

	public static void main(String[] arg) {
		BenchmarkInvokerGUI b = new BenchmarkInvokerGUI();
	}

}
