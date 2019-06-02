package org.jemiahlabs.skrls.app;

public class App {
    public static void main(String[] args) {
        CLI cli = CLI.getInstance(args);
        cli.run();
    }
}
