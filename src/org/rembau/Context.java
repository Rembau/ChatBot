package org.rembau;

import java.io.File;
import java.util.Properties;

import org.rembau.tools.PropertiesTool;

public class Context {
	public static int NUMOFCLIENT=0; //当前客户端连接数
	
	public static int CMD_S_PORT=2000;
	public static String CMD_START="start";
	public static String CMD_STOP="stop";
	public static String CMD_EXIT="exit";
	public static String CMD_EXCUTE="excute";
	public static String CMD_CLIENT="client";
	public static void init(){
		Properties params = PropertiesTool.getParams("conf"+File.separator+"extra.properties");
		Context.CMD_S_PORT = Integer.valueOf(params.getProperty("CMD_S_PORT"));
		Context.CMD_EXCUTE = params.getProperty("CMD_EXCUTE").trim();
		Context.CMD_EXIT = params.getProperty("CMD_EXIT").trim();
		Context.CMD_STOP = params.getProperty("CMD_STOP").trim();
		Context.CMD_START = params.getProperty("CMD_START").trim();
		Context.CMD_CLIENT = params.getProperty("CMD_CLIENT").trim();
	}
}
