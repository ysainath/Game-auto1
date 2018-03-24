/**
 * 
 */
package com.auto1.group.game.model.actors;

import java.util.List;

/**
 * This class holds the game level and can be created by Game implementations
 *
 */
public class GameLevel {
	/**
	 * level number
	 */
	private Integer levelNumber;
	/**
	 * no of kills pertaining to each level
	 */
	private Integer no_of_kills;
	/**
	 * No of enemies occurs at each level . Killing these enemies at each level
	 * will be proceeding to next level
	 */
	private List<Enemy> enemies;

	public Integer getLevelNumber() {
		return levelNumber;
	}

	public void setLevelNumber(Integer levelNumber) {
		this.levelNumber = levelNumber;
	}

	public Integer getNo_of_kills() {
		return no_of_kills;
	}

	public void setNo_of_kills(Integer no_of_kills) {
		this.no_of_kills = no_of_kills;
	}

	public List<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(List<Enemy> enemies) {
		this.enemies = enemies;
	}

}
