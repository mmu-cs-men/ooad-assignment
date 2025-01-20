package utils;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CircularLinkedList<T> extends LinkedList<T>
{
    @Override
    public T get(int index)
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("List is empty");
        }

        return super.get(index % size());
    }

    public Iterator<T> circularIterator()
    {
        return new CircularIterator();
    }

    private class CircularIterator implements Iterator<T>
    {
        private int currentIndex = 0;

        @Override
        public boolean hasNext()
        {
            return !isEmpty();
        }

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