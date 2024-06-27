import java.util.*;

public class LengthCount {
    /* TODO */
    // implement the lengthCount() method below



    public static void main(String[] args) {
        Set<String> input = addValues();
        System.out.println(lengthCount(input));
    }

    // private method to set up program
    private static Set<String> addValues() {
        Set<String> s = new TreeSet<>();
        s.add("to");
        s.add("be");
        s.add("or");
        s.add("not");
        s.add("that");
        s.add("is");
        s.add("the");
        s.add("question");
        return s;
    }
}
