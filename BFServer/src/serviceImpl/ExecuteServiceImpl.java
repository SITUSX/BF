//请不要修改本文件名
package serviceImpl;

import java.rmi.RemoteException;
import java.util.ArrayList;

import service.ExecuteService;

public class ExecuteServiceImpl implements ExecuteService {

	/**
	 * 请实现该方法
	 */
	@Override
	public String execute(String code,String param) throws RemoteException{
		//输出
		String result = new String();
		
		//所需寄存器数量
		int countOfRegister = 1;
		for( char c : code.toCharArray())
			if(c=='>')
				countOfRegister++;
		
		//寄存器声明以及初始化
		char[] register = new char[countOfRegister];
		for(int i=0;i<countOfRegister;i++)
			register[i] = 0;
		
		//[]位置下标映射
		int countOfCircle = 0;
		for( char c : code.toCharArray())
			if(c=='[')
				countOfCircle++;
		int[] former = new int[countOfCircle];
		int m = 0;
		int[] later = new int[countOfCircle];
		for(int i=0;i<code.length();i++){
			if(code.charAt(i)=='['){
				former[m] = i;
				int count = 0;
LOOP:			for(int j=i;j<code.length();j++){
					if(code.charAt(j)=='[')
						count++;
					if(code.charAt(j)==']')
						count--;
					if(count==0){
						later[m] = j;
						break LOOP;
						}
				}
				m++;
			}
		}
		//寄存器指针
		int pointer = 0;
		
		for(int j=0;j<code.length();j++){
			char c = code.charAt(j);

			try{
				if(c=='+')
					register[pointer]++;
				if(c=='-')
					register[pointer]--;
				if(c=='>')
					pointer++;
				if(c=='<')
					pointer--;
				if(c=='.')
					result = result + (char)register[pointer];
				if(c==','){
					register[pointer] = param.charAt(0);
					try{
						param = param.substring(1);
					}catch(Exception e){
						param = null;
					}	
				}
				if(c=='['&&register[pointer]==0){
					for(int k=0;k<countOfCircle;k++){
						if(former[k]==j)
							j = later[k];
					}
				}
				if(c==']'&&register[pointer]!=0){
					for(int k=0;k<countOfCircle;k++){
						if(later[k]==j)
							j = former[k];
					}
				}
			}catch(Exception e){
				System.out.println("Error!:"+e.getMessage());
			}	
		}
		return result;
	} 


}
