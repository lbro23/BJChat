package spaceships;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class State implements Serializable {
	private static final long serialVersionUID = -2890596819732839941L;
	
	protected List<Actor> actors;
	
	public State(List<Actor> actors) {
		this.actors = actors;
	}
	
	public State() {
		actors = new ArrayList<Actor>();
	}
		
	public List<Actor> getActors() {
		return actors;
	}
	
	public void addActor(Actor a) {
		actors.add(a);
	}
	
	public void updateAll() {
		for(Actor a: actors) {
			a.update();
		}
	}
	
	
}
