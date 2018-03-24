/**
 * 
 */
package com.auto1.group.game.interfaces;

import com.auto1.group.game.util.GameUtils;

/**
 * This is Game interface which exposes all the API operations for a player. It
 * holds default options shown to player and can be overridden if required.
 * 
 * @author yelsa03
 *
 */
public interface Game {

	public static final String ATTACK = "1";
	public static final String USE_BOTTLE = "2";
	public static final String RUN = "3";
	public static final String EXIT_FIGHT = "4";

	/**
	 * Api to start a new game
	 */
	void startNewGame();

	/**
	 * Api to save game status for a player to Db
	 * 
	 * @return
	 */
	boolean saveGame();

	/**
	 * Api to fetch the loaded status of player from Db
	 * 
	 * @return
	 */
	boolean loadGame();

	/**
	 * Api to take fight
	 */
	void takeFight();

	/**
	 * Api to explore the world specific to game
	 */
	void explore();

	/**
	 * Default player activities shown to player
	 */
	default void loadPlayerActivitiesOptions() {
		GameUtils.delimiter();
		System.out.println("1.Explore");
		System.out.println("2.Take a fight");
		System.out.println("3.Exit");
		GameUtils.delimiter();
		System.out.println("Enter Activity Option:");
	}

	/**
	 * Deault game options
	 */
	default void loadGameOptions() {
		GameUtils.delimiter();
		System.out.println("1.Start a new Game");
		System.out.println("2.Load Saved Game");
		System.out.println("3.Exit");
		GameUtils.delimiter();
		System.out.println("Select Game option to proceed");
	}

	/**
	 * Zone options shown to player specific to game
	 */
	public abstract void loadZoneOptions();

}
