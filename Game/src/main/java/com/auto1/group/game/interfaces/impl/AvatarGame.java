/**
 * 
 */
package com.auto1.group.game.interfaces.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.auto1.group.game.interfaces.AbstractGame;
import com.auto1.group.game.model.actors.GameZone;

/**
 * This is implementation of Abstract Game .This class is lazily loaded based
 * on @Lazy annotation
 * 
 * @author yelsa03
 *
 */
@Component
@Lazy
public class AvatarGame extends AbstractGame {

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
	protected List<GameZone> loadDefaultZones() {
		return super.loadDefaultZones();
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

		Map<String, Integer> playerZones = this.player.getZones();
		if (!CollectionUtils.isEmpty(playerZones)) {
			int maxVisitedZone = 0;
			String maxZoneName = "";
			for (String zoneName : playerZones.keySet()) {
				Integer value = playerZones.get(zoneName);
				if (value > 2 && value > 0) {
					maxZoneName = zoneName;
					maxVisitedZone = value;
				}
			}
			if (maxVisitedZone > 2) {
				System.out
						.println("Based on history, you have visited " + maxZoneName + " " + maxVisitedZone + " times");
				System.out.println("### Recommended to explore through this to collect more items in your basket. ###");
			}

		}

	}

}
