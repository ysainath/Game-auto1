/**
 * 
 */
package com.auto1.group.game.factory;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.auto1.group.game.interfaces.Game;
import com.auto1.group.game.interfaces.impl.AvatarGame;
import com.auto1.group.game.interfaces.impl.HarryPotterGame;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.service.PlayerService;
import com.auto1.group.game.util.GameUtils;

/**
 * @author yelsa03
 *
 */
@RunWith(SpringRunner.class)
public class GameFactoryTest {

	@TestConfiguration
	static class GameFactoryTestContextConfiguration {

		@Bean
		public AvatarGame avatarGame() {
			return new AvatarGame();
		}

		@Bean
		public HarryPotterGame harryPotter() {
			return new HarryPotterGame();
		}

	}

	@Autowired
	private ApplicationContext ctx;

	@MockBean
	private PlayerService PlayerService;

	@Test
	public void testAvatarGameObject() {
		GameFactory factory = new GameFactory();
		factory.setApplicationContext(ctx);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getGameName()).thenReturn(GameUtils.AVATAR);
		Game game = factory.getGame(player);
		Assert.assertTrue(game instanceof AvatarGame);
	}

	@Test
	public void testHarryPotterGameObject() {
		GameFactory factory = new GameFactory();
		factory.setApplicationContext(ctx);
		Player player = Mockito.mock(Player.class);
		Mockito.when(player.getGameName()).thenReturn(GameUtils.HARRY_POTTER);
		Game game = factory.getGame(player);
		Assert.assertTrue(game instanceof HarryPotterGame);
	}

}
