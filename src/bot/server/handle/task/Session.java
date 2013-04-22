package bot.server.handle.task;

import java.util.UUID;

public interface Session {
	UUID id= UUID.randomUUID();
	public void handleMessage(String str);
}
