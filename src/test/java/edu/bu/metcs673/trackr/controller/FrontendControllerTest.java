package edu.bu.metcs673.trackr.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class FrontendControllerTest {
	private final FrontendController controller = new FrontendController();

	@Test
	public void testIndexTemplate() {
		Assertions.assertEquals("index", controller.index());
	}
}
