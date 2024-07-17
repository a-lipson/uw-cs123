package me.alipson.multiply;

/**
 * Monoid
 */
public interface Monoid<T> {
    T combine(T a, T b);

    T identity();
}
