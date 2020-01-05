package com.jemiahlabs.skrls.cli;

import org.apache.commons.cli.Options;

public class CliApplication {

    public void run(String[] args){
        Options options = SKRLSOptions.getInstance();
        SKRLSCommandRunner runner = new SKRLSCommandRunner(options, args);
        runner.run();
    }
}
