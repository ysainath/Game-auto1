/**
 * 
 */
package com.auto1.group.game.model.actors;

import com.auto1.group.game.util.GameUtils;

/**
 * @author yelsa03
 *
 */
public class GameZone {

	private static final String[] ITEMS = { "Food", "Currency", "Weapon" };

	public static String spawnRandomItem() {
		return ITEMS[GameUtils.rand.nextInt(ITEMS.length)];
	}

	private String zoneName;
	private Integer visitedCount = 0; // used for self learning/re-commondation

	public GameZone() {
	}

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
