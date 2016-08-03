package fr.creart.gamestack.common.lang;

/**
 * @author Creart
 */
public class BasicWrapper<T> extends Wrapper<T> {

    public BasicWrapper()
    {
        super();
    }

    public BasicWrapper(T value)
    {
        super(value);
    }

    @Override
    public void set(T value)
    {
        this.value = value;
    }

    @Override
    public T get()
    {
        return value;
    }

}
