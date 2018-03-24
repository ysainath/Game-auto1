/**
 * 
 */
package com.auto1.group.game.factory;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import com.auto1.group.game.interfaces.Game;
import com.auto1.group.game.interfaces.impl.AvatarGame;
import com.auto1.group.game.interfaces.impl.HarryPotterGame;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.util.GameUtils;

/**
 * This class follows Factory patterns to produce objects
 * 
 * @author yelsa03
 *
 */
@Service
public class GameFactory implements ApplicationContextAware {

	private ApplicationContext context;
	public static final String AVATAR_GAME_NAME = "Avatar";
	public static final String HARRY_GAME_NAME = "Harry Potter";

	/**
	 * This method accepts player and constructs game of specific type
	 * 
	 * @param player
	 * @return
	 */
	public Game getGame(Player player) {

		if (GameUtils.AVATAR.equals(player.getGameName())) {
			AvatarGame game = (AvatarGame) context.getBean(AvatarGame.class);
			game.setPlayer(player);
			game.setGameName(player.getGameName());
			return game;
		} else if (GameUtils.HARRY_POTTER.equals(player.getGameName())) {
			HarryPotterGame game = (HarryPotterGame) context.getBean(HarryPotterGame.class);
			game.setPlayer(player);
			game.setGameName(player.getGameName());
			return game;
		}
		throw new UnsupportedOperationException("Invalid game came as parameter :" + player.getGameName());

	}

	/**
	 * Injects Application context
	 */
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		this.context = context;
	}

}
