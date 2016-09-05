package serviceImpl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import service.IOService;

public class IOServiceImpl implements IOService{
	
	@Override
	public boolean newFile(String userName, String fileName){
		File tempUser = new File("user/"+userName);
		String[] list = tempUser.list();
		boolean flag = true;
		for(String str : list)
			if(str.equals(fileName))
				flag = false;
		if(flag){
			File f = new File("user/"+userName+"/"+fileName);
			f.mkdirs();
			return true;
		}else
			return false;
	}
	
	@Override
	public boolean writeFile(String code, String username, String fileName) {
		String version = String.valueOf(System.currentTimeMillis());
		File f = new File("user/"+username+"/"+fileName+"/"+version+".txt");
		try {
			FileWriter fw = new FileWriter(f);
			fw.write(code);
			fw.flush();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public String readFile(String username, String fileName, String version) {
		File f = new File("user/"+username+"/"+ fileName+"/"+version);
		String code = "";
		try {
			FileReader fr = new FileReader(f);
			BufferedReader br = new BufferedReader(fr);
			String brLine;
			try {
				while((brLine = br.readLine())!=null)
					code += brLine;
				} catch (IOException e) {
					e.printStackTrace();
				}
			} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return code;

	}

	@Override
	public ArrayList<String> readFileList(String username) {
		File f = new File("user/"+username);
		String[] tempList = f.list();
		ArrayList<String> list = new ArrayList<String>();
		for(String str : tempList)
			list.add(str);
		return list;
	}
	
	@Override
	public ArrayList<String> readVersionList(String username, String fileName) {
		File f = new File("user/"+username+"\\"+fileName);
		String[] tempList = f.list();
		ArrayList<String> list = new ArrayList<String>();
		for(String str : tempList)
			list.add(str);
		return list;
	}
	
}
