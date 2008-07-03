package games.stendhal.server.actions;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import games.stendhal.server.core.engine.SingletonRepository;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.item.Seed;
import games.stendhal.server.entity.mapstuff.spawner.FlowerGrower;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.maps.MockStendlRPWorld;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import utilities.PlayerTestHelper;
import utilities.RPClass.GrowingPassiveEntityRespawnPointTestHelper;

public class PlantActionTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		MockStendlRPWorld.get();
		GrowingPassiveEntityRespawnPointTestHelper.generateRPClasses();
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testExecuteWithNUllValues() {
		PlantAction plantAction = new PlantAction();
		Player player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(player);
		Seed seed = (Seed) SingletonRepository.getEntityManager().getItem("seed");
		assertNotNull(seed);
		assertFalse(plantAction.execute());

		plantAction.setUser(player);
		plantAction.setSeed(null);
		assertFalse(plantAction.execute());

		plantAction.setUser(null);
		plantAction.setSeed(seed);
		assertFalse(plantAction.execute());

	}

	@Test
	public void testExecute() {
		PlantAction plantAction = new PlantAction();
		Player player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(player);
		StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);

		Seed seed = (Seed) SingletonRepository.getEntityManager().getItem("seed");
		assertNotNull(seed);
		zone.add(seed);
		seed.setPosition(1, 0);

		plantAction.setUser(player);
		plantAction.setSeed(seed);
		assertTrue(plantAction.execute());

		assertNotNull(player.getZone().getEntityAt(1, 0));
		assertTrue(player.getZone().getEntityAt(1, 0) instanceof FlowerGrower);

	}
	

	@Test
	public void testExecuteSeedInBag() {
		PlantAction plantAction = new PlantAction();
		Player player = PlayerTestHelper.createPlayer("bob");
		assertNotNull(player);
		StendhalRPZone zone = new StendhalRPZone("zone");
		SingletonRepository.getRPWorld().addRPZone(zone);
		zone.add(player);
		
		Seed seed = (Seed) SingletonRepository.getEntityManager().getItem("seed");
		assertNotNull(seed);
		player.equip("bag", seed);
		
		plantAction.setUser(player);
		plantAction.setSeed(seed);
		assertFalse(plantAction.execute());

		
	}

}
