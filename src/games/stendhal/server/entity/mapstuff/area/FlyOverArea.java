/***************************************************************************
 *                   (C) Copyright 2018 - Arianne                          *
 ***************************************************************************
 ***************************************************************************
 *                                                                         *
 *   This program is free software; you can redistribute it and/or modify  *
 *   it under the terms of the GNU General Public License as published by  *
 *   the Free Software Foundation; either version 2 of the License, or     *
 *   (at your option) any later version.                                   *
 *                                                                         *
 ***************************************************************************/
package games.stendhal.server.entity.mapstuff.area;

import games.stendhal.server.entity.Entity;
import games.stendhal.server.entity.RPEntity;
import marauroa.common.game.Definition.Type;
import marauroa.common.game.RPClass;

/**
 * An entity that acts as an obstacle for "walking" entities.
 *
 * TODO: Implement with multi-level collision.
 * TODO: Allow entities with a "flying" attribute to pass.
 * FIXME: Players should not be able to set items in this area
 *        as is done with WalkBlocker.
 */
public class FlyOverArea extends AreaEntity {
	/**
	 * Create an area that can be "flown" over.
	 */
	public FlyOverArea() {
		super(1, 1);

		setRPClass("flyover");
		put("type", "flyover");

		// Count as collision for the client and pathfinder
		setResistance(100);
	}

	public static void generateRPClass() {
		final RPClass flyover = new RPClass("flyover");
		flyover.isA("area");
		flyover.addAttribute("class", Type.STRING);
	}

	@Override
	public boolean isObstacle(final Entity entity) {
		if (entity instanceof RPEntity) {
			return true;
		}

		return super.isObstacle(entity);
	}
}
