package rmi;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import service.ExecuteService;
import service.IOService;
import service.UserService;
import serviceImpl.ExecuteServiceImpl;
import serviceImpl.IOServiceImpl;
import serviceImpl.UserServiceImpl;

public class DataRemoteObject extends UnicastRemoteObject implements IOService, UserService, ExecuteService{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4029039744279087114L;
	private IOService iOService;
	private UserService userService;
	private ExecuteService executeService;
	protected DataRemoteObject() throws RemoteException {
		iOService = new IOServiceImpl();
		userService = new UserServiceImpl();
		executeService = new ExecuteServiceImpl();
	}
	@Override
	public String execute(String code, String param) throws RemoteException{
		// TODO Auto-generated method stub
		return executeService.execute(code, param);
	}

	@Override
	public boolean writeFile(String code, String username, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.writeFile(code, username, fileName);
	}
	
	@Override
	public boolean newFile(String userName, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.newFile(userName, fileName);
	}
	
	@Override
	public String readFile(String username, String fileName, String version) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFile(username, fileName, version);
	}

	@Override
	public ArrayList<String> readFileList(String username) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readFileList(username);
	}
	
	@Override
	public ArrayList<String> readVersionList(String username, String fileName) throws RemoteException{
		// TODO Auto-generated method stub
		return iOService.readVersionList(username, fileName);
	}


	@Override
	public String login(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.login(username, password);
	}

	@Override
	public boolean logout(String username) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.logout(username);
	}
	
	@Override
	public boolean signup(String username, String password) throws RemoteException {
		// TODO Auto-generated method stub
		return userService.signup(username, password);
	}
}
