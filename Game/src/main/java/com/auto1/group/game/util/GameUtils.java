/**
 * 
 */
package com.auto1.group.game.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.auto1.group.game.acid.GameZoneEntity;
import com.auto1.group.game.acid.ItemEntity;
import com.auto1.group.game.acid.PlayerEntity;
import com.auto1.group.game.model.actors.Enemy;
import com.auto1.group.game.model.actors.GameLevel;
import com.auto1.group.game.model.actors.GameZone;
import com.auto1.group.game.model.actors.Player;

/**
 * This class serves as utility for Game operations
 *
 */
public class GameUtils {

	public static final String OPTION_5 = "5";
	public static final String OPTION_4 = "4";
	public static final String OPTION_3 = "3";
	public static final String OPTION_2 = "2";
	public static final String OPTION_1 = "1";
	public static final String AVATAR = "Avatar";
	public static final String HARRY_POTTER = "Harry Potter";
	public static final Integer MAX_LEVEL = 3;
	public static final Integer MAX_KILLS = 3;
	public static final Integer MAX_ZONE = 4;
	public static List<String> staticZones = new ArrayList<String>();

	static {
		staticZones.add("North");
		staticZones.add("South");
		staticZones.add("East");
		staticZones.add("West");
	}
	public static final Random rand = new Random();

	public static void delimiter() {
		System.out.println("--------------------------");
	}

	/**
	 * Create levels of static data
	 * 
	 * @return
	 */
	public static List<GameLevel> createLevels() {
		List<GameLevel> levels = new ArrayList<>();
		for (int i = 1; i <= MAX_LEVEL; i++) {
			GameLevel level = new GameLevel();
			level.setNo_of_kills(MAX_KILLS);
			List<Enemy> enemiesList = new ArrayList<>();
			for (int j = 1; j <= MAX_KILLS; j++) {
				enemiesList.add(Enemy.spawnRandomEnemy());
			}
			level.setEnemies(enemiesList);
			level.setLevelNumber(i);
			levels.add(level);
		}
		return levels;
	}

	/**
	 * creates zones of static data
	 * 
	 * @return
	 */
	public static List<GameZone> createZones() {

		List<GameZone> zones = new ArrayList<>();

		for (String zoneName : staticZones) {
			GameZone gameZone = new GameZone();
			gameZone.setZoneName(zoneName);
			gameZone.setVisitedCount(0); // initial visited is zero
			zones.add(gameZone);
		}
		return zones;
	}

	/**
	 * transforms entity to player
	 * 
	 * @param entity
	 * @return
	 */
	public static Player transformToPlayer(PlayerEntity entity) {

		Player player = new Player();
		player.setPlayerId(entity.getId());
		player.setPassword(entity.getPassword());
		player.setCurrentLevel(entity.getCurrentLevel());
		player.setGameName(entity.getGameName());
		player.setHealth(entity.getHealth());
		player.setHealthBottles(entity.getHealthBottles());
		for (ItemEntity itemEntity : entity.getItems()) {
			player.addItem(itemEntity.getItemName(), itemEntity.getItemCount());
		}

		for (GameZoneEntity gameZoneEntity : entity.getGameZones()) {
			player.addZone(gameZoneEntity.getZoneName(), gameZoneEntity.getVisitedCount());
		}
		player.setName(entity.getName());
		player.setScore(entity.getScore());
		return player;
	}

	/**
	 * transforms player to player entity
	 * 
	 * @param player
	 * @return
	 */
	public static PlayerEntity transformToPlayerEntity(Player player) {

		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setId(player.getPlayerId());
		playerEntity.setCurrentLevel(player.getCurrentLevel());
		playerEntity.setGameName(player.getGameName());
		playerEntity.setHealth(player.getHealth());
		playerEntity.setHealthBottles(player.getHealthBottles());
		if (player.getItems() != null) {
			for (String itemName : player.getItems().keySet()) {
				playerEntity.addItemEntity(itemName, player.getItems().get(itemName), playerEntity);
			}
		}

		if (player.getZones() != null) {
			for (String zoneName : player.getZones().keySet()) {
				playerEntity.addZoneEntity(zoneName, player.getZones().get(zoneName), playerEntity);
			}
		}

		playerEntity.setName(player.getName());
		playerEntity.setPassword(player.getPassword());
		playerEntity.setScore(player.getScore());
		return playerEntity;
	}
}
