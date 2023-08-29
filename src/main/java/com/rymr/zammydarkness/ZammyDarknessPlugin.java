/*
 * Copyright (c) 2023, R-Y-M-R
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE FOR
 * ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package com.rymr.zammydarkness;


import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;

/**
 * This plugin hides the "Darkness" widget at Zamorak in Godwars Dungeon.
 */
@Slf4j
@PluginDescriptor(name = ZammyDarknessConstants.PLUGIN_NAME)
public class ZammyDarknessPlugin extends Plugin {
    @Inject
    private Client client;

    @Inject
    private ClientThread clientThread;

    @Subscribe
    public void onWidgetLoaded(WidgetLoaded loaded) {
        if (loaded.getGroupId() == ZammyDarknessConstants.ZAMMY_WIDGET_GROUP_ID) {
            log.debug("@WidgetLoaded: Zammy Darkness Group Id, hiding it...");
            hideZammyDarkness();
        }
    }

    @Override
    protected void startUp() throws Exception {
        log.info(ZammyDarknessConstants.PLUGIN_NAME + " started!");
        if (client.getGameState() == GameState.LOGGED_IN) {
            log.debug("Logged in, hiding Zammy Darkness widget");
            hideZammyDarkness();
        }
    }

    /**
     * Hides the Zammy Darkness widget.
     */
    private void hideZammyDarkness() {
        final Widget darkness = getZammyWidget();
        if (darkness != null) {
            log.debug("Zammy Darkness widget found, hiding it");
            clientThread.invokeAtTickEnd(() -> darkness.setHidden(true));
        }
    }

    /**
     * Gets the Zammy Darkness widget.
     *
     * @return the Zammy Darkness widget
     */
    private Widget getZammyWidget() {
        return client.getWidget(ZammyDarknessConstants.ZAMMY_WIDGET_ID);
    }

    @Override
    protected void shutDown() throws Exception {
        log.info(ZammyDarknessConstants.PLUGIN_NAME + " stopped!");
        if (client.getGameState() == GameState.LOGGED_IN) {
            log.debug("Logged in, unhiding Zammy Darkness widget");
            resetZammyDarkness();
        }
    }

    /**
     * Unhides the Zammy Darkness widget.
     */
    private void resetZammyDarkness() {
        final Widget darkness = getZammyWidget();
        if (darkness != null) {
            darkness.setHidden(false);
            log.info("Zammy Darkness widget unhidden");
        }
    }
}
