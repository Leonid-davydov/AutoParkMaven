import java.util.ArrayList;

public class Test {
    public static void main(String[] args) {
        ArrayList<Double> myList = new ArrayList<>();
        myList.add(4.0);
        myList.add(9.0);

        double result = myList.parallelStream().reduce(1.0, (a, b) -> a * Math.sqrt(b));

        System.out.println("Result: " + result);
    }
}
