package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.rmi.RemoteException;

import service.UserService;

public class UserServiceImpl implements UserService{
	
//	public static void main(String[] args) throws RemoteException {
//		UserServiceImpl impl = new UserServiceImpl();
//		String str = impl.login("admin","123");
//		System.out.println(str);
//	}

	@Override
	public String login(String username, String password) throws RemoteException {
		File User = new File("user");
		String[] arr = User.list();
		for(String str : arr){
			if(username.equals(str)){
				//获取密码字符串
				FileReader fr = null;
				try {
					fr = new FileReader("user/"+username+"/password.txt");
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				}
				BufferedReader bf = new BufferedReader(fr);
				String userPassword = "";
				try {
					userPassword = bf.readLine();
					bf.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
				//密码比对
				if(password.equals(userPassword))
					return "0";
				else
					return "1";
			}
		}
		return "-1";
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		return true;
	}

	@Override
	public boolean signup(String username, String password) throws RemoteException {
		//判断是否已经存在此账户
		File User = new File("user");
		String[] arr = User.list();
		for(String str : arr){
			if(username.equals(str))
				return false;
			}
		
		//创建账户文件夹
		File un = new File("user/"+username);
		un.mkdirs();
		//添加密码文件
		File pw = new File("user/"+username+"/password.txt");
		try {
			pw.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
		FileWriter fw = null;
		try {
			fw = new FileWriter("user/"+username+"/password.txt");
			fw.write(password);
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			try {
				fw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}


		return true;
	}
}
