
public class Main {

    public static void main(String[] args) {

        System.out.println("Hello world!");        // Create a Pair with Integer and String

        Pair<Integer, String> pair = new Pair<>(10, "Hello");

        // Accessing elements of the Pair
        Integer first = pair.getFirst();
        String second = pair.getSecond();

        // Printing the Pair
        System.out.println("First: " + first);
        System.out.println("Second: " + second);

        // Using toString() method to print the Pair
        System.out.println("Pair: " + pair);
    }


}