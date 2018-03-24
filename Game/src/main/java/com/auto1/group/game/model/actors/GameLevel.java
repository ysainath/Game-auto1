/**
 * 
 */
package com.auto1.group.game.model.actors;

import java.util.List;

/**
 * @author yelsa03
 *
 */
public class GameLevel {

	private Integer levelNumber;
	private Integer no_of_kills;
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
