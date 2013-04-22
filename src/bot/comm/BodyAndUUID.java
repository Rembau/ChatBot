package bot.comm;

public class BodyAndUUID {
	public static String sep=Context.uuidAndMessageSep;
	public static String[] getContext(String m){
		String context[] = m.split(sep);
		String mid = context[1];
		context[1] = context[0];
		context[0] = mid;
		return context;
	}
	public static String combine(String uuid,String m){
		return m+""+sep+""+uuid;
	}
}
