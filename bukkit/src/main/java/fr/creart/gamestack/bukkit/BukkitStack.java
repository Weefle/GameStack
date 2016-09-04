/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package fr.creart.gamestack.bukkit;

import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author Creart
 */
public class BukkitStack extends JavaPlugin {

    @Override
    public void onEnable()
    {
    }

    @Override
    public void onDisable()
    {
    }

    private void registerListeners(Listener... listeners)
    {
        for (Listener listener : listeners)
            getServer().getPluginManager().registerEvents(listener, this);
    }

}
