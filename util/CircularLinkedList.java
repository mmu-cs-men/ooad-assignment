package util;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

public class CircularLinkedList<E> extends LinkedList<E>
{
    @Override
    public E get(int index)
    {
        if (isEmpty())
        {
            throw new IndexOutOfBoundsException("List is empty");
        }
        int size = size();
        int modIndex = ((index % size) + size) % size;
        return super.get(modIndex);
    }

    @Override
    public Iterator<E> iterator()
    {
        return new CircularIterator();
    }

    private class CircularIterator implements Iterator<E>
    {
        private int currentIndex = 0;

        @Override
        public boolean hasNext()
        {
            return !isEmpty();
        }

        @Override
        public E next()
        {
            if (isEmpty())
            {
                throw new NoSuchElementException();
            }
            E element = get(currentIndex);
            currentIndex = (currentIndex + 1) % size();
            return element;
        }
    }
}