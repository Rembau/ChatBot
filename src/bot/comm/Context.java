package bot.comm;

public class Context {
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
	
	public static String window_control_min="_";
	public static String window_control_max="��";
	public static String window_control_close="��";
	public static String image_path="images/";
	
	public static String cmd_train_start="ѵ��";
	public static String cmd_train_end="����";
	
	public static String cmd_train_start_message="teacher";
	public static String cmd_train_end_message="end";
	
	public static String cmd_send_message="����";
	public static String cmd_login="��½";
}
