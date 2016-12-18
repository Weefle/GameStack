/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.player;

/**
 * Represents an object which can be enqueued in the player queues
 *
 * @author Creart
 */
public interface Queueable extends Connectable, Contactable {

    /**
     * Returns the "weight" of the queueable object. Which is <tt>1</tt> for each player,
     * so a party of 10 players has a weight of 10.
     *
     * @return the "weight" of the queueable object
     */
    byte getWeight();

    /**
     * Returns the priority of the current item.
     * <p>
     * If it is a player, his priority is given; otherwise if it is a party, leader's priority is given.
     *
     * @return the priority of the current item
     */
    byte getPriority();

}
