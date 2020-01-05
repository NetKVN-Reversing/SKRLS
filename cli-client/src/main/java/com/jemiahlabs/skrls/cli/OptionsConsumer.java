package com.jemiahlabs.skrls.cli;

@FunctionalInterface
public interface OptionsConsumer<T> {
    void process(T args);
}
