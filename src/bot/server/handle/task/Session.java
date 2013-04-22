package bot.server.handle.task;

import bot.server.core.Bot;

public abstract class Session {
	public String id;
	public Bot bot = new Bot();
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
