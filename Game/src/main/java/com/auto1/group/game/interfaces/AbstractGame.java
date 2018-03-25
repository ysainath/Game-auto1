/**
 * 
 */
package com.auto1.group.game.interfaces;

import static com.auto1.group.game.util.GameUtils.OPTION_1;
import static com.auto1.group.game.util.GameUtils.OPTION_2;
import static com.auto1.group.game.util.GameUtils.OPTION_3;
import static com.auto1.group.game.util.GameUtils.OPTION_4;
import static com.auto1.group.game.util.GameUtils.OPTION_5;

import java.util.Collection;
import java.util.List;
import java.util.Scanner;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.auto1.group.game.model.actors.Enemy;
import com.auto1.group.game.model.actors.GameLevel;
import com.auto1.group.game.model.actors.GameZone;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.service.PlayerService;
import com.auto1.group.game.util.GameUtils;

/**
 * This is abstract class which handles the game interfaces All the
 * implementations can use the abstract logic or override it
 * 
 * @author yelsa03
 *
 */
public abstract class AbstractGame implements Game {

	/**
	 * Game name string
	 */
	private String gameName;
	/**
	 * Player object injected to this class
	 */
	protected Player player;
	/**
	 * Game levels
	 */
	protected Collection<GameLevel> levels;
	/**
	 * Game Zones
	 */
	protected Collection<GameZone> zones;

	private Logger logger = LoggerFactory.getLogger(AbstractGame.class);

	/**
	 * Player service which is used to perform crud operations
	 */
	@Autowired
	protected PlayerService playerService;

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		System.out.println("Welcome to " + gameName + " world !!");
		this.gameName = gameName;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public Collection<GameLevel> getLevels() {
		return levels;
	}

	public void setLevels(Collection<GameLevel> levels) {
		this.levels = levels;
	}

	public Collection<GameZone> getZones() {
		return zones;
	}

	public void setZones(Collection<GameZone> zones) {
		this.zones = zones;
	}

	protected List<GameLevel> loadDefaultLevels() {
		return GameUtils.createLevels();
	}

	protected List<GameZone> loadDefaultZones() {
		return GameUtils.createZones();
	}

	/**
	 * This method handles fighting algorithm and child classes can override if
	 * required
	 */
	@Override
	public void takeFight() {
		Scanner in = new Scanner(System.in);
		try {

			GameUtils.delimiter();
			outerloop: for (GameLevel level : levels) {
				Integer cuurentLevel = level.getLevelNumber();
				player.setCurrentLevel(cuurentLevel);
				System.out.println("You are at Level:" + cuurentLevel);
				innerloop: while (player.isAlive()) {
					logger.debug("Player is at level :{}", cuurentLevel);
					List<Enemy> enemies = level.getEnemies();
					for (final Enemy enemy : enemies) {
						System.out.println("\t# " + enemy + " appeared! #\n");
						while (enemy.isAlive()) {

							showPlayerCurrentStatus(enemy);
							final String input = in.nextLine();
							switch (input) {
							case ATTACK:
								player.attack(enemy);
								enemy.attack(player);
								if (player.isDying()) {
									System.out.println("\t> #### You have taken too much damage ! ###");
									break innerloop;
								}
								break;
							case USE_BOTTLE:
								player.customBottles();
								break;
							case RUN:
								System.out.println("\t> You run away from the " + enemy + "!");
								continue;
							case EXIT_FIGHT:
								saveAndExitFighting();
								break outerloop;
							default:
								System.out.println("\t>Invalid command");
								continue;
							}

						}

						player.defeat(enemy);

						GameUtils.delimiter();
						System.out.println("What would you like to do now?");
						System.out.println("1. Continue fighting");
						System.out.println("2. Drink health bottle");
						System.out.println("3. Save and Exit Fighting");
						String input = in.nextLine();
						while (!input.equals(OPTION_1) && !input.equals(OPTION_2) && !input.equals(OPTION_3)) {
							System.out.println("Invalid command!");
							input = in.nextLine();
						}
						if (input.equals(OPTION_1)) {
							System.out.println("Continue on your fighting enemies!");
						} else if (input.equals(OPTION_2)) {
							player.customBottles();
						} else if (input.equals(OPTION_3)) {
							saveAndExitFighting();
							break outerloop;
						}

					}
				}
				if (player.isAlive()) {
					GameUtils.delimiter();
					System.out.println("Hurray !!!! You have Successfully completed Level:" + cuurentLevel);
					GameUtils.delimiter();
				} else {
					break;
				}
			}
			if (!player.isAlive()) {
				System.out.println("### Player is Dead due to no health remaining. ###");
				System.out.println("### Game Over !!!  ###");
				System.exit(0);
			}
		} catch (Exception e) {
			if (in != null) {
				in.close();
			}
			logger.error("Exception found in fighting for player = {}", player);
			throw e;
		}
	}

	private void saveAndExitFighting() {
		System.out.println("You exited the fighting successfully");
		saveGame();
		logger.debug("Saved the player state successfully and player :{}", player);
	}

	private void showPlayerCurrentStatus(final Enemy enemy) {
		System.out.println("\tYour Score: " + player.getScore());
		System.out.println("\tYour Health: " + player.getHealth());
		System.out.println("\t" + enemy + "'s Health: " + enemy.getHealth());
		System.out.println("\n\tWhat would you like to do?");
		System.out.println("\t1. Attack");
		System.out.println("\t2. Drink Health Bottle");
		System.out.println("\t3. Run!");
		System.out.println("\t4. Save & Exit fighting");
	}

	/**
	 * This method handles starting the new game logic. Deletes all history and
	 * start a fresh one
	 * 
	 */
	@Override
	public void startNewGame() {
		try {
			playerService.delete(player);
			logger.info("Deleted player from repo :{}", player);
		} catch (Exception e) {
			logger.error("Exception in deleting the player :{}", player);
			throw e;
		}
	}

	/**
	 * This method allows to save state of player to db
	 */
	@Override
	public boolean saveGame() {
		try {
			playerService.save(player);
			logger.info("Saved player to repo :{}", player);
			return true;
		} catch (Exception e) {
			logger.error("Exception in saving the player status:{}", player);
			throw e;
		}
	}

	/**
	 * This method helps to load the saved state of player from db
	 */
	@Override
	public boolean loadGame() {

		try {
			Player loadedPlayer = playerService.findByPlayerAndGameName(player);
			if (loadedPlayer != null) {
				this.player = loadedPlayer;
				System.out.println("### Your details successfully loaded. ###");
				player.loadStatus();
				logger.debug("Player details found for player :{}", player);
				return true;
			}
			System.out.println("No player details found to load for player name:" + player.getName());
			logger.debug("No player details found for player :{}", player);
		} catch (Exception e) {
			logger.error("Exception in loading the player status:{}", player);
			throw e;
		}
		return false;
	}

	/**
	 * This method shows the zones options for selecting for specific game
	 */
	@Override
	public void loadZoneOptions() {

		GameUtils.delimiter();
		int i = 1;
		for (GameZone zone : zones) {
			System.out.println(i + ". " + zone.getZoneName());
			i++;
		}
		System.out.println(i + ". Save & Exit");
		GameUtils.delimiter();
		System.out.println("Select Zone option to explore");
	}

	/**
	 * This method handles explore logic . It runs in while loop until player is
	 * alive
	 */
	@Override
	public void explore() {
		Scanner in = new Scanner(System.in);
		try {
			loop: while (player.isAlive()) {
				recommendTheZone();
				loadZoneOptions();
				String zoneOption = in.nextLine();
				switch (zoneOption) {

				case OPTION_1:
					exploreStatus("North");
					break;
				case OPTION_2:
					exploreStatus("South");
					break;
				case OPTION_3:
					exploreStatus("East");
					break;
				case OPTION_4:
					exploreStatus("West");
					break;
				case OPTION_5:
					saveGame();
					break loop;

				default:
					System.out.println("\n Invalid command");
					continue;

				}
			}

		} catch (Exception e) {
			if (in != null) {
				in.close();
			}
			throw e;
		}
	}

	private void exploreStatus(String zoneName) {
		player.addZone(zoneName);
		player.addItem(GameZone.spawnRandomItem());
		System.out.println(" # Hurray !! You have got item in your basket in zone :" + zoneName + ". #");
		player.loadStatus();
	}

	/**
	 * This method handles recommendations for zones or guide the player to take
	 * direction and must be handled by child classes extending this abstract
	 * class
	 */
	protected abstract void recommendTheZone();

}
