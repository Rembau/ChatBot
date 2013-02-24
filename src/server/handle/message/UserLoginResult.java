package server.handle.message;

public class UserLoginResult  extends MessageCombine implements Message{
	public UserLoginResult(String s[]){
		this.s=s;
		type=2;
	}
}
