/*
 * Copyright (c) 2013, LankyLord
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 *   list of conditions and the following disclaimer.
 * * Redistributions in binary form must reproduce the above copyright notice,
 *   this list of conditions and the following disclaimer in the documentation
 *   and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package net.lankylord.simpleslotbypass.listeners;

import net.lankylord.simpleslotbypass.SimpleSlotBypass;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

/**
 *
 * @author LankyLord
 */
public class LoginListener implements Listener {

    private final SimpleSlotBypass plugin;
    private String serverFullMessage;

    public LoginListener(SimpleSlotBypass plugin) {
        this.plugin = plugin;
        this.serverFullMessage = plugin.getConfig().getString("server-full-message");
    }

    @EventHandler(priority = EventPriority.NORMAL)
    public void onPlayerLogin(PlayerLoginEvent e) {
        Player p = e.getPlayer();
        Player[] players = plugin.getServer().getOnlinePlayers();
        if (e.getResult() == PlayerLoginEvent.Result.KICK_FULL && p.hasPermission("simpleslotbypass.bypass")) {
            if (players.length <= plugin.maximumSlots)
                e.allow();
            if (players.length == plugin.maximumSlots)
                e.disallow(PlayerLoginEvent.Result.KICK_FULL, serverFullMessage);
        } else if (players.length == plugin.publicSlots && !p.hasPermission("simpleslotbypass.bypass"))
            e.disallow(PlayerLoginEvent.Result.KICK_FULL, serverFullMessage);
    }
}
