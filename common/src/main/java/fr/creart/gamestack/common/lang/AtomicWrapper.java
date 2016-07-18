package fr.creart.gamestack.common.lang;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * Implementation of {@link Wrapper} for atomic values
 *
 * @author Creart
 */
@AllArgsConstructor
@NoArgsConstructor
public class AtomicWrapper<T> extends Wrapper<T> {

    private final Object mutex = new Object();
    private T value;

    @Override
    public void set(T value)
    {
        synchronized (mutex) {
            this.value = value;
        }
    }

    @Override
    public T get()
    {
        synchronized (mutex) {
            return value;
        }
    }

}
