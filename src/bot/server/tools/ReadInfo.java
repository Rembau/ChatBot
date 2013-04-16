package bot.server.tools;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ReadInfo {
	static Properties p;
	public static void load(File file){
		p=new Properties();
		  InputStream in = null;
	      try {
	          in = new FileInputStream(file);
	      } catch (IOException ioe) {
	          ioe.printStackTrace();
	      }
	      try {
			p.load(in);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static String getString(String property){
		return 	p.getProperty(property);
	}
	public static int getInt(String property){
		return Integer.parseInt(p.getProperty(property));
	}
}
