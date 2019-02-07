package de.ced.sadengine.utils;

import de.ced.threadpool.Task;

public class SadIntro implements Task {
	
	private final Stage stage;
	private final String version;
	
	public SadIntro(Stage stage, String version) {
		this.stage = stage;
		this.version = version;
	}
	
	@Override
	public void run() {
		synchronized (System.out) {
			System.out.println();
			System.out.println("    ------------           /\\            ---------------        -----------------      -------------------      ");
			System.out.println("  /            /          /--\\          |               \\      /                 \\    |                   \\     ");
			System.out.println(" /     --------          /    \\         |     ------     \\    /                   \\   |                    \\    ");
			System.out.println("|     /                 /------\\        |    |      \\     |  /                     \\  |                     \\   ");
			System.out.println("|    |                 /        \\       |    |       |    | |           -           | |                      \\  ");
			System.out.println("|     ---------       /__------__\\      |    |       |    | |          | |          | |          |\\           \\ ");
			System.out.println(" \\             \\     /            \\     |    |       |    |  ----------  |          | |          | \\           |");
			System.out.println("  ---------     |   /   ___--___   \\    |    |       |    |             /           | |          |  |          |");
			System.out.println("           |    |  /_---- |()|  ---_\\   |    |       |    |   ----------            | |          |  |          |");
			System.out.println("          /     | /  ---___\\/___---  \\  |    |      /     |  |                     /  |          |  |          |");
			System.out.println("  --------     / /_        --        _\\ |     ------     /   |                    |   |          |  |          |");
			System.out.println(" /            / /  ---___      ___---  \\|               /    |                    |   |          |  |          |");
			System.out.println(" -------------  ------------------------ ---------------      ----------           \\  |          |  |          |");
			System.out.println("  -------  ---      -    ------   -   ---      -   -------              \\           | |          |  |          |");
			System.out.println(" |  ----- |   \\    | |  /  ---   | | |   \\    | | |  -----   ----------  |          | |          | /           |");
			System.out.println(" | |      | |\\ \\   | | |  /      | | | |\\ \\   | | | |       |          | |          | |          |/           / ");
			System.out.println(" |  ----  | | \\ \\  | | | |   --  | | | | \\ \\  | | |  ----   |           -           | |                      /  ");
			System.out.println(" |  ----  | |  \\ \\ | | | |  -  | | | | |  \\ \\ | | |  ----    \\                     /  |                     /   ");
			System.out.println(" | |      | |   \\ \\| | |  \\_/  | | | | |   \\ \\| | | |         \\                   /   |                    /    ");
			System.out.println(" |  ----- | |    \\   |  \\     /  | | | |    \\   | |  -----     \\  -" + stage.name().toLowerCase() + " " + version + "-  /    |                   /     ");
			System.out.println("  -------  -      ---    -----    -   -      ---   -------      -----------------      -------------------      ");
			System.out.println();
		}
	}
	
	@SuppressWarnings("unused")
	public enum Stage {
		INDEV,
		ALPHA,
		BETA,
		RELEASE
	}
}
