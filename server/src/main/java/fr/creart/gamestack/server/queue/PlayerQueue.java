/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.queue;

import fr.creart.gamestack.common.game.GameMap;
import fr.creart.gamestack.server.player.Player;
import fr.creart.gamestack.server.player.Queueable;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.TreeMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author Creart
 */
public class PlayerQueue {

    private GameMap gameMap;
    private Lock queueLock = new ReentrantLock();
    private Map<Integer, Queue<Queueable>> queues = new TreeMap<>();

    public PlayerQueue(GameMap gameMap)
    {
        this.gameMap = gameMap;
    }

    /**
     * Returns <tt>true</tt> if the player is in the current queue
     *
     * @param player the player
     * @return <tt>true</tt> if the player is in the current queue
     */
    public boolean hasPlayer(Player player)
    {
        return getCurrentQueue(player) != null;
    }

    /**
     * Returns player's current queue for this current map (<tt>null if not found</tt>).
     *
     * @param player the player
     * @return player's current queue for this current map.
     */
    public Queue getCurrentQueue(Player player)
    {
        queueLock.lock();
        try {
            for (Queue<Queueable> queue : queues.values())
                if (queue.contains(player))
                    return queue;
        } finally {
            queueLock.unlock();
        }
        return null;
    }

    /**
     * Removes the player from his current queue — if he has one — and
     * adds him to the queue of the given priority
     *
     * @param player   the player to add
     * @param priority player's priority
     */
    public void addPlayer(Player player, int priority)
    {
        removePlayer(player);

        queueLock.lock();
        try {
            Queue<Queueable> put = queues.get(priority);

            if (put == null) {
                put = new LinkedList<>();
                queues.put(priority, put);
            }

            put.add(player);
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * Removes the given player from his queue
     *
     * @param player the player
     */
    public void removePlayer(Player player)
    {
        removePlayer(player, getCurrentQueue(player));
    }

    private void removePlayer(Player player, Queue queue)
    {
        queueLock.lock();
        try {
            if (player != null && queue != null)
                queue.remove(player);
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * Returns a collection of players to send to the available server. (Players with higher priority come first.)
     *
     * @param slots the amount of players to send
     * @return a collection of players to send to the available server.
     */
    public Collection<Queueable> getToSend(int slots)
    {
        Collection<Queueable> ret = new HashSet<>();

        queueLock.lock();
        try {
            while (ret.size() < slots) {
                // lookup
            }
        } finally {
            queueLock.unlock();
        }

        return ret;
    }

    @Override
    public int hashCode()
    {
        return gameMap.hashCode();
    }

    @Override
    public boolean equals(Object obj)
    {
        return obj instanceof PlayerQueue && obj.hashCode() == hashCode();
    }

}
