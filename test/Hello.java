package test;

public class Hello {

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Hello h = new Hello();
		int i=h.p();
		int j = i+4;
		System.out.println("Hello world-1!");
		System.out.println("Hello world-2!");
	}
	
	public int p(){
		return 10;
	}

}
