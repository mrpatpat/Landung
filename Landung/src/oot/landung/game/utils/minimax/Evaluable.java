package oot.landung.game.utils.minimax;

import java.util.List;

public interface Evaluable {
	
	public int getValue();
	public List<Evaluable> getChildren();

}
