package me.alipson.multiply;

/**
 * IntegerScaleMap
 */
public class IntegerScaleMap implements Map<Integer, Integer> {
    @Override
    public Integer apply(Integer x) {
        return 2 * x;
    }

}
