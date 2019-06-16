package de.ced.sadengine.trash;

public class Tree {
	
	private boolean fallen = false;
	
	public void letHimDie() {
		fallen = true;
	}
	
	@Override
	public String toString() {
		return fallen ? "O" : "X";
	}
}
