package bot.comm;
/**
 * Chararter:UTF-8
 * @author Rembau
 *
 */
public class Context {
	public static int server_port=678;
	public static String servler_ip="127.0.0.1";
	/**
	 * 性能配置
	 */
	public static int connpool_num = 10;
	public static int threadpool_num = 10;
	/**
	 * 消息配置
	 */
	public static String uuidAndMessageSep="-=-=-=-=-=-=-=";
	/**
	 * 给有符号的 答案加上的权值。
	 */
	public static int pointForSign=10;
	/**
	 * 判断是否是重复问题算法接受的问题的最小长度。
	 */
	public static int minLengthAccept=1;
	
	public static String window_control_back="۩";
	public static String window_control_min="_";
	public static String window_control_max="□";
	public static String window_control_close="×";
	
	public static String image_path="images/";
	
	public static String cmd_train_start="训练";
	public static String cmd_train_end="结束";
	
	public static String cmd_train_start_message="teacher";
	public static String cmd_train_end_message="end";
	
	public static String cmd_send_message="发送";
	public static String cmd_login="登陆";
	
	public static String font_bold = "B";
	public static String font_italic = "I";
	public static String font_underline ="U";
	public static String font_color ="C";
	
	public static String font_name="font_name";
	public static String font_size="font_size";
	public static String font_name_default="宋体";
	public static String font_size_default="15";
	
	public static String mark_me="me";
	
	public static String back_image_name="";
}
