package ui;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import rmi.RemoteHelper;

public class SignupFrame extends JFrame{
	private JTextField textField1;//账号框
	private JTextField textField2;//密码框
	private JButton buttonConfirm;//确认按钮
	JFrame frame;
	
	public SignupFrame(){
		frame = new JFrame("注册");
		frame.setLayout(null);
		
		JLabel label = new JLabel("注册界面");
		frame.add(label);
		label.setBounds(190, 30, 120, 50);
		label.setFont(new Font("黑体",1,25));;
		
		//账号密码区
		JLabel label1 = new JLabel("账号");
		JLabel label2 = new JLabel("密码");
				
		textField1 = new JTextField();
		textField2 = new JTextField();
		
		//账号标签
		frame.add(label1);
		label1.setBounds(110, 150, 50, 50);
		label1.setFont(new Font("宋体" ,1 ,20));
		frame.add(textField1);
		textField1.setBounds(240, 150, 170, 40);
		
		//密码标签
		frame.add(label2);
		label2.setBounds(110, 200, 50, 50);
		label2.setFont(new Font("宋体" ,1 ,20));
		frame.add(textField2);
		textField2.setBounds(240, 200, 170, 40);
	
		//确认按钮
		buttonConfirm = new JButton("确认");
		frame.add(buttonConfirm);
		buttonConfirm.setBounds(200, 300, 100, 50);
		buttonConfirm.addActionListener(new ButtonActionListener());
		
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setBounds(720, 320, 500, 500);
		frame.setVisible(true);
	}
	
	class ButtonActionListener implements ActionListener{
		//确认
		@Override
		public void actionPerformed(ActionEvent e){
			String username = textField1.getText();
			String password = textField2.getText();
			boolean b = true;
			try{
				b = RemoteHelper.getInstance().getUserService().signup(username, password);
			} catch (RemoteException e1) {
				e1.printStackTrace();
			}
			if(b){
				JOptionPane.showMessageDialog(null, "Sign up successly! Now log in.");
				frame.dispose();
			}else{
				JOptionPane.showMessageDialog(null, "This account exists!");
			}
		}
	}
}
