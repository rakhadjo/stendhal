/***************************************************************************
 *                     Copyright © 2020 - Arianne                          *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.maps.nalwor.forest;

import java.awt.Rectangle;

import games.stendhal.common.Level;
import games.stendhal.server.core.config.zone.NoTeleportIn;
import games.stendhal.server.core.engine.StendhalRPZone;
import games.stendhal.server.entity.player.Player;
import games.stendhal.server.util.Area;


/**
 * Representation of an area where a player can train.
 */
public class TrainingArea extends Area {

	private static final int MAX_LEVEL = Level.LEVELS - 1;


	public TrainingArea(final StendhalRPZone zone, final Rectangle shape) {
		super(zone, shape);

		new NoTeleportIn().configureZone(zone, shape);
	}

	public TrainingArea(final StendhalRPZone zone, final int x, final int y, final int width, final int height) {
		this(zone, new Rectangle(x, y, width, height));
	}


	/**
	 * Checks if a player qualifies for training.
	 *
	 * @param player
	 * 		Player to calculate cap for.
	 * @param statLevel
	 * 		Stat to compare cap against.
	 * @return
	 * 		<code>true</code> if the player's stat/level is too high to train.
	 */
	public boolean meetsLevelCap(final Player player, final int statLevel) {
		final int levelCap = calculateLevelCap(player);
		if (levelCap < 0) {
			return false;
		}

		return statLevel >= levelCap;
	}

	/**
	 * Calculates the level cap at which a player cannot train.
	 *
	 * @param player
	 * 		Player to be checked.
	 * @return
	 * 		Capped level for training.
	 */
	private int calculateLevelCap(final Player player) {
		final int level = player.getLevel();

		// no limit at max level
		if (level >= MAX_LEVEL) {
			return -1;
		}

		if (level <= 50) {
			return level;
		}

		return level / 5 + 50;
	}
}
