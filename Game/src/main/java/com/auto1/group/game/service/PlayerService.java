/**
 * 
 */
package com.auto1.group.game.service;

import com.auto1.group.game.model.actors.Player;

/**
 * @author yelsa03
 *
 */
public interface PlayerService {

	public Player findPlayerByName(String playerName);

	public Player findByPlayerAndGameName(Player player);
	
	public void save(Player p);
	
	public void delete(Player p);

}
