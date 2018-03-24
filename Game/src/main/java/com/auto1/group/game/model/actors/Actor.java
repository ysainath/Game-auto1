/**
 * 
 */
package com.auto1.group.game.model.actors;

import java.util.Random;

/**
 * @author yelsa03
 *
 */
public class Actor {

	protected static final Random rand = new Random();
	protected static final int HEALTH_BOTTLE_HEAL_AMOUNT = 30;
	protected static final int HEALTH_BOTTLE_DROP_PERCENTAGE = 50;

	protected String name;
	protected int health;
	protected int damage;

	public Actor() {
	}

	public Actor(final String name, final int health, final int damage) {
		this.name = name;
		this.health = health;
		this.damage = damage;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

	public void attack(final Actor actor) {
		final int damage = getDamage();
		if (this instanceof Player) {
			System.out.println("\t> You strike the " + actor + " for " + damage + " damage.");
		} else {
			System.out.println("\t> The " + this + " hits you for " + damage + ".");
		}
		actor.damage(damage);
	}

	public void damage(final int damage) {
		health -= damage;
	}

	public int getDamage() {
		return rand.nextInt(damage <= 0 ? 10 : damage);
	}

	public boolean isAlive() {
		return health > 0;
	}

	public boolean isDying() {
		return !isAlive();
	}

	@Override
	public String toString() {
		return name;
	}

}
