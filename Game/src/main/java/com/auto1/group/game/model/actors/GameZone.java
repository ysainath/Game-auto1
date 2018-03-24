/**
 * 
 */
package com.auto1.group.game.model.actors;

import com.auto1.group.game.util.GameUtils;

/**
 * This class holds Game zones to explore and can be created by Game
 * implementations
 *
 */
public class GameZone {

	private static final String[] ITEMS = { "Food", "Currency", "Weapon" };

	public static String spawnRandomItem() {
		return ITEMS[GameUtils.rand.nextInt(ITEMS.length)];
	}

	/**
	 * Zone name
	 */
	private String zoneName;
	/**
	 * No of time the zone is visited and can be used in recommendations
	 */
	private Integer visitedCount = 0; // used for self learning/re-commondation

	public String getZoneName() {
		return zoneName;
	}

	public void setZoneName(String zoneName) {
		this.zoneName = zoneName;
	}

	public Integer getVisitedCount() {
		return visitedCount;
	}

	public void setVisitedCount(Integer visitedCount) {
		this.visitedCount = visitedCount;
	}

}
