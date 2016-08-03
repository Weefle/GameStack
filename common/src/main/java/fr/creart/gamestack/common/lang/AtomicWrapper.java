package fr.creart.gamestack.common.lang;

/**
 * Implementation of {@link Wrapper} for atomic values
 *
 * @author Creart
 */
public class AtomicWrapper<T> extends Wrapper<T> {

    private final Object mutex = new Object();

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
