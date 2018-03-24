/**
 * 
 */
package com.auto1.group.game.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auto1.group.game.acid.PlayerEntity;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.repo.PlayerRepository;
import com.auto1.group.game.util.GameUtils;

/**
 * @author yelsa03
 *
 */
@Service
@Transactional
public class PlayerServiceImpl implements PlayerService {

	@Autowired
	private PlayerRepository playerRepo;

	@Transactional(readOnly = true)
	public Player findPlayerByName(String playerName) {

		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setName(playerName);
		Example<PlayerEntity> example = Example.of(playerEntity);
		playerEntity = playerRepo.findOne(example);
		if (playerEntity == null) {
			return null;
		}
		Player player = GameUtils.transformToPlayer(playerEntity);
		return player;
	}

	@Transactional(readOnly = true)
	public Player findByPlayerAndGameName(Player player) {
		PlayerEntity playerEntity = findPlayerEntity(player);
		if (playerEntity == null) {
			return null;
		}
		Player transformed = GameUtils.transformToPlayer(playerEntity);
		return transformed;
	}

	public void save(Player p) {
		if (p != null) {

			PlayerEntity playerEntity = findPlayerEntity(p);
			if (playerEntity == null) {
				playerRepo.save(GameUtils.transformToPlayerEntity(p));
			} else {
				p.setPlayerId(playerEntity.getId());
				playerRepo.save(GameUtils.transformToPlayerEntity(p));
			}

		}

	}

	/**
	 * @param p
	 * @return
	 */
	private PlayerEntity findPlayerEntity(Player p) {
		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setName(p.getName());
		playerEntity.setGameName(p.getGameName());
		Example<PlayerEntity> example = Example.of(playerEntity);
		playerEntity = playerRepo.findOne(example);
		return playerEntity;
	}

	public void delete(Player p) {
		PlayerEntity playerEntity = findPlayerEntity(p);
		if (playerEntity != null) {
			playerRepo.delete(playerEntity.getId());
		}
	}

}
