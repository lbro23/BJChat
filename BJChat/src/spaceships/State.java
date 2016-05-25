package spaceships;

import java.io.Serializable;
import java.util.List;

public class State implements Serializable {
	private static final long serialVersionUID = -2890596819732839941L;
	
	protected List<Actor> actors;
	
	public State(List<Actor> actors) {
		this.actors = actors;
	}
	
}
