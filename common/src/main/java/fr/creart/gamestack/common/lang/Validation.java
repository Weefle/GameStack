/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.common.lang;

import java.util.Optional;

/**
 * Result of an action, which can be a success or a failure.
 * It allows to keep exceptions.
 * <p>
 * The way you can use it is when your method does not guarantee to return a correct value (an exception)
 * you set the return type to {@link Validation}:
 * <pre>
 *     <tt>
 *
 *     public static class Validation<Exception, Integer> toInt(String s) {
 *         try {
 *             return new Validation.Success<>(Integer.valueOf(s));
 *         } catch (Exception e) {
 *             return new Validation.Failure<>(e);
 *         }
 *     }
 *     </tt>
 * </pre>
 * <p>
 * Then, the caller of the function can get to know if the operation is a success by calling the {@link #isSuccess()}
 * function. (You can have a look at the test: fr.creart.gamestack.common.test.ValidationTest)
 *
 * @param <E> exception
 * @param <T> value
 * @author François Sarradin (https://www.youtube.com/watch?v=evS3iwZLS4s)
 */
public abstract class Validation<E, T> {

    private Validation()
    {

    }

    /**
     * Returns <tt>true</tt> if the operation is a success
     *
     * @return <tt>true</tt> if the operation is a success
     */
    public final boolean isSuccess()
    {
        return this.getClass() == Success.class;
    }

    /**
     * Returns an {@link Optional} of the value contained by the validation
     *
     * @return an {@link Optional} of the value contained by the validation
     */
    public abstract Optional<T> toOptional();

    /**
     * Returns the inverse Validation of the current one
     *
     * @return the inverse Validation of the current one
     */
    public abstract Validation<T, E> swap();

    /**
     * Represents a case of success
     *
     * @param <T> value
     * @param <E> exception
     */
    public static class Success<T, E> extends Validation<E, T> {
        private final T value;

        /**
         * @param value the value
         */
        public Success(T value)
        {
            this.value = value;
        }

        @Override
        public Optional<T> toOptional()
        {
            return Optional.ofNullable(value);
        }

        @Override
        public Validation<T, E> swap()
        {
            return new Failure<>(value);
        }
    }

    /**
     * Represents a case of failure
     *
     * @param <E> exception
     * @param <T> value
     */
    public static class Failure<E, T> extends Validation<E, T> {
        private final E exception;

        /**
         * @param exception the exception
         */
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
