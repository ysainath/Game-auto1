/**
 * 
 */
package com.auto1.group.game.interfaces.impl;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import com.auto1.group.game.interfaces.AbstractGame;

/**
 * This is implementation of Abstract Game .This class is lazily loaded based
 * on @Lazy annotation
 * 
 * @author yelsa03
 *
 */
@Component
@Lazy
public class HarryPotterGame extends AbstractGame {

	@PostConstruct
	public void postConstruct() {
		setLevels(loadDefaultLevels());
		setZones(loadDefaultZones());
	}

	@Override
	public void startNewGame() {
		super.startNewGame();

	}

	@Override
	public boolean saveGame() {
		return super.saveGame();
	}

	@Override
	public boolean loadGame() {
		return super.loadGame();
	}

	@Override
	public void takeFight() {
		super.takeFight();
	}

	@Override
	public void explore() {
		super.explore();

	}

	@Override
	protected void recommendTheZone() {

	}
}
