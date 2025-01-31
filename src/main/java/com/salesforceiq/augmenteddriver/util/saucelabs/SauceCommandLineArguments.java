package com.salesforceiq.augmenteddriver.util.saucelabs;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.converters.PathConverter;
import com.google.common.base.Preconditions;

import java.nio.file.Path;

public class SauceCommandLineArguments {

    public static SauceCommandLineArguments ARGUMENTS;

    public static SauceCommandLineArguments initialize(String[] args) {
        SauceCommandLineArguments result = new SauceCommandLineArguments();
        JCommander jCommander = new JCommander();
        jCommander.setAcceptUnknownOptions(true);
        jCommander.addObject(result);
        jCommander.parse(args);
        ARGUMENTS = result;
        return ARGUMENTS;
    }

    public Path file() {
        Preconditions.checkNotNull(ARGUMENTS, "Call CommandLineArguments#intialize first");
        return ARGUMENTS.fileToUpload;
    }

    public boolean overwrite() {
        Preconditions.checkNotNull(ARGUMENTS, "Call CommandLineArguments#intialize first");
        return ARGUMENTS.overwrite;
    }

    public String conf() {
        Preconditions.checkNotNull(ARGUMENTS, "Call CommandLineArguments#intialize first");
        return ARGUMENTS.conf;
    }

    @Parameter(names = "-file", description = "Path to file to upload", converter = PathConverter.class)
    private Path fileToUpload;

    @Parameter(names = "-overwrite", description = "Whether to overwrite or not the file in case it exists in SauceLabs")
    private boolean overwrite = true;

    @Parameter(names = "-conf", description = "Path to the properties file, conf/tesyui.properties by default")
    private String conf = "conf/augmented.properties";
}
