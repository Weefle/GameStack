/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.queue;

import fr.creart.gamestack.common.game.GameMap;
import fr.creart.gamestack.server.player.Player;
import fr.creart.gamestack.server.player.Queueable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;

/**
 * @author Creart
 */
public class PlayerQueue {

    private GameMap map;
    private Map<Integer, Queue<Queueable>> queues = new HashMap<>();

    public boolean hasPlayer(Player player)
    {
        return getCurrentQueue(player) != null;
    }

    public Queue getCurrentQueue(Player player)
    {
        for (Queue<Queueable> queue : queues.values())
            if (queue.contains(player))
                return queue;
        return null;
    }

    public void addPlayer(Player player, int priority)
    {
        removePlayer(player);

        Queue<Queueable> put = queues.get(priority);

        if (put == null) {
            put = new LinkedList<>();
            queues.put(priority, put);
        }

        put.add(player);
    }

    public void removePlayer(Player player)
    {
        removePlayer(player, getCurrentQueue(player));
    }

    public void removePlayer(Player player, Queue queue)
    {
        if (player != null && queue != null)
            queue.remove(player);
    }

}
