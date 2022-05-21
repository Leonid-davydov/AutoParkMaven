package application.by.incubator.Collections;

import application.by.incubator.Vehicle.Vehicle;

public class Stack {
    private int size = 0;
    private Vehicle[] deque = new Vehicle[size];

    public void push(Vehicle vh) {
        size++;
        Vehicle[] NewDeque = new Vehicle[size];
        for (int i = 0; i < size - 1; i++) {
            NewDeque[i] = deque[i];
        }
        NewDeque[size - 1] = vh;
        deque = NewDeque;
    }

    public Vehicle pop() throws IllegalStateException {
        if (size > 0) {
            size--;
            Vehicle last = deque[size];
            Vehicle[] NewDeque = new Vehicle[size];
            for (int i = 0; i < size; i++) {
                NewDeque[i] = deque[i];
            }
            deque = NewDeque;
            return last;
        } else {
            throw new IllegalStateException();
        }
    }

    public Vehicle peek() throws IllegalStateException {
        if (size > 0) {
            return deque[size - 1];
        } else {
            throw new IllegalStateException();
        }
    }

    public int size() {
        return size;
    }
}
