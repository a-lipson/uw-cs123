package me.alipson.quiz;

/**
 * Lens
 */
public interface Lens<A, S> {

    A get(S s);

    S set(S s, A a);
}
