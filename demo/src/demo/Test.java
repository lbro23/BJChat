package demo;

public class Test {

	public static void main(String[] args) {
		System.out.println("The Console is broken");
		Server s1 = new Server(4445);
		
		Client c1 = new Client("Client 1", "127.0.0.1", 4445);
		System.out.println("Please fix it");
	}
}
