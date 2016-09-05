//服务器IOService的Stub，内容相同
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
public interface IOService extends Remote{
	public boolean newFile(String userName, String fileName) throws RemoteException;
	
	public boolean writeFile(String code, String username, String fileName)throws RemoteException;
	
	public String readFile(String username, String fileName, String version)throws RemoteException;
	
	public ArrayList<String> readFileList(String username)throws RemoteException;
	
	public ArrayList<String> readVersionList(String username, String fileName)throws RemoteException;
}
