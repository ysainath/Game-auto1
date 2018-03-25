/**
 * 
 */
package com.auto1.group.game.service;

import static com.auto1.group.game.util.GameUtils.OPTION_1;
import static com.auto1.group.game.util.GameUtils.OPTION_2;
import static com.auto1.group.game.util.GameUtils.OPTION_3;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auto1.group.game.factory.GameFactory;
import com.auto1.group.game.interfaces.Game;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.util.GameExceptionHandler;
import com.auto1.group.game.util.GameUtils;
import com.auto1.group.game.util.ValidationService;

/**
 * This acts as helper class for Game operations Performs authentication of a
 * player Validates uniqueness if character creation
 * 
 * Follows proxy pattern for all game operations
 *
 */
@Service
public class GameHelper {

	@Autowired
	private GameFactory gameFactory;

	@Autowired
	private ValidationService validationService;

	public void setGameFactory(GameFactory gameFactory) {
		this.gameFactory = gameFactory;
	}

	public void setValidationService(ValidationService validationService) {
		this.validationService = validationService;
	}

	private Logger logger = LoggerFactory.getLogger(GameHelper.class);

	/**
	 * authenticates if player is allowed
	 * 
	 * @param player
	 * @return
	 */
	public boolean authenticate(Player player) {
		System.out.println("### Authenticating your profile...###");
		return validationService.authenticate(player);
	}

	/**
	 * validates uniquenesss of character
	 * 
	 * @param player
	 * @return
	 */
	public boolean validateUniqueness(Player player) {
		System.out.println("### Verifying uniqueness of your character name...###");
		return validationService.verifyNameAlreadyRegistered(player);
	}

	/**
	 * Entry point of game and runs continuously unless the player dies
	 * 
	 */
	public void run() {
		try (final Scanner in = new Scanner(System.in)) {
			System.out.println("#####  Welcome to Multi Player Game ##########");
			try {
				loop: while (true) {
					System.out.println("\n\nSelect below options to proceed.");
					System.out.println("1.Create a character.");
					System.out.println("2.Do you already know your registered Character details?");
					System.out.println("3.Exit");
					String characterOption = in.nextLine();
					switch (characterOption) {
					case OPTION_1:
						if (playGame(true))
							return;
						break;
					case OPTION_2:
						if (playGame(false))
							return;
						break;
					case OPTION_3:
						break loop;
					default:
						System.out.println("Invalid command");
						continue;
					}
				}
			} catch (Exception e) {
				GameExceptionHandler.handleException(e);
			}
		}

	}

	private boolean playGame(boolean newProfile) {
		try (final Scanner in = new Scanner(System.in)) {
			loop: while (true) {
				String playerName = null, password = null;
				boolean isNotEmpty = false;
				while (!isNotEmpty) {
					System.out.println("Enter Character Name:");
					playerName = in.nextLine();
					System.out.println("Enter Password:");
					password = in.nextLine();
					if (StringUtils.isNotEmpty(playerName) && StringUtils.isNotEmpty(password)) {
						isNotEmpty = true;
					} else {
						System.out.println("Details should not be empty");
					}
				}

				Player player = new Player(playerName, 100, 25);
				player.setPassword(password);
				GameUtils.delimiter();
				System.out.println("Hi " + playerName + " !! Select Game to Play:");
				System.out.println("1.Avatar");
				System.out.println("2.Harry Potter");
				System.out.println("3.Exit");
				String gameChoice = in.nextLine();
				logger.info("Player :{} selected game choice :{}", player, gameChoice);
				switch (gameChoice) {
				case OPTION_1:
					player.setGameName(GameUtils.AVATAR);
					if (registerGame(newProfile, player))
						return true;
					break;
				case OPTION_2:
					player.setGameName(GameUtils.HARRY_POTTER);
					if (registerGame(newProfile, player))
						return true;
					break;
				case OPTION_3:
					break loop;
				default:
					System.out.println("Invalid command");
					continue;
				}

			}
		}
		return true;
	}

	private boolean registerGame(boolean newProfile, final Player player) {
		Game game = gameFactory.getGame(player);
		boolean isValid;
		if (!newProfile) {
			isValid = this.authenticate(player);
			if (!isValid) {
				System.out.println("Invalid details found for this character name !!.");
				return false;
			}
		} else {
			isValid = this.validateUniqueness(player);
			if (!isValid) {
				System.out.println("Character already exists .Enter different character name !!!");
				return false;
			}
		}
		final Scanner in = new Scanner(System.in);
		loop: while (true) {
			game.loadGameOptions();
			String initGameChoice = in.nextLine();
			logger.info("Player selected choice :{}", initGameChoice);
			switch (initGameChoice) {
			case OPTION_1:// start a new game
				game.startNewGame();
				initGame(in, game);
				break;
			case OPTION_2:// load saved game
				game.loadGame();
				initGame(in, game);
				break;
			case OPTION_3:// exit
				return true;
			default:
				System.out.println("Invalid command");
				continue;
			}
		}

	}

	private void initGame(final Scanner in, final Game game) {
		try {

			loop: while (true) {
				game.loadPlayerActivitiesOptions();
				String activityOption = in.nextLine();
				switch (activityOption) {
				case OPTION_1: // explore
					game.explore();
					break;
				case OPTION_2: // take fight
					game.takeFight();
					break;
				case OPTION_3:// save & exit
					// System.exit(0);
					break loop;
				default:
					System.out.println("Invalid command");
					continue;
				}
			}

		} catch (Exception e) {
			if (in != null) {
				in.close();
			}
			throw e;
		}
	}

}
