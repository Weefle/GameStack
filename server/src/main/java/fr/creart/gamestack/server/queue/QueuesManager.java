/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.queue;

import fr.creart.gamestack.common.game.GameMap;
import fr.creart.gamestack.common.player.Queueable;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import org.apache.commons.lang3.Validate;

/**
 * Manages the player queues. (The queues where players are waiting to play.)
 *
 * @author Creart
 */
public class QueuesManager {

    private ReadWriteLock lock = new ReentrantReadWriteLock();
    private Map<GameMap, PlayerQueue> queues = new HashMap<>();

    public boolean enqueueObject(Queueable queueable, GameMap map)
    {
        Validate.notNull(queueable, "queueable can't be null");

        PlayerQueue queue = null;
        lock.readLock().lock();
        try {
            queue = queues.get(map);
        } finally {
            lock.readLock().unlock();
        }

        if (queue != null) {
            queue.addQueueable(queueable);
            return true;
        }

        //TODO:

        return false;
    }

    /**
     * Returns <code>true</code> if the given object has been removed from a queue
     *
     * @param queueable the queueable object to remove
     * @return <code>true</code> if the given object has been removed from a queue
     */
    public boolean dequeueObject(Queueable queueable)
    {
        Validate.notNull(queueable, "queueable item can't be null");

        lock.readLock().lock();
        try {
            for (PlayerQueue queue : queues.values())
                if (queue.removeQueueable(queueable))
                    return true;
            return false;
        } finally {
            lock.readLock().unlock();
        }
    }

}
