package com.jemiahlabs.skrls.cli;

@FunctionalInterface
public interface OptionsConsumer<T> extends SKRLSConsumer{
    void process(T args);
}
