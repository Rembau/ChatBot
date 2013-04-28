package bot.comm;

import java.awt.Image;
import java.awt.Toolkit;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Chararter:UTF-8
 * @author Rembau
 *
 */
public class Context {
	////////////server
	public static int server_port=678;
	public static String servler_ip="127.0.0.1";
	/**
	 * ��������
	 */
	public static int connpool_num = 10;
	public static int threadpool_num = 10;
	/**
	 * ��Ϣ����
	 */
	public static String uuidAndMessageSep="-=-=-=-=-=-=-=";
	/**
	 * ���з��ŵ� �𰸼��ϵ�Ȩֵ��
	 */
	public static int pointForSign=10;
	/**
	 * �ж��Ƿ����ظ������㷨���ܵ��������С���ȡ�
	 */
	public static int minLengthAccept=1;
	public static int session_life_time_left=5;
	
	
	///////////////client
	public static String window_control_back="#";
	public static String window_control_min="_";
	public static String window_control_max="��";
	public static String window_control_close="��";
	
	public static String image_path="/images/";
	
	public static String cmd_train_start="ѵ��";
	public static String cmd_train_end="����";
	
	public static String cmd_train_start_message="teacher";
	public static String cmd_train_end_message="end";
	
	public static String cmd_send_message="����";
	public static String cmd_login="��½";
	
	public static String font_bold = "B";
	public static String font_italic = "I";
	public static String font_underline ="U";
	public static String font_color ="C";
	
	public static String font_name="font_name";
	public static String font_size="font_size";
	public static String font_name_default="����";
	public static String font_size_default="15";
	
	public static String mark_me="me";
	
	public static String back_image_name="back.jpg";
	/*static {
		Properties params=null;
		params = PropertiesTool.getParams(Context.class.getClassLoader().getResource("extra.properties"));
		Context.image_path = params.getProperty("image_path").trim();
		System.out.println(image_path);
		Context.back_image_name = params.getProperty("back_image_name").trim();
	}*/
	public static Image getImage(String name){
		System.out.println(name);
		if(name.startsWith("http:") || name.startsWith("HTTP:")){
			URL path=null;
			try {
				path = new URL(image_path+""+name);
			} catch (MalformedURLException e) {
				e.printStackTrace();
			}
			System.out.println(path);
			return Toolkit.getDefaultToolkit().createImage(path);
		} else {
			String path = image_path+""+name;
			System.out.println(path);
			return Toolkit.getDefaultToolkit().createImage(Context.class.getResource(path));
		}
	}
}
