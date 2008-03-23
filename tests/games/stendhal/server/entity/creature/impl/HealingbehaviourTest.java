package games.stendhal.server.entity.creature.impl;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertTrue;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.creature.Creature;
import games.stendhal.server.maps.MockStendlRPWorld;

import org.junit.Test;

import utilities.RPClass.CreatureTestHelper;

public class HealingbehaviourTest {
	static boolean called;

	@Test
	public void testHealNonHealer() {
		called = false;
		MockStendlRPWorld.get();
		StendhalRPZone zone = new StendhalRPZone("blabl");
		CreatureTestHelper.generateRPClasses();
		Creature creature = new Creature() {
			@Override
			public void healSelf(int amount, int frequency) {
				called = true;
			}
		};
		zone.add(creature);
		creature.setBaseHP(100);
		creature.setHP(50);
		creature.setHealer(null);
		creature.logic();
		assertThat(creature.getHP(), is(50));
		creature.setHealer("10,1");
		creature.logic();
		assertTrue(called);

	}

	@Test
	public void testGet() {
		assertThat(Healingbehaviour.get(null), instanceOf(NonHealingBehaviour.class));
		assertThat(Healingbehaviour.get("5,5"), instanceOf(Healer.class));
	}

	@Test(expected = NumberFormatException.class)
	public void testGetEmptyString() {
		assertThat(Healingbehaviour.get(""), instanceOf(NonHealingBehaviour.class));
	}

}
