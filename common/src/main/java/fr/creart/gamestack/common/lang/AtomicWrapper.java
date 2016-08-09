/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.lang;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Implementation of {@link Wrapper} for atomic values
 *
 * @author Creart
 */
public class AtomicWrapper<T> extends Wrapper<T> {

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public AtomicWrapper()
    {
        super();
    }

    public AtomicWrapper(T value)
    {
        super(value);
    }

    @Override
    public void set(T value)
    {
        lock.writeLock().lock();
        try {
            this.value = value;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public T get()
    {
        lock.readLock().lock();
        try {
            return value;
        } finally {
            lock.readLock().unlock();
        }
    }

}
