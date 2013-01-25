package server.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;

public class ReadFile {
	public LinkedList<String> readLineList(File file){
		LinkedList<String> content = new LinkedList<String>();
		BufferedReader  dr=null;
		try {
			dr = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		String str_=null;
		try {
			while((str_=dr.readLine())!=null){
				if(str_.trim().equals("") || str_.trim().startsWith("#")){
					continue;
				}
				content.add(str_);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally{
			try {
				dr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}//end try
		return content;
	}//end readProgramFile
	public static void main(String[] args) {
	}
}
