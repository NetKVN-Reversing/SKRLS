package com.jemiahlabs.skrls.cli;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.jemiahlabs.skrls.context.ApplicationContext;
import org.jemiahlabs.skrls.context.Plugin;
import org.jemiahlabs.skrls.core.ExtractableKnowledge;
import org.jemiahlabs.skrls.core.Producer;

public class SKRLSOptionsProcessor {

    ApplicationContext context;

    Map<String, OptionsConsumer<String[]>> argConsumers;

    public SKRLSOptionsProcessor(){
        this.context = initializeContext();
        argConsumers = new HashMap<>();
        initializeArgConsumers();
        context.loaderPlugins((x)->{}, this::onFail);
    }

    public void process(String option, String... args){
        argConsumers.get(option).process(args);
    }

    private ApplicationContext initializeContext(){
        ApplicationContext context = ApplicationContext.GetNewInstance(new ReceiverImpl());
        return context;
    }

    private void initializeArgConsumers(){

        argConsumers.put("add", args -> {
            String pathToJar = args[0];
            context.addPlugin(pathToJar, this::onSuccess, this::onFail);
        });

        argConsumers.put("list", args -> {
            List<Plugin> plugins = context.getPlugins();
            Function<Plugin, String> pluginInfo = plugin -> {
                String authors = "\n\t" + String.join("\n\t", plugin.getAuthors());
                return String.format("Name: %s%nVersion: %s%nTarget Language: %s%nDescription: %s%nAuthors: %s%n",
                     plugin.getName(),
                     plugin.getVersion(),
                     plugin.getTargetLanguaje(),
                     plugin.getDescription(),
                     authors);
            };
            plugins.forEach( plugin -> System.out.println(pluginInfo.apply(plugin)));
        });

        argConsumers.put("remove", args -> {
            String[] nameAndVersion = args[0].split(":");
            List<Plugin> installedPlugins = context.getPlugins();
            List<Plugin> matches = installedPlugins.stream().filter( p -> p.getName().equals(nameAndVersion[0])).collect(Collectors.toList());
            if(matches.size() > 1 && nameAndVersion.length == 1){
                System.out.println("found more than 1 plugin with the same name, please specify the number of the version");
            }else if(matches.size() > 1 && nameAndVersion.length == 2){
                Optional<Plugin> foundPlugin = matches.stream().filter(p -> p.getVersion().equals(nameAndVersion[1])).findFirst();
                foundPlugin.ifPresent( p -> context.removePlugin(p.getNameable()));
                System.out.println("Plugin removed succesfully");
            }else if(matches.size() == 1 && nameAndVersion.length == 1){
                context.removePlugin(matches.get(0).getNameable());
                System.out.println("Plugin removed successfully");
            }
        });

        argConsumers.put("analyze", args -> {
            String requestedPlugin = args[0];
            String inputDir = args[1];
            String outputDir = args[2];
            Plugin pluginToUse = null;
            for (Plugin p : context.getPlugins()){
                if(p.getName().equals(args[0])){
                    pluginToUse = p;
                    break;
                }
            }
            if(Objects.nonNull(pluginToUse)){
                ExtractableKnowledge extractor = pluginToUse.getExtractableKnowledge();
                Producer producer = new ProducerImpl(new ChannelImpl(context.getReceiver()));
                extractor.extractKDM(producer,inputDir, outputDir);
            } else {
                System.out.println("plugin " + requestedPlugin + "not found");
            }
        });
    }

    private void onSuccess(Plugin plugin){
        System.out.println("Plugin loaded successfully");
        System.out.println(plugin.getName() + " - " + plugin.getVersion());
    }

    private void onFail(Exception e){
        System.out.println("Failed to load plugin. Reason: " + e.getMessage());
    }
}
