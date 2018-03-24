/**
 * 
 */
package com.auto1.group.game.interfaces;

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
 * @author yelsa03
 *
 */
public abstract class AbstractGame implements Game {

	private String gameName;
	protected Player player;
	protected Collection<GameLevel> levels;
	protected Collection<GameZone> zones;

	private Logger logger = LoggerFactory.getLogger(AbstractGame.class);

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
									System.out.println(
											"\t> #### You have taken too much damage, Drink health bottle ! ###");
									break innerloop;
								}
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
						while (!input.equals("1") && !input.equals("2") && !input.equals("3")) {
							System.out.println("Invalid command!");
							input = in.nextLine();
						}
						if (input.equals("1")) {
							System.out.println("Continue on your fighting enemies!");
						} else if (input.equals("2")) {
							player.customBottles();
						} else if (input.equals("3")) {
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
				System.out.println("Player is Dead due to no health remaining.");
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

	@Override
	public void startNewGame() {
		try {
			playerService.delete(player);
		} catch (Exception e) {
			logger.error("Exception in deleting the player :{}", player);
			throw e;
		}
	}

	@Override
	public boolean saveGame() {
		try {
			playerService.save(player);
			return true;
		} catch (Exception e) {
			logger.error("Exception in saving the player status:{}", player);
			throw e;
		}
	}

	@Override
	public boolean loadGame() {

		try {
			Player loadedPlayer = playerService.findByPlayerAndGameName(player);
			if (loadedPlayer != null) {
				this.player = loadedPlayer;
				System.out.println("Player details loaded: " + player.getName());
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

	
	@Override
	public void explore() {
		Scanner in = new Scanner(System.in);
		try {
			loop: while (player.isAlive()) {
				loadZoneOptions();
				recommendTheZone();
				String zoneOption = in.nextLine();
				switch (zoneOption) {

				case "1":
					exploreStatus("North");
					break;
				case "2":
					exploreStatus("South");
					break;
				case "3":
					exploreStatus("East");
					break;
				case "4":
					exploreStatus("West");
					break;
				case "5":
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

	protected abstract void recommendTheZone();

	private void exploreStatus(String zoneName) {
		player.addZone(zoneName);
		player.addItem(GameZone.spawnRandomItem());
		System.out.println(" # Hurray !! You have got item in your basket in zone :" + zoneName + ". #");
		player.loadStatus();
	}

}
