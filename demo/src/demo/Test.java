package demo;

public class Test {

	public static void main(String[] args) {
		System.out.println("The Console is broken");
		Server s1 = new Server(4456);
		Client c1 = new Client("Client 1", "127.0.0.1", 4456);
		
		s1.start();
		c1.start();
		System.out.println("Everything is broken");
	}
}
