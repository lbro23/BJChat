package demo;

public class Test {

	public static void main(String[] args) {
		Server s1 = new Server();
		Client c1 = new Client("Client 1", "127.0.0.1", 4445);
	}
}
