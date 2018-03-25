/**
 * 
 */
package com.auto1.group.game.util;

/**
 * Exception handler for Game application
 *
 */
public class GameExceptionHandler {

	/**
	 * Handle exception generically . Either write to file or print on console
	 * 
	 * @param e
	 */
	public static void handleException(Throwable e) {
		e.printStackTrace();
	}
}
