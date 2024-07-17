package me.alipson.multiply;

/**
 * IntegerMultiplicationMonoid
 */
public class IntegerMultiplicationMonoid implements Monoid<Integer> {
    @Override
    public Integer combine(Integer a, Integer b) {
        return a * b;
    }

    @Override
    public Integer identity() {
        return 1;
    }
}
