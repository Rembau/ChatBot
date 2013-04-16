package org.rembau.tools;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;


public class PropertiesTool {
	public static Properties getParams(String file){
		 Properties params = new Properties();
	        try {
	        	params.load(new FileInputStream(file));
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
		return params;
	}
	public static void main(String[] args) {

	}
}
