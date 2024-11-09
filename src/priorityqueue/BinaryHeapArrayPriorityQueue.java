package priorityqueue;

public class BinaryHeapArrayPriorityQueue {
    private double[] items = new double[1000];

    private int lastIndex = -1;

    private int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    private void swap(int i1, int i2) {
        double temp = items[i1];
        items[i1] = items[i2];
        items[i2] = temp;
    }

    private void shiftUp(int index) {
        int i = index, pi;
        while (i > 0) {
            pi = getParentIndex(i);
            if (items[i] > items[pi]) {
                swap(i, pi);
                i = pi;
            } else {
                break;
            }
        }
    }

    public void enqueue(double item) {
        items[++lastIndex] = item;
        shiftUp(lastIndex);
    }

    public double peek() {
        return items[0];
    }

    private void shiftDown(int index) {
        int i = index, li, ri;

        while (i < lastIndex) {
            li = index * 2 + 1;
            ri = index * 2 + 2;

            if (items[li] > items[i]) {
                swap(i, li);
                i = li;
            } else if (items[ri] > items[i]) {
                swap(i, ri);
                i = ri;
            } else {
                break;
            }
        }
    }

    public double dequeue() {
        double topItem = peek();

        items[0] = items[lastIndex];
        shiftDown(0);

        items[lastIndex] = 0;
        lastIndex--;

        return topItem;
    }

    public boolean isEmpty() {
        return lastIndex == -1;
    }

    public void changePriority(int i, double priority) {
        double oldPriority = items[i];
        items[i] = priority;
        if (oldPriority < priority) {
            shiftUp(i);
        } else {
            shiftDown(i);
        }
    }

    public double remove(int i) {
        double removed = items[i];
        items[i] = peek() + 1;
        shiftUp(i);
        dequeue();
        return removed;
    }
}
