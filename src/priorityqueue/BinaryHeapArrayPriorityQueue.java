package priorityqueue;

public class BinaryHeapArrayPriorityQueue {
    private final double[] items = new double[1000];

    private int lastIndex = -1;

    private int getParentIndex(int i) {
        return (i - 1) / 2;
    }

    private void swap(int i1, int i2) {
        double temp = items[i1];
        items[i1] = items[i2];
        items[i2] = temp;
    }

    private void siftUp(int index) {
        int i = index, pi = getParentIndex(i);
        while (pi >= 0 && items[i] > items[pi]) {
            swap(i, pi);
            i = pi;
            pi = getParentIndex(i);
        }
    }

    public void enqueue(double item) {
        items[++lastIndex] = item;
        siftUp(lastIndex);
    }

    public double peek() {
        return items[0];
    }

    private int getLeftChildIndex(int i) {
        return i * 2 + 1;
    }

    private int getRightChildIndex(int i) {
        return i * 2 + 2;
    }

    private void siftDown(int index) {
        int i = index, li = getLeftChildIndex(i), ri = getRightChildIndex(i), childIndex;

        while ((li <= lastIndex && items[li] > items[i]) || (ri <= lastIndex && items[ri] > items[i])) {
            childIndex = li == lastIndex || items[ri] <= items[i] || items[li] > items[ri] ? li : ri;
            swap(i, childIndex);
            i = childIndex;
            li = getLeftChildIndex(i);
            ri = getRightChildIndex(i);
        }
    }

    public double dequeue() {
        double topItem = peek();

        items[0] = items[lastIndex];
        siftDown(0);

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
            siftUp(i);
        } else {
            siftDown(i);
        }
    }

    public double remove(int i) {
        double removed = items[i];
        items[i] = peek() + 1;
        siftUp(i);
        dequeue();
        return removed;
    }
}
