package bot.server.handle.task;

import java.util.Hashtable;

public class Sessions {
	private static Hashtable<String,Session> se = new Hashtable<String,Session>();
	public static Session getSe(String id){
		return se.get(id);
	}
	public static void addSession(String id,Session s){
		se.put(id, s);
	}
}
