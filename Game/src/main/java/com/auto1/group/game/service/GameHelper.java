/**
 * 
 */
package com.auto1.group.game.service;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.auto1.group.game.factory.GameFactory;
import com.auto1.group.game.interfaces.Game;
import com.auto1.group.game.model.actors.Player;
import com.auto1.group.game.util.GameExceptionHandler;
import com.auto1.group.game.util.GameUtils;
import com.auto1.group.game.util.ValidationService;

/**
 * @author yelsa03
 *
 */
@Service
public class GameHelper {

	@Autowired
	private GameFactory gameFactory;

	@Autowired
	private ValidationService validationService;

	public boolean authenticate(Player player) {
		System.out.println("### Authenticating your profile...###");
		return validationService.authenticate(player);
	}

	public boolean validateUniqueness(Player player) {
		System.out.println("### Verifiying uniqueness of your character name...###");
		return validationService.verifyNameAlreadyRegistered(player);
	}

	public void run() {
		try (final Scanner in = new Scanner(System.in)) {
			System.out.println("#####  Welcome to Multi Player Game ##########");
			while (true) {
				try {
					System.out.println("\n\nSelect below options to proceed.");
					System.out.println("1.Create a character.");
					System.out.println("2.Do you already know your registered Character details?");

					String characterOption = in.nextLine();
					switch (characterOption) {
					case "1":
						playGame(true);
						break;
					case "2":
						playGame(false);
						break;
					default:
						System.out.println("Invalid command");
						continue;
					}
				} catch (Exception e) {
					GameExceptionHandler.handleException(e);
				}
			}
		}
	}

	private void playGame(boolean newProfile) {
		try (final Scanner in = new Scanner(System.in)) {
			while (true) {
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
				switch (gameChoice) {
				case "1":
					player.setGameName(GameUtils.AVATAR);
					registerGame(newProfile, player);
					break;
				case "2":
					player.setGameName(GameUtils.HARRY_POTTER);
					registerGame(newProfile, player);
					break;
				case "3":
					break;
				default:
					System.out.println("Invalid command");
					continue;
				}

			}
		}
	}

	private void registerGame(boolean newProfile, final Player player) {
		Game game = gameFactory.getGame(player);
		boolean isValid;
		if (!newProfile) {
			isValid = this.authenticate(player);
			if (!isValid) {
				System.out.println("Invalid details found for this character name !!.");
				return;
			}
		} else {
			isValid = this.validateUniqueness(player);
			if (!isValid) {
				System.out.println("Character already exists .Enter different character name !!!");
				return;
			}
		}
		try (final Scanner in = new Scanner(System.in)) {
			while (true) {
				game.loadGameOptions();
				String initGameChoice = in.nextLine();
				switch (initGameChoice) {
				case "1":// start a new game
				{
					game.startNewGame();
					initGame(in, game);
					break;
				}
				case "2":// load saved game
					game.loadGame();
					initGame(in, game);
					break;
				default:
					System.out.println("Invalid command");
					continue;
				}
			}
		}

	}

	private void initGame(final Scanner in, final Game game) {
		try {

			loop: while (true) {
				game.loadPlayerActivitiesOptions();
				String activityOption = in.nextLine();
				switch (activityOption) {
				case "1": // explore
					game.explore();
					break;
				case "2": // take fight
					game.takeFight();
					break;
				case "3":// save & exit
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
