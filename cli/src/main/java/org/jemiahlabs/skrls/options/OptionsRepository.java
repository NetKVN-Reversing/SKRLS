package org.jemiahlabs.skrls.options;

import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public interface OptionsRepository {
    Options retrieveOptions();
    void insertOption(Option... opt);
}
