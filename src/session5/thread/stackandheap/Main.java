package session5.thread.stackandheap;

public class Main {
    public static void main(String[] args) {
        int a = 1;
        int b = 2;
        int result = sum(a, b);
        System.out.println(result);
    }

    private static int sum(int a, int b) {
        return a + b;
    }
}
