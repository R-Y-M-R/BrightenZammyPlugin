package com.rymr.zammydarkness;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class ZammyDarknessPluginTest {
    public static void main(String[] args) throws Exception {
        ExternalPluginManager.loadBuiltin(ZammyDarknessPlugin.class);
        RuneLite.main(args);
    }
}