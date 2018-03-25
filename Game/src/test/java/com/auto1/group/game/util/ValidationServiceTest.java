package com.auto1.group.game.util;

import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Example;
import org.springframework.test.context.junit4.SpringRunner;

import com.auto1.group.game.acid.PlayerEntity;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.repo.PlayerRepository;
import com.auto1.group.game.service.PlayerService;
import com.auto1.group.game.service.PlayerServiceImpl;

@RunWith(SpringRunner.class)
public class ValidationServiceTest {

	@TestConfiguration
	static class PlayerServiceImplTestContextConfiguration {

		@Bean
		public PlayerService playerService() {
			return new PlayerServiceImpl();
		}

		@Bean
		public ValidationService service() {
			return new ValidationService();
		}

	}

	@Autowired
	private PlayerService playerService;

	@Autowired
	private ValidationService validationService;

	@MockBean
	private PlayerRepository playerRepository;

	private Player player;

	@SuppressWarnings("unchecked")
	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);

		String playerName = "sainath";
		String gameName = "Avatar";
		player = new Player();
		player.setName(playerName);
		player.setGameName(gameName);
		player.setPassword("sainath");
		PlayerEntity playerEntity = GameUtils.transformToPlayerEntity(player, null);

		when(playerRepository.findOne(Mockito.any(Example.class))).thenReturn(playerEntity);
		validationService.setPlayerService(playerService);
	}

	@Test
	public void testAuthenticate() {

		Assert.assertTrue(validationService.authenticate(player));
	}

	@Test
	public void testUniqueness() {

		Assert.assertTrue(!validationService.verifyNameAlreadyRegistered(player));

	}
}
