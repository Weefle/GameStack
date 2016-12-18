/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.queue;

import fr.creart.gamestack.common.game.GameMap;
import fr.creart.gamestack.common.player.Queueable;
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
    private Map<Byte, Queue<Queueable>> queues = new TreeMap<>();

    public PlayerQueue(GameMap gameMap)
    {
        this.gameMap = gameMap;
    }

    /**
     * Returns <tt>true</tt> if the player is in the current queue
     *
     * @param queueable the queueable item
     * @return <tt>true</tt> if the player is in the current queue
     */
    public boolean hasQueueable(Queueable queueable)
    {
        return getCurrentQueue(queueable) != null;
    }

    /**
     * Returns player's current queue for this current map (<tt>null if not found</tt>).
     *
     * @param queueable the queueable item
     * @return player's current queue for this current map.
     */
    public Queue getCurrentQueue(Queueable queueable)
    {
        queueLock.lock();
        try {
            for (Queue<Queueable> queue : queues.values())
                if (queue.contains(queueable))
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
     * @param queueable     the player to add
     */
    public void addQueueable(Queueable queueable)
    {
        removeQueueable(queueable);

        queueLock.lock();
        try {
            Queue<Queueable> put = queues.get(queueable.getPriority());

            if (put == null) {
                put = new LinkedList<>();
                queues.put(queueable.getPriority(), put);
            }

            put.add(queueable);
        } finally {
            queueLock.unlock();
        }
    }

    /**
     * Removes the given player from his queue
     *
     * @param queueable the queueable
     * @return <code>true</code> if the player has been removed from a queue
     */
    public boolean removeQueueable(Queueable queueable)
    {
        return removeQueueable(queueable, getCurrentQueue(queueable));
    }

    private boolean removeQueueable(Queueable queueable, Queue queue)
    {
        queueLock.lock();
        try {
            if (queueable != null && queue != null) {
                queue.remove(queueable);
                return true;
            }

            return false;
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
