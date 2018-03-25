package com.auto1.group.game;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * This test validates if app context got loaded
 * @author yelsa03
 *
 */
@RunWith(SpringRunner.class)
public class GameApplicationTests {

	@Autowired
	private ApplicationContext ctx;

	@Test
	public void contextLoads() {
		Assert.assertTrue(ctx != null);
	}

}
