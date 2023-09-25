package ch.zhaw.ads; /**
 * @(#)ExBoxFrame.java
 *
 * JFC ch.zhaw.ads.ExBox application
 *
 * @author K.Rege
 * @version	1.00 2014/2/3
 * @version	1.01 2016/8/2 
 * @version	2.00 2017/8/30 Test
 * @version	2.01 2018/2/5 AutoscaleFaktor
 * @version	2.02 2018/3/12 Reconnect (inspired by S. Kunz)
 * @version	2.03 2021/7/24 Test (repeat)
 * @version	2.04 2021/9/11 Test as plugin
 */


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.util.*;


public class ExBoxFrame extends JFrame implements ActionListener, ItemListener {
	private final int UHDTHRESHOLD = 1920;
	private final String STANDARDENCODING = "ISO-8859-1";

	private JMenuItem connect, exit, open, test, retest, textView, graphicView, clear;
	private JMenu menuServer;
	private JButton enter;
	private JTextField arguments;
	private JComboBox history;
	private JTextArea output;
	private JScrollPane scrollPane;
	private CommandExecutor command;
	private CommandExecutor unitTest;
	private boolean graphicOn;
	private GraphicPanel graphic;
	private String lastServer;
	private String lastTestFile;

	public void setFontSize(int size) {
		Set<Object> keySet = UIManager.getLookAndFeelDefaults().keySet();
		Object[] keys = keySet.toArray(new Object[keySet.size()]);
		for (Object key : keys) {
			if (key != null && key.toString().toLowerCase().contains("font")) {
				Font font = UIManager.getDefaults().getFont(key);
				if (font != null) {
					font = font.deriveFont((float) size);
					UIManager.put(key, font);
				}
			}
		}
	}

	private void initMenu() {
		JMenuBar menuBar = new JMenuBar();
		setJMenuBar(menuBar);

		JMenu menuFile = new JMenu("File");
		menuBar.add(menuFile);
		open = new JMenuItem("Open...");
		open.addActionListener(this);
		menuFile.add(open);
		exit = new JMenuItem();
		exit.setText("Exit");
		exit.addActionListener(this);
		menuFile.add(exit);

		menuServer = new JMenu("Server");
		menuBar.add(menuServer);
		connect = new JMenuItem("Connect ...");
		connect.addActionListener(this);
		menuServer.add(connect);

		JMenu menuView = new JMenu("View");
		menuBar.add(menuView);
		clear = new JMenuItem("Clear");
		clear.addActionListener(this);
		menuView.add(clear);
		textView = new JMenuItem("Text");
		textView.addActionListener(this);
		menuView.add(textView);
		graphicView = new JMenuItem("Graphic");
		graphicView.addActionListener(this);
		menuView.add(graphicView);
	}

	private void initJUnit() {
		try {
			unitTest = ServerFactory.createServer(
					getPathCompiled() + File.separator + "ch.zhaw.ads.ExBoxJUnit.class");
			test = new JMenuItem("Test ...");
			test.addActionListener(this);
			menuServer.add(test);
			retest = new JMenuItem("Test");
			retest.addActionListener(this);
			menuServer.add(retest);
		} catch (Exception e) {
			warning("Test Plugin not found\n");
		}
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		output = new JTextArea();
		scrollPane = new JScrollPane(output);
		add(BorderLayout.CENTER, scrollPane);

		JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(BorderFactory.createEmptyBorder(4, 4, 4, 4));
		arguments = new JTextField();
		arguments.addActionListener(this);
		panel.add(BorderLayout.CENTER, arguments);
		enter = new JButton("enter");
		enter.addActionListener(this);
		panel.add(BorderLayout.EAST, enter);
		history = new JComboBox();
		history.addItemListener(this);
		panel.add(BorderLayout.SOUTH, history);
		add(BorderLayout.SOUTH, panel);
	}

	/**
	 * get default path for file open dialog
	 */
	private String getPathCompiled() {
		String pathtocompiled = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		pathtocompiled = pathtocompiled.replace("%20", " ").replace("/",
				File.separator);
		pathtocompiled += getClass().getPackage().getName().replace(".",
				File.separator);
		if (System.getProperty("os.name").toLowerCase().indexOf("win") >= 0) {
			pathtocompiled = pathtocompiled.substring(1);
		}
		return pathtocompiled;
	}

	/**
	 * The constructor
	 */
	public ExBoxFrame() throws Exception {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double scaleFaktor = (screenSize.getWidth() <= UHDTHRESHOLD) ? 1 : 2;
		setFontSize((int) (11 * scaleFaktor));
		setSize(
				new	Dimension((int) (400 * scaleFaktor), (int) (400 * scaleFaktor)));
		setTitle("ch.zhaw.ads.ExBox");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initComponents();
		initMenu();
		initJUnit();
	}

	private void warning(String s) {
		System.err.println("\nWARNING: " + s + "\n");
	}

	private void error(String s) {
		output.append("\nERROR: " + s + "\n");
	}

	private void execute(String args) throws Exception {
		if (lastServer != null) {
			command = ServerFactory.createServer(lastServer);
		}
		if (!arguments.getText().equals(history.getItemAt(0))
				&& !arguments.getText().equals(history.getSelectedItem())) {
			history.insertItemAt(arguments.getText(), 0);
		}
		if (command == null) {
			error("no Server connected");
		} else {
			String res = command.execute(args);
			if (graphicOn) {
				graphic.setFigure(res);
			} else {
				output.append(res);
			}
		}
	}

	private void setGraphicView() {
		if (!graphicOn) {
			remove(scrollPane);
			graphic = new GraphicPanel();
			output.removeNotify();
			add(BorderLayout.CENTER, graphic);
			graphicOn = true;
			validate();
			repaint();
		}
	}

	private void setTextView() {
		if (graphicOn) {
			remove(graphic);
			add(BorderLayout.CENTER, scrollPane);
			graphicOn = false;
			validate();
			repaint();
		}
	}

	private String openFileDialog(String startDirectory, String pattern) {
		FileDialog	fd = new FileDialog(this, "Open");
		if (pattern != null) fd.setFile(pattern);
		if (startDirectory != null) fd.setDirectory(startDirectory);
		fd.setVisible(true);
		return  fd.getDirectory() + fd.getFile();
	}

	private void testCommand(boolean retest) throws Exception {
		if (!retest) {
			lastTestFile = openFileDialog(getPathCompiled(), "*experimentbox.test.class");
		}
		if (lastTestFile == null) {
			output.append("ERROR no Test spezified\n");
		} else if (unitTest != null) {
			output.append(unitTest.execute(lastTestFile));
		}
	}

	private void connectCommand() throws Exception {
		String name = openFileDialog(getPathCompiled(), "*Server.class");
		command = ServerFactory.createServer(name);
		lastServer = name;
		String fullClassName = command.getClass().getName();
		String simpleClassName = fullClassName.substring(
				fullClassName.lastIndexOf('.') + 1);
		setTitle("ch.zhaw.ads.ExBox connected to " + simpleClassName);

	}

	private void openFile()  throws Exception {
		String name = openFileDialog(null, null);

		BufferedReader br = new BufferedReader(
				new InputStreamReader(new FileInputStream(name), STANDARDENCODING));
		StringBuffer b = new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
			b.append(line);
			b.append('\n');
		}
		execute(b.toString());
	}

	public void	itemStateChanged(ItemEvent e) {
		try {
			arguments.setText((String) e.getItem());
			execute(arguments.getText());
		} catch (Throwable ex) {
			error(ex.toString());
		}
	}

	public void	actionPerformed(ActionEvent	e) {
		try {
			if ((e.getSource() == arguments) || (e.getSource() == enter)) {
				execute(arguments.getText());
			} else if (e.getSource() == connect) {
				connectCommand();
			} else if (e.getSource() == test) {
				testCommand(false);
			} else if (e.getSource() == retest) {
				testCommand(true);
			} else if (e.getSource() == open) {
				openFile();
			} else if (e.getSource() == textView) {
				setTextView();
			} else if (e.getSource() == graphicView) {
				setGraphicView();
			} else if (e.getSource() == clear) {
				output.setText("");
			} else if (e.getSource() == exit) {
				System.exit(0);
			}
		} catch (Throwable ex) {
			ex.printStackTrace();
			error(ex.toString());
		}
	}
}