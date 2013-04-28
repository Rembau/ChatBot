package bot.server.handle.task;

import java.util.Hashtable;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;

public class Sessions {
	private static final Logger logger = Logger.getLogger(Sessions.class);
	private static Hashtable<String,Session> se = new Hashtable<String,Session>();
	static{
		new Timer().schedule(new TimerTask(){
			public void run() {
				LinkedList<String> invalids = new LinkedList<String>();
				for (String key : se.keySet()) {
					Session s = se.get(key);
					if(s.getLife_time_left()==0){
						invalids.add(key);
					} else{
						s.setLife_time_left(s.getLife_time_left()-1);
					}
				}
				for (String key : invalids) {
					Session s = se.get(key);
					
					logger.info(s.getAddress()+" was invalid,remove this.");
					se.remove(key);
				}
			}
		}, 0, 60*1000);
	}
	public static Session getSe(String id){
		return se.get(id);
	}
	public static void addSession(String id,Session s){
		logger.info("ÐÂÓÃ»§"+s.getAddress());
		se.put(id, s);
	}
}
