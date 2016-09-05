package ui;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class LoginFrame extends JFrame {
	JTextField textField1;//账号框
	JTextField textField2;//密码框
	private JButton buttonConfirm;//确认按钮
	private JButton buttonsignup;//注册按钮
	JFrame frame;
	String b = "";//登陆状态
	MainFrame mainFrame;

	public LoginFrame (MainFrame mainFrame){
		this.mainFrame = mainFrame;
		frame = new JFrame("登陆");
		frame.setLayout(null);
		
		JLabel label = new JLabel("登陆界面");
		frame.add(label);
		label.setBounds(190, 30, 120, 50);
		label.setFont(new Font("黑体",1,25));;
		
		JLabel label1 = new JLabel("账号");
		JLabel label2 = new JLabel("密码");
				
		textField1 = new JTextField();
		textField2 = new JTextField();
		
		frame.add(label1);
		label1.setBounds(110, 150, 50, 50);
		label1.setFont(new Font("宋体" ,1 ,20));
		frame.add(textField1);
		textField1.setBounds(240, 150, 170, 40);
		
		frame.add(label2);
		label2.setBounds(110, 200, 50, 50);
		label2.setFont(new Font("宋体" ,1 ,20));
		frame.add(textField2);
		textField2.setBounds(240, 200, 170, 40);
		

		buttonConfirm = new JButton("确认");
		buttonsignup = new JButton("注册");
		
		frame.add(buttonConfirm);
		buttonConfirm.setBounds(100, 300, 100, 50);
		
		frame.add(buttonsignup);
		buttonsignup.setBounds(300, 300, 100, 50);
		
		buttonConfirm.addActionListener(new ButtonActionListener());
		buttonsignup.addActionListener(new Button2ActionListener());
		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(700, 300, 500, 500);
		frame.setVisible(true);
		
 	}
	
	class  ButtonActionListener implements ActionListener{
		//登陆
		@Override
		public void actionPerformed(ActionEvent e){
			String username = textField1.getText();
			String password = textField2.getText();
			try {
				b = RemoteHelper.getInstance().getUserService().login(username, password);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			
			//判断账号是否存在或密码是否错误
			if(b.equals("0")){
				JOptionPane.showMessageDialog(null, "Login successly! Welcome,"+username);
				frame.dispose();
				mainFrame.login.setText(username);
				mainFrame.state = true;
				mainFrame.userName = username;
				mainFrame.password = password;
				
				//mainFrame目录菜单
				ArrayList<String> list = new ArrayList<String>();
				try {
					list = RemoteHelper.getInstance().getIOService().readFileList(username);
				} catch (RemoteException e1) {
					e1.printStackTrace();
				}
				for(String str : list){
					if(!(str.equals("password.txt")))
						mainFrame.fileListMenu.add(new JMenuItem(str));
					}
			}else if(b.equals("1")){
				JOptionPane.showMessageDialog(null, "Login failed! Password is wrong!");
			}else if(b.equals("-1"))
				JOptionPane.showMessageDialog(null, "Login failed! This account doesn't exist!");
				
		}
	}
	
	class Button2ActionListener implements ActionListener{
		//注册
		@Override
		public void actionPerformed(ActionEvent e){
			new SignupFrame();
		}
	}
//	public static void main(String[] args) {
//		new LoginFrame();
//	}

}
