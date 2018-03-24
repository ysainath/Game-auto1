/**
 * 
 */
package com.auto1.group.game.util;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.service.PlayerService;

/**
 * @author yelsa03
 *
 */
@Service
public class ValidationService {

	@Autowired
	private PlayerService playerService;

	public boolean verifyNameAlreadyRegistered(Player player) {

		return playerService.findPlayerByName(player.getName()) == null;
	}

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
