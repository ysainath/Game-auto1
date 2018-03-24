/**
 * 
 */
package com.auto1.group.game.interfaces;

import com.auto1.group.game.util.GameUtils;

/**
 * @author yelsa03
 *
 */
public interface Game {

	public static final String ATTACK = "1";
	public static final String USE_BOTTLE = "2";
	public static final String RUN = "3";
	public static final String EXIT_FIGHT = "4";

	void startNewGame();

	boolean saveGame();

	boolean loadGame();

	void takeFight();

	void explore();
	

	default void loadPlayerActivitiesOptions() {
		GameUtils.delimiter();
		System.out.println("1.Explore");
		System.out.println("2.Take a fight");
		System.out.println("3.Exit");
		GameUtils.delimiter();
		System.out.println("Enter Activity Option:");
	}

	default void loadGameOptions() {
		GameUtils.delimiter();
		System.out.println("1.Start a new Game");
		System.out.println("2.Load Saved Game");
		GameUtils.delimiter();
		System.out.println("Select Game option to proceed");
	}

	public void loadZoneOptions();

}
