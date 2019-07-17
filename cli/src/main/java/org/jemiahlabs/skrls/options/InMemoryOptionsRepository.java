package org.jemiahlabs.skrls.options;

import java.util.Arrays;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class InMemoryOptionsRepository implements OptionsRepository {

    private Options options;

    public InMemoryOptionsRepository(){
        options = new Options();
    }

    @Override
    public Options retrieveOptions() {
        return options;
    }

    @Override
    public void insertOption(Option... opt) {
        Arrays.stream(opt).forEach( option -> options.addOption(option));
    }
}
