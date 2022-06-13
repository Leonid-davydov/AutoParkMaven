package by.incubator.application.collections;

import by.incubator.application.vehicle.Vehicle;

public class WashQueue {
    private int size = 0;
    private Vehicle[] deque = new Vehicle[size];

    public void enqueue(Vehicle vh) {
        size++;
        Vehicle[] NewDeque = new Vehicle[size];
        for (int i = 0; i < size - 1; i++) {
            NewDeque[i] = deque[i];
        }
        NewDeque[size - 1] = vh;
        deque = NewDeque;
    }

    public Vehicle dequeue() throws IllegalStateException {
        if (size > 0) {
            size--;
            Vehicle next = deque[0];
            Vehicle[] NewDeque = new Vehicle[size];
            for (int i = 0; i < size; i++) {
                NewDeque[i] = deque[i + 1];
            }
            deque = NewDeque;
            return next;
        } else {
            throw new IllegalStateException();
        }
    }

    public Vehicle peek() throws IllegalStateException {
        if (size > 0) {
            return deque[0];
        } else {
            throw new IllegalStateException();
        }
    }

    public int size() {
        return size;
    }
}
