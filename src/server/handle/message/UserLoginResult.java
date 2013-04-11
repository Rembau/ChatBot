package server.handle.message;

import comm.Message;
import comm.MessageCombine;

public class UserLoginResult  extends MessageCombine implements Message{
	public UserLoginResult(String s[]){
		this.s=s;
		type=2;
	}
}
