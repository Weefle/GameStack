package fr.creart.gamestack.common.lang;

import java.util.Optional;

/**
 * Result of an action, which can be a success or a failure.
 * It allows to keep exceptions.
 * <p>
 * The way you can use it is when your method does not guarantee to return a correct value (an exception)
 * you set the return type to {@link Validation}:
 * <pre>
 *     {@code
 *     public static class Validation<Exception, Integer> toInt(String s) {
 *         try {
 *             return new Validation.Success<>(Integer.valueOf(s));
 *         } catch (Exception e) {
 *             return new Validation.Failure<>(e);
 *         }
 *     }
 *     }
 * </pre>
 *
 * Then, the caller of the function can get to know if the operation is a success by calling the {@link #isSuccess()}
 * function. (You can have a look at the test: fr.creart.gamestack.common.test.ValidationTest)
 *
 * @author François Sarradin (https://www.youtube.com/watch?v=evS3iwZLS4s)
 */
public abstract class Validation<E, T> {

    private Validation()
    {

    }

    /**
     * Returns {@code true} if the operation is a success
     * @return {@code true} if the operation is a success
     */
    public final boolean isSuccess()
    {
        return this.getClass() == Success.class;
    }

    /**
     * Returns an {@link Optional} of the value contained by the validation
     * @return an {@link Optional} of the value contained by the validation
     */
    public abstract Optional<T> toOptional();

    /**
     * Returns the inverse Validation of the current one
     * @return the inverse Validation of the current one
     */
    public abstract Validation<T, E> swap();

    public static class Success<T, E> extends Validation<E, T> {
        private final T value;

        public Success(T value)
        {
            this.value = value;
        }

        @Override
        public Optional<T> toOptional()
        {
            return null;
        }

        @Override
        public Validation<T, E> swap()
        {
            return new Failure<>(value);
        }
    }

    public static class Failure<E, T> extends Validation<E, T> {
        private final E exception;

        public Failure(E exception)
        {
            this.exception = exception;
        }

        @Override
        public Optional<T> toOptional()
        {
            return Optional.empty();
        }

        @Override
        public Validation<T, E> swap()
        {
            return new Success<>(exception);
        }
    }

}
