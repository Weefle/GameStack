/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.server.queue;

import fr.creart.gamestack.common.game.GameMap;
import fr.creart.gamestack.common.pipeline.Pipeline;
import fr.creart.gamestack.common.pipeline.SimplePipeline;
import fr.creart.gamestack.common.player.Queueable;
import fr.creart.gamestack.server.StackServer;
import fr.creart.gamestack.server.server.HostServer;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
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
    private Pipeline<Collection<PlayerQueue>> requestPipeline = new SimplePipeline<>();

    public void lookup()
    {
        Set<PlayerQueue> filled = new HashSet<>();
        requestPipeline.call(filled);

        if (filled.isEmpty())
            return;

        for (PlayerQueue q : filled) {
            HostServer server = StackServer.getInstance().getBestServer();
            // TODO: 19/12/2016 create settings for minimal player amount
            if (server == null || !server.canHost(q.getGameMap(), (short) 0)) {
                // cannot satisfy demand, we need new servers
            }
            else {
                // now, we can send players
                // q.getToSend, etc.
            }
        }
    }

    public void createQueues()
    {

    }

    /**
     * Returns <tt>true</tt> if the {@link Queueable} has been successfully added to the requested queue
     *
     * @param queueable the queuable object in itself
     * @param map       the requested queue (the map associated to it)
     * @return <tt>true</tt> if the {@link Queueable} has been successfully added to the requested queue
     */
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
