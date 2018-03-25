/**
 * 
 */
package com.auto1.group.game.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.service.PlayerService;

/**
 * This class is responsible for validation performed across game operations
 *
 */
@Service
public class ValidationService {

	@Autowired
	private PlayerService playerService;
	
	public void setPlayerService(PlayerService playerService) {
		this.playerService = playerService;
	}

	/**
	 * verifies character is unique or not
	 * 
	 * @param player
	 * @return
	 */
	public boolean verifyNameAlreadyRegistered(Player player) {

		return playerService.findPlayerByName(player.getName()) == null;
	}

	/**
	 * authenticates player
	 * 
	 * @param player
	 * @return
	 */
	public boolean authenticate(Player player) {

		Player dbPlayer = playerService.findByPlayerAndGameName(player);
		if (dbPlayer != null) {
			if (dbPlayer.getPassword().equals(player.getPassword())) {
				return true;
			}
		}
		return false;
	}
	
}
