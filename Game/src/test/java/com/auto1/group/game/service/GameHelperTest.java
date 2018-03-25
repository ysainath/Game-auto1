package com.auto1.group.game.service;

import static com.auto1.group.game.util.GameUtils.OPTION_1;
import static com.auto1.group.game.util.GameUtils.OPTION_3;
import static com.auto1.group.game.util.GameUtils.OPTION_5;
import static org.junit.contrib.java.lang.system.TextFromStandardInputStream.emptyStandardInputStream;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.TextFromStandardInputStream;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import com.auto1.group.game.factory.GameFactory;
import com.auto1.group.game.interfaces.impl.AvatarGame;
import com.auto1.group.game.interfaces.impl.HarryPotterGame;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.repo.PlayerRepository;
import com.auto1.group.game.util.ValidationService;

/**
 * Integration Tests of Game
 * 
 * Used external library System-rules
 * {@link http://stefanbirkner.github.io/system-rules/#TextFromStandardInputStream}
 * for taking standard inputs from console
 * 
 * @author yelsa03
 *
 */
@RunWith(SpringRunner.class)
public class GameHelperTest {

	@TestConfiguration
	static class GameHelperTestContextConfiguration {

		@Bean
		public GameFactory gameFactory() {
			return new GameFactory();
		}

		@Bean
		public AvatarGame avatarGame() {
			return new AvatarGame();
		}

		@Bean
		public HarryPotterGame harryPotter() {
			return new HarryPotterGame();
		}

		@Bean
		public PlayerService playerService() {
			return new PlayerServiceImpl();
		}
	}

	@InjectMocks
	private GameHelper gameHelper;
	@Autowired
	private GameFactory gameFactory;

	@MockBean
	private PlayerRepository playerRepository;

	@MockBean
	private ValidationService validationService;

	@Rule
	public final TextFromStandardInputStream systemInMock = emptyStandardInputStream();
	private final static String AVATAR_GAME = "1";
	private final static String HARRY_POTTER_GAME = "2";

	@Before
	public void before() {
		MockitoAnnotations.initMocks(this);
		Mockito.when(validationService.verifyNameAlreadyRegistered(Mockito.any(Player.class))).thenReturn(true);
		gameHelper.setGameFactory(gameFactory);
	}

	@Test
	public void testAvatarGameIntegrationExplore() throws Throwable {
		systemInMock.provideLines(OPTION_1, "sad", "sad", AVATAR_GAME, OPTION_1, OPTION_1, OPTION_1, OPTION_5, OPTION_3,
				OPTION_3, OPTION_3);
		gameHelper.run();
	}

	@Test
	public void testHarryPotterGameIntegration() throws Throwable {
		systemInMock.provideLines(OPTION_1, "sad", "sad", HARRY_POTTER_GAME, OPTION_1, OPTION_1, OPTION_1, OPTION_5,
				OPTION_3, OPTION_3, OPTION_3);
		gameHelper.run();
	}

}
