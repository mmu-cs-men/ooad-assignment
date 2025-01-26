package utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * A specialized linked list that supports circular iteration using a custom iterator.
 * <p>
 * This class demonstrates the <em>Iterator</em> design pattern by providing a
 * {@link CircularIterator} that loops indefinitely over the list's elements.
 * It extends {@link LinkedList}, adding behavior to wrap around index-based
 * accesses and to offer an iterator that never terminates unless the list is empty.
 * </p>
 *
 * <p>
 * In this implementation of the iterator pattern:
 * <ul>
 *   <li>
 *     The {@link #circularIterator()} method serves as the <em>creator</em>
 *     of the iterator instance.
 *   </li>
 *   <li>
 *     The {@link CircularIterator} class itself encapsulates how the
 *     iteration will proceed, continuously cycling back to the
 *     beginning of the list after reaching the end.
 *   </li>
 * </ul>
 * </p>
 *
 * @param <T> the type of elements held in this list
 * @author Harris Majeed
 */

public class CircularLinkedList<T> extends LinkedList<T>
{
    /**
     * Retrieves the element at the specified position in this list, wrapping
     * around the end if the given index is larger than the current size.
     *
     * @param index the index of the element to return
     * @return the element at the specified index, modulo the size of the list
     * @throws IndexOutOfBoundsException if this list is empty
     * @author Harris Majeed
     */
    @Override
    public T get(int index)
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("List is empty");
        }

        return super.get(index % size());
    }

    /**
     * Returns an iterator that cycles through the elements in this list
     * indefinitely, wrapping back to the start when the end is reached.
     *
     * @return a circular iterator over the elements in this list
     * @author Harris Majeed
     */
    public Iterator<T> circularIterator()
    {
        return new CircularIterator();
    }

    /**
     * An iterator that repeatedly loops through the list's elements in a
     * circular manner.
     * @author Harris Majeed
     */
    private class CircularIterator implements Iterator<T>
    {
        private int currentIndex = 0;

        /**
         * Indicates whether the list has another element to iterate over.
         * Since this iterator loops indefinitely, it returns {@code true}
         * as long as the list is not empty.
         *
         * @return {@code true} if the list is not empty; {@code false} otherwise
         * @author Harris Majeed
         */
        @Override
        public boolean hasNext()
        {
            return !isEmpty();
        }

        /**
         * Returns the next element in the circular iteration, wrapping
         * to the beginning of the list once the end is reached.
         *
         * @return the next element in the iteration
         * @throws NoSuchElementException if the list is empty
         * @author Harris Majeed
         */
        @Override
        public T next()
        {
            if (isEmpty())
            {
                throw new NoSuchElementException();
            }
            T element = get(currentIndex);
            currentIndex = (currentIndex + 1) % size();
            return element;
        }
    }
}