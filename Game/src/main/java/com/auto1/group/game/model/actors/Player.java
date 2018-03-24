/**
 * 
 */
package com.auto1.group.game.model.actors;

import java.util.HashMap;
import java.util.Map;

import com.auto1.group.game.util.GameUtils;

/**
 * @author yelsa03
 *
 */
public class Player extends Actor {

	private Long playerId;
	private Integer healthBottles = 0;
	private Integer score = 0;
	private Integer currentLevel = 0;
	private String gameName;
	private Map<String, Integer> items;
	private String password;

	private Map<String, Integer> zones;

	public Player(final String name, final int health, final int damage) {
		super(name, health, damage);
		loadDefaultItems(); // default properties
	}

	private void loadDefaultItems() {
		this.healthBottles = 3;
		addItem("Food");
		addItem("Weapon");
		addItem("Currency");
	}

	public Player() {
	}

	public Integer getHealthBottles() {
		return healthBottles;
	}

	public void setHealthBottles(Integer healthBottles) {
		this.healthBottles = healthBottles;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public void incrementScore() {
		this.setScore(this.score + 50);
	}

	public Integer getCurrentLevel() {
		return currentLevel;
	}

	public void setCurrentLevel(Integer currentLevel) {
		this.currentLevel = currentLevel;
	}

	public String getGameName() {
		return gameName;
	}

	public void setGameName(String gameName) {
		this.gameName = gameName;
	}

	public void customBottles() {
		if (hasHealthBottles()) {
			if (!hasWeapons()) {
				System.out.println(
						"\t> You have no weapons left! Defeat enemies or explore to collect the items for a chance to get more! \n");
				return;
			}
			health += HEALTH_BOTTLE_HEAL_AMOUNT;
			healthBottles--;
			System.out
					.println("\t> You drink a health bottle, healing yourself for " + HEALTH_BOTTLE_HEAL_AMOUNT + ".");
			System.out.println("\t> You now have " + health + "Health.");
			System.out.println("\t> You have " + healthBottles + " health bottles remaining. \n");
		} else {
			System.out.println(
					"\t> You have no health bottle left! Defeat enemies or explore to collect the items for a chance to get more! \n");
		}
	}

	private boolean hasWeapons() {
		return items.get("Weapon") > 0;
	}

	public void defeat(final Enemy enemy) {
		GameUtils.delimiter();
		System.out.println(" # " + enemy + " was defeated! # ");
		System.out.println(" # You have " + health + " Health Percentage left. #");
		this.incrementScore();
		System.out.println(" # Your score " + score + " .#");
		enemy.dropHealthBottle(this);
	}

	public void loadStatus() {
		System.out.println(" # You have " + health + " Health Percentage left. #");
		System.out.println(" # Your score " + score + " .#");
		System.out.println(" # You have " + healthBottles + " health bottles remaining. #");

		for (String item : items.keySet()) {
			System.out.println(" # Item name: " + item + " Item Count: " + items.get(item) + " .#");
		}
	}

	private boolean hasHealthBottles() {
		return healthBottles > 0;
	}

	@Override
	public String toString() {
		return "Player [healthBottles=" + healthBottles + ", score=" + score + ", currentLevel=" + currentLevel
				+ ", gameName=" + gameName + "]";
	}

	public int incrementHealthBottles() {
		this.setHealthBottles(getHealthBottles() + 1);
		return getHealthBottles();
	}

	public Map<String, Integer> getItems() {
		return items;
	}

	public void addItem(String itemName) {
		if (this.items == null) {
			items = new HashMap<>();
		}
		Integer itemCount = items.get(itemName);
		if (itemCount == null) {
			addItem(itemName, 1);
		} else {
			addItem(itemName, itemCount + 1);
		}

	}

	public void addItem(String itemName, Integer itemCount) {
		if (this.items == null) {
			items = new HashMap<>();
		}
		items.put(itemName, itemCount);

	}

	public Map<String, Integer> getZones() {
		return zones;
	}

	public void addZone(String zoneName) {
		if (this.zones == null) {
			zones = new HashMap<>();
		}
		Integer visitedCount = zones.get(zoneName);
		if (visitedCount == null) {
			addZone(zoneName, 1);
		} else {
			addZone(zoneName, visitedCount + 1);
		}

	}

	public void addZone(String zoneName, Integer visitedCount) {
		if (this.zones == null) {
			zones = new HashMap<>();
		}
		zones.put(zoneName, visitedCount);

	}

	public Long getPlayerId() {
		return playerId;
	}

	public void setPlayerId(Long playerId) {
		this.playerId = playerId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
