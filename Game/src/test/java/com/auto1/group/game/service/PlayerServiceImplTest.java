/**
 * 
 */
package com.auto1.group.game.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.auto1.group.game.acid.PlayerEntity;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.repo.PlayerRepository;
import com.auto1.group.game.util.GameUtils;

/**
 * @author yelsa03
 *
 */
@RunWith(SpringRunner.class)
public class PlayerServiceImplTest {

	@TestConfiguration
	static class PlayerServiceImplTestContextConfiguration {

		@Bean
		public PlayerService playerService() {
			return new PlayerServiceImpl();
		}
	}

	@Autowired
	private PlayerServiceImpl playerService;

	@MockBean
	private PlayerRepository playerRepository;

	@Test
	public void testPlayerNotFound() {

		String playerName = "sainath";
		PlayerEntity playerEntity = new PlayerEntity();
		playerEntity.setName(playerName);
		Example<PlayerEntity> example = Example.of(playerEntity);
		when(playerRepository.findOne(example)).thenReturn(playerEntity);
		Player player = playerService.findPlayerByName(playerName);
		Assert.assertTrue(player == null);

	}

	@SuppressWarnings("unchecked")
	@Test
	public void testPlayerFound() {

		String playerName = "sainath";
		String gameName = "Avatar";
		Player player = new Player();
		player.setName(playerName);
		player.setGameName(gameName);

		PlayerEntity playerEntity = GameUtils.transformToPlayerEntity(player);

		playerService.save(player);

		when(playerRepository.findOne(Mockito.any(Example.class))).thenReturn(playerEntity);
		Player dbPlayer = playerService.findPlayerByName(playerName);
		Assert.assertTrue(dbPlayer != null);

	}

	@Test
	public void testSave() {

		String playerName = "sainath";
		String gameName = "Avatar";
		Player player = new Player();
		player.setName(playerName);
		player.setGameName(gameName);

		playerService.save(player);
		Mockito.verify(playerRepository, times(1)).save(Mockito.any(PlayerEntity.class));

	}

	@Test
	public void testDelete() {

		String playerName = "sainath";
		String gameName = "Avatar";
		Player player = new Player();
		player.setName(playerName);
		player.setGameName(gameName);

		playerService.delete(player);
		Mockito.verify(playerRepository, times(0)).delete(Mockito.any(Long.class));

	}
}
