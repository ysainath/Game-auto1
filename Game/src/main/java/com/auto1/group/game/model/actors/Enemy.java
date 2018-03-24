/**
 * 
 */
package com.auto1.group.game.model.actors;

/**
 * This Class extends Actor and acts as Enemy entity
 *
 */
public class Enemy extends Actor {

	private static final String[] ENEMIES = { "Skeleton", "Zombie", "Warrior", "Assassin" };
	private static final int MAX_HEALTH = 50;
	private static final int DAMAGE = 25;

	public static Enemy spawnRandomEnemy() {
		return new Enemy(ENEMIES[rand.nextInt(ENEMIES.length)], rand.nextInt(MAX_HEALTH), DAMAGE);
	}

	public Enemy(final String name, final int health, final int damage) {
		super(name, health, damage);
	}

	public void dropHealthBottle(final Player player) {
		if (rand.nextInt(100) < HEALTH_BOTTLE_DROP_PERCENTAGE) {
			System.out.println(" # The " + this + " dropped a health bottle! #");
			System.out
					.println(" # You have " + String.valueOf(player.incrementHealthBottles()) + " health bottle(s). #");
		}
	}

}
