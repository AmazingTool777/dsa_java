package priorityqueue;

/**
 * Priority queue using an array.
 * Items that have higher values have higher priorities.
 */
public class ArrayPriorityQueue {
    public class Item {
        double value;
        int priority;

        public Item(double value, int priority) {
            this.value = value;
            this.priority = priority;
        }
    }

    private Item[] items = new Item[1000];

    private int lastIndex = -1;

    public int getSize() {
        return lastIndex + 1;
    }

    public boolean isEmpty() {
        return getSize() == 0;
    }

    public void enqueue(double value, int priority) {
        lastIndex++;
        items[lastIndex] = new Item(value, priority);
    }

    private int getHighestPriorityItemIndex() {
        int maxPriority = Integer.MIN_VALUE;
        int maxI = -1;

        for (int i = 0; i <= lastIndex; i++) {
            Item item = items[i];
            if (item.priority > maxPriority) {
                maxPriority = item.priority;
                maxI = i;
            } else if (item.priority == maxPriority && item.value > items[maxPriority].value) {
                maxPriority = item.priority;
                maxI = i;
            }
        }

        return maxI;
    }

    public double dequeue() {
        int maxI = getHighestPriorityItemIndex();
        double maxValue = items[maxI].value;

        // Shifting the items behind the dequeued item to the left
        for (int i = maxI; i < lastIndex; i++) {
            items[i] = items[i + 1];
        }
        lastIndex--;

        return maxValue;
    }

    public double peek() {
        return items[getHighestPriorityItemIndex()].value;
    }
}
