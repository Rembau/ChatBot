package server.botException;

public class InitException extends Exception{
	private static final long serialVersionUID = 1L;
	private String type; 
	public InitException(String type){
		this.type=type;
	}
	public String getMessage(){
		return type+"º”‘ÿ ß∞‹£°";
	}
}
