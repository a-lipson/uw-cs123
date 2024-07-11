import java.util.*;

public class RarestAge {

  public static Integer rarestAge(Map<String, Integer> map) {
    Map<Integer, Integer> ageCounts = new HashMap<>();

    for (Integer age : map.values()) {
      if (ageCounts.containsKey(age)) {
        ageCounts.put(age, map.get(age) + 1);
      } else {
        ageCounts.put(age, 1);
      }
    }

    return Integer.valueOf();

  }

  public static void main(String[] args) {
    Map<String, Integer> map = new HashMap<>();
    map.put("Trien", 22);
    map.put("Rohan", 25);
    map.put("Owen", 25);
    map.put("Malak", 20);
    map.put("Liza", 20);
    map.put("Jay", 20);
    map.put("Hayden", 25);
    map.put("Eric", 25);
    map.put("Nicholas", 22);
    System.out.println(rarestAge(map));
  }
}
