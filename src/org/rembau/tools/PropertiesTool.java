package org.rembau.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Properties;


public class PropertiesTool {
	public static Properties getParams(String file){
		return getParams(new File(file));
	}
	public static Properties getParams(URI file){
		return getParams(new File(file));
	}
	public static Properties getParams(URL file){
		try {
			System.out.println(file);
			return getParams(new File(file.toURI()));
		} catch (URISyntaxException e) {
			e.printStackTrace();
			return null;
		}
	}
	public static Properties getParams(File file){
		 Properties params = new Properties();
	        try {
	        	FileInputStream fis = new FileInputStream(file);
	        	params.load(fis);
	        	fis.close();
	        } catch (IOException e) {
	            System.out.println(e.getMessage());
	        }
		return params;
	}
	public static void main(String[] args) {

	}
}
