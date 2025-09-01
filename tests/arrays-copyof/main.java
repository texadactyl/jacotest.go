public class main {

    // TODO: add qualitative tests.

    public static void main(String[] args) {
        IntArray original = new IntArray(new int[]{1, 2, 3, 4, 5});

        IntArray largerCopy = original.copyOf(8);
        IntArray smallerCopy = original.copyOf(3);

        System.out.print("Original:     ");
        original.print();

        System.out.print("Larger Copy:  ");
        largerCopy.print();

        System.out.print("Smaller Copy: ");
        smallerCopy.print();
    }
}

// Wrapper class, not a subclass of int[]
class IntArray {
    private final int[] data;

    public IntArray(int[] data) {
        this.data = data;
    }

    public IntArray copyOf(int newLength) {
        return new IntArray(java.util.Arrays.copyOf(data, newLength));
    }

    public void print() {
        System.out.print("[");
        for (int i = 0; i < data.length; i++) {
            System.out.print(data[i]);
            if (i < data.length - 1) {
                System.out.print(", ");
            }
        }
        System.out.println("]");
    }

    public int length() {
        return data.length;
    }

    public int get(int index) {
        return data[index];
    }

    public void set(int index, int value) {
        data[index] = value;
    }
}

