package bot.server.handle.task;

import bot.server.core.Bot;

public abstract class Session {
	public String id;
	public String address;
	public int life_time_left=10;
	public Bot bot = new Bot();
	
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public int getLife_time_left() {
		return life_time_left;
	}
	public void setLife_time_left(int life_time_left) {
		this.life_time_left = life_time_left;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Bot getBot() {
		return bot;
	}
	public void setBot(Bot bot) {
		this.bot = bot;
	}
	public abstract void handleMessage(String str);
}
