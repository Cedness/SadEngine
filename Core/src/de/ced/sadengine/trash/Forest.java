package de.ced.sadengine.trash;

public class Forest {
	
	private Tree[][] trees;
	
	public Forest(int width, int height) {
		trees = new Tree[height][width];
		for (Tree[] trees : this.trees) {
			for (int i = 0; i < width; i++) {
				trees[i] = new Tree();
			}
		}
	}
	
	public void letTreeDie(int width, int height) {
		trees[height][width].letHimDie();
	}
	
	public void letTreesDie(int ii, boolean direction) {
		for (int i = (direction ? getWidth() : getHeight()) - 1; i >= 0; i--) {
			trees[direction ? ii : i][direction ? i : ii].letHimDie();
		}
	}
	
	public int getWidth() {
		return trees[0].length;
	}
	
	public int getHeight() {
		return trees.length;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		for (Tree[] trees : this.trees) {
			for (Tree tree : trees) {
				builder.append(tree.toString());
			}
			builder.append("\n");
		}
		return builder.toString();
	}
}
