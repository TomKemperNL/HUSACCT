package husacct.control.presentation;
import husacct.control.presentation.menubar.AnalyseMenu;
import husacct.control.presentation.menubar.DefineMenu;
import husacct.control.presentation.menubar.FileMenu;
import husacct.control.presentation.menubar.HelpMenu;
import husacct.control.presentation.menubar.LanguageMenu;
import husacct.control.presentation.menubar.ValidateMenu;
import husacct.control.presentation.taskbar.TaskBar;
import husacct.control.task.MainController;

import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;
import javax.swing.JDesktopPane;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

public class MainGui extends JFrame{

	private static final long serialVersionUID = 140205650372010347L;

	private MainController mainController;
	private JMenuBar menuBar;
	private String titlePrefix = "HUSACCT";
	private JDesktopPane desktopPane;
	private TaskBar taskBar;
	
	public MainGui(MainController controller) {
		this.mainController = controller;
		setup();
		addComponents();
		createMenuBar();
		setVisible(true);
	}

	private void setup(){
		setTitle();
		Image icon = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/husacct/common/resources/husacct.png"));
		setIconImage(icon);
		setLayout(new BoxLayout(this.getContentPane(), BoxLayout.Y_AXIS));
		setExtendedState(Frame.MAXIMIZED_BOTH);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		setBounds(100, 100, 783, 535);
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				mainController.exit();		
			}
		});
	}
	
	private void addComponents(){
		desktopPane = new JDesktopPane();
		JPanel taskBarPane = new JPanel(new GridLayout());
		
		taskBar = new TaskBar();
		taskBarPane.add(taskBar);
		
		add(desktopPane);
		add(taskBarPane);
	}
	
	private void createMenuBar() {
		this.menuBar = new JMenuBar();
		
		FileMenu fileMenu = new FileMenu(mainController);
		DefineMenu defineMenu = new DefineMenu(mainController);
		AnalyseMenu analyseMenu = new AnalyseMenu(mainController);
		ValidateMenu validateMenu = new ValidateMenu(mainController);
		LanguageMenu languageMenu = new LanguageMenu(mainController);
		HelpMenu helpMenu = new HelpMenu(mainController);

		fileMenu.setMnemonic('F');
		defineMenu.setMnemonic('D');
		analyseMenu.setMnemonic('A');
		validateMenu.setMnemonic('V');
		languageMenu.setMnemonic('L');
		helpMenu.setMnemonic('H');
		
		menuBar.add(fileMenu);
		menuBar.add(defineMenu);
		menuBar.add(analyseMenu);
		menuBar.add(validateMenu);
		menuBar.add(languageMenu);
		menuBar.add(helpMenu);
		
		setJMenuBar(menuBar);

	}
	
	public JDesktopPane getDesktopPane(){
		return desktopPane;
	}
	
	public TaskBar getTaskBar(){
		return taskBar;
	}
	
	public void setTitle(String title){
		if(title.length() > 0){
			super.setTitle(titlePrefix + " - " + title);
		} else {
			super.setTitle(titlePrefix);
		}
	}
	
	public void setTitle(){
		setTitle("");
	}

}
