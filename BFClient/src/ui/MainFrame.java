package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;

import rmi.RemoteHelper;


public class MainFrame extends JFrame {
	private JTextArea codeArea;//代码区
	private JTextArea inputArea;//输入区
	private JTextArea outputArea;//输出区
	boolean state = false;//登陆状态
	String userName = "";
	String password = "";
	JButton login;//登陆按钮
	MainFrame mainFrame = this;//引用
	JMenu fileListMenu;//文件菜单
	JMenu versionMenu;//版本菜单
	boolean fileState = false;//是否有文件正在被读取

	public MainFrame() {
		// 创建窗体
		JFrame frame = new JFrame("BF Client");
		frame.setLayout(null);
		
		//创建菜单栏
		JMenuBar menuBar = new JMenuBar();
		
		//文件菜单
		JMenu fileMenu = new JMenu("File");
		menuBar.add(fileMenu);
		fileMenu.setFont(new Font("", 1, 15));
		JMenuItem newMenuItem = new JMenuItem("New");
		fileMenu.add(newMenuItem);
		newMenuItem.setFont(new Font("", 1, 15));
		JMenuItem openMenuItem = new JMenuItem("Open");
		fileMenu.add(openMenuItem);
		openMenuItem.setFont(new Font("", 1, 15));
		JMenuItem saveMenuItem = new JMenuItem("Save");
		fileMenu.add(saveMenuItem);
		saveMenuItem.setFont(new Font("", 1, 15));
		frame.setJMenuBar(menuBar);

		newMenuItem.addActionListener(new NewActionListener());
		openMenuItem.addActionListener(new OpenActionListener());
		saveMenuItem.addActionListener(new SaveActionListener());
		
		//编译菜单
		JMenu runMenu = new JMenu("Run");
		menuBar.add(runMenu);
		runMenu.setFont(new Font("", 1, 15));
		JMenuItem executeMenuItem = new  JMenuItem("execute");
		runMenu.add(executeMenuItem);
		executeMenuItem.setFont(new Font("", 1, 15));
		
		executeMenuItem.addActionListener(new CompileActionListener());
		
		//目录菜单
		fileListMenu = new JMenu("FileList");
		menuBar.add(fileListMenu);
		fileListMenu.setFont(new Font("", 1, 15));
		
		//版本菜单
		versionMenu = new JMenu("Version");
		menuBar.add(versionMenu);
		versionMenu.setFont(new Font("", 1, 15));
		
		//登陆按钮
		login = new JButton("Login");
		frame.add(login);
		login.setBounds(580, 0, 100, 20);
		login.addActionListener(new LoginOroutActionListener());
	
		//代码区
		JLabel Code = new JLabel("Code :");
		Code.setBounds(10, 0, 50, 20);
		frame.add(Code);
		Code.setFont(new Font("", 1, 15));
		
		codeArea = new JTextArea();
		codeArea.setBounds(10, 20, 670, 300);
		codeArea.setMargin(new Insets(10, 10, 10, 10));
		codeArea.setBackground(Color.LIGHT_GRAY);
		codeArea.setLineWrap(true);
		frame.add(codeArea);

		//输入区
		JLabel input = new JLabel("Input :");
		input.setBounds(10, 330, 70, 20);
		frame.add(input);
		input.setFont(new Font("", 1, 15));
		
		inputArea = new JTextArea();
		inputArea.setBounds(10, 350, 300, 150);
		inputArea.setMargin(new Insets(10, 10, 10, 10));
		inputArea.setBackground(Color.LIGHT_GRAY);
		frame.add(inputArea);
		
		//输出区
		JLabel output = new JLabel("Output :");
		output.setBounds(370, 330, 70, 20);
		frame.add(output);
		output.setFont(new Font("", 1, 15));
		
		outputArea = new JTextArea();
		outputArea.setBounds(370, 350, 300, 150);
		outputArea.setMargin(new Insets(10, 10, 10, 10));
		outputArea.setBackground(Color.LIGHT_GRAY);
		frame.add(outputArea);
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(700, 600);
		frame.setLocation(600, 300);
		frame.setVisible(true);
		frame.setResizable(false);
	}
	
	class LoginOroutActionListener implements ActionListener{
		//登陆或登出
		@Override
		public void actionPerformed(ActionEvent e){
			if(!state)
				new LoginFrame(mainFrame);
			if(state){
				state = false;
				login.setText("Login");
				fileListMenu.removeAll();
				JOptionPane.showMessageDialog(null, "Logout successfully!");
			}
		}
	}
	
	class CompileActionListener implements ActionListener{
		//编译
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			String param = inputArea.getText();
			String output = "";
			try {
				output = RemoteHelper.getInstance().getExecuteService().execute(code, param);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			outputArea.setText(output);
		}
	}

	class NewActionListener implements ActionListener{
		//新建
		@Override
		public void actionPerformed(ActionEvent e){
			if(state){
				boolean flag = false;
				String fileName = JOptionPane.showInputDialog("Input the Filename:");
				try {
					flag = RemoteHelper.getInstance().getIOService().newFile(userName, fileName);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				if(flag)
					JOptionPane.showMessageDialog(null, "Create successfully!");
				else
					JOptionPane.showMessageDialog(null, "This filename exists!");
			}else
				JOptionPane.showMessageDialog(null, "You have not Loged in!");
		}
	}
	
	class OpenActionListener implements ActionListener{
		//打开
		@Override
		public void actionPerformed(ActionEvent e){
			if(state){
				String fileName = "";
				versionMenu.removeAll();
				fileName = JOptionPane.showInputDialog("Input the Filename:");
				ArrayList<String> versionList = new ArrayList<String>();
				try {
					versionList = RemoteHelper.getInstance().getIOService().readVersionList(userName, fileName);
				} catch (RemoteException e2) {
					e2.printStackTrace();
				}
				for(String version : versionList){
					JMenuItem versionItem = new JMenuItem(version);
					versionMenu.add(versionItem);
					versionItem.addActionListener(new versionActionListener(fileName, version));
				}
				fileState = true;
			}else
				JOptionPane.showMessageDialog(null, "You have not Loged in!");
		}
	}
	
	class versionActionListener implements ActionListener {
		//打开历史版本
		String version = "";
		String fileName = "";
		public versionActionListener(String fileName, String version){
			this.version = version;
			this.fileName = fileName;
		}
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = new String();
			try {
				code = RemoteHelper.getInstance().getIOService().readFile(userName, fileName, version);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			codeArea.setText(code);
		}
	}

	class SaveActionListener implements ActionListener {
		//保存
		@Override
		public void actionPerformed(ActionEvent e) {
			String code = codeArea.getText();
			if(state){
				String fileName = JOptionPane.showInputDialog("Input the Filename:");
				try {
					RemoteHelper.getInstance().getIOService().writeFile(code, userName, fileName);
					JOptionPane.showMessageDialog(null, "Saved successfully!");
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
			}else
				JOptionPane.showMessageDialog(null, "You have not Loged in!");
		}

	}
}
