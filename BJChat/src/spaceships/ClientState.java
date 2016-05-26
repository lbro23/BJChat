package spaceships;

import java.util.List;

public class ClientState extends State {
	
	private static final long serialVersionUID = -87284704644917374L;
	private String name;

	public ClientState(List<Actor> actors, String name) {
		super(actors);
		this.name = name;
	}
	
	public ClientState() {
		super();
	}
	
	public String getName() { return name; }
}
