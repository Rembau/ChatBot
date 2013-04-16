package bot.server.handle.message;

import bot.comm.Message;
import bot.comm.MessageCombine;
import bot.server.core.Bot;


public class ChatContent extends MessageCombine implements Message{
	public ChatContent(String s[]){
		this.s=s;
		this.type=1;
	}
	public String getMessageContent(){
		String str="";
		for(int i=0;i<s.length-1;i++){
			str+=s[i]+""+separate;
		}
		str+=s[s.length-1];
		str=Bot.getName()+""+this.separate+""+str;
		return this.combine(str);
	}
}
