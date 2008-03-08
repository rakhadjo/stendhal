package games.stendhal.client.actions;

import games.stendhal.client.StendhalClient;
import games.stendhal.client.StendhalUI;
import games.stendhal.client.entity.User;
import games.stendhal.common.Constants;
import games.stendhal.common.EquipActionConsts;
import games.stendhal.common.Grammar;
import marauroa.common.game.RPAction;

/**
 * Drop a player item.
 */
class DropAction implements SlashAction {

	/**
	 * Execute a chat command.
	 * 
	 * @param params
	 *            The formal parameters.
	 * @param remainder
	 *            Line content after parameters.
	 * 
	 * @return <code>true</code> if command was handled.
	 */
	public boolean execute(String[] params, String remainder) {
		int quantity;
		String itemName;

		// Is there a numeric expression as first parameter?
		if (params[0].matches("[0-9].*")) {
    		try {
    			quantity = Integer.parseInt(params[0]);
    		} catch (NumberFormatException ex) {
    			StendhalUI.get().addEventLine("Invalid quantity: " + params[0]);
    			return true;
    		}

    		itemName = remainder;
		} else {
			quantity = 1;
			itemName = (params[0] + " " + remainder).trim();
		}

		String singularItemName = Grammar.singular(itemName);

		for (String slotName : Constants.CARRYING_SLOTS) {
			int itemID = User.get().findItem(slotName, itemName);

			// search again using the singular, in case it was a plural item name
			if (itemID == -1 && !itemName.equals(singularItemName)) {
				itemID = User.get().findItem(slotName, singularItemName);
			}

			if (itemID != -1) {
				RPAction drop = new RPAction();

				drop.put(EquipActionConsts.TYPE, "drop");
				drop.put(EquipActionConsts.BASE_OBJECT, User.get().getObjectID());
				drop.put(EquipActionConsts.BASE_SLOT, slotName);
				drop.put(EquipActionConsts.GROUND_X, (int) User.get().getX());
				drop.put(EquipActionConsts.GROUND_Y, (int) User.get().getY());
				drop.put(EquipActionConsts.QUANTITY, quantity);
				drop.put(EquipActionConsts.BASE_ITEM, itemID);

				StendhalClient.get().send(drop);
				return true;
			}
		}

		StendhalUI.get().addEventLine("You don't have any " + singularItemName);
		return true;
	}

	/**
	 * Get the maximum number of formal parameters.
	 * 
	 * @return The parameter count.
	 */
	public int getMaximumParameters() {
		return 1;
	}

	/**
	 * Get the minimum number of formal parameters.
	 * 
	 * @return The parameter count.
	 */
	public int getMinimumParameters() {
		return 1;
	}
}
