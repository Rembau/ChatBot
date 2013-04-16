package org.rembau;

import org.apache.log4j.Logger;
import org.rembau.minaCommand.CommandClient;
import org.rembau.minaCommand.CommandServer;

import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * 
 * program entrance.
 * @author Rembau
 *
 */
public class MainClass {
	private static final Logger logger = Logger.getLogger(MainClass.class);

	public static void main(String[] args) {
		Context.init(); // initialize context
		for (int i = 0; i < args.length; i++) {
		//	System.out.println(args[i]);
		}
		//1:no param 2:start,starttup service 3:isn't start,entry client and exit after send the command 
		if (args.length == 0) {
			new CommandClient().start();
		} else if (args[0].trim().equals(Context.CMD_START)) {
			logger.info("Server start");
			new CommandServer().start();
			new FileSystemXmlApplicationContext(
					new String[] {"classpath:applicationContext.xml" });
			//DBManager db = (DBManager) ac.getBean("dbManager");
			//new JobDetail(db).excute();
		} else {
			new CommandClient(args[0]).start();
		}
	}
}
