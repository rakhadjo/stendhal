/***************************************************************************
 *                   (C) Copyright 2019 - Arianne                          *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.quests;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import games.stendhal.server.entity.npc.SpeakerNPC;
import games.stendhal.server.maps.ados.animal_sanctuary.ZoologistNPC;
import games.stendhal.server.maps.semos.apothecary_lab.ApothecaryNPC;
import utilities.QuestHelper;
import utilities.ZonePlayerAndNPCTestImpl;

public class AntivenomRingTest extends ZonePlayerAndNPCTestImpl {
	private static final String ZONE_NAME = "testzone";

	private SpeakerNPC apothecary;
	private SpeakerNPC zoologist;

	private final String questName = "antivenom_ring";
	private final String subquestName = questName + "_extract";

	public AntivenomRingTest() {
		setZoneForPlayer(ZONE_NAME);
		setNpcNames("Jameson", "Zoey");
		addZoneConfigurator(new ApothecaryNPC(), ZONE_NAME);
		addZoneConfigurator(new ZoologistNPC(), ZONE_NAME);
	}

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		QuestHelper.setUpBeforeClass();
		setupZone(ZONE_NAME);
	}

	@Override
	@Before
	public void setUp() throws Exception {
		super.setUp();

		apothecary = getNPC("Jameson");
		zoologist = getNPC("Zoey");

		// initialize quest
		// FIXME: SpeakerNPC instances related to quest are null
		//new AntivenomRing().addToWorld();
	}

	@Test
	public void testQuest() {
		testEntities();
		testQuestNotActive();
	}

	private void testEntities() {
	}

	private void testQuestNotActive() {
	}
}
