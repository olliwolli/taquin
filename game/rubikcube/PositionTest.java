package game.rubikcube;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class PositionTest {

	@Test
	public void testTurnDistanceIntIntIntInt() {
		assertEquals(2, Position.turnDistance(5, -85, 5, -5));
		assertEquals(1, Position.turnDistance(5, -95, 5, -5));

	}

}
