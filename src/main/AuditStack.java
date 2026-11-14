public class AuditStack<T> {

    private static class Node<T> {
        T data;
        Node<T> next;

        Node(T data) {
            this.data = data;
        }
    }

    private Node<T> top;
    private int size;

    public AuditStack() {
        this.top = null;
        this.size = 0;
    }

    public void push(T value) {
        if (value == null) throw new IllegalArgumentException("Log audit tidak boleh null");
        Node<T> newNode = new Node<>(value);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public T pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack kosong");
        }
        T data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public T peek() {
        if (isEmpty()) {
            throw new IllegalStateException("Stack kosong");
        }
        return top.data;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        top = null;
        size = 0;
    }

    public T getFromTop(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Indeks di luar batas: " + index);
        }
        Node<T> current = top;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }

    public boolean validateInvariant() {
        if (size == 0) return top == null;
        if (top == null) return false;

        int count = 0;
        Node<T> current = top;
        while (current != null) {
            count++;
            if (count > size) return false;
            current = current.next;
        }
        return count == size;
    }
}