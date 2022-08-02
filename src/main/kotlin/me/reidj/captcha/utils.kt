package me.reidj.captcha

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.entity.Player
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream

/**
 * @project : Captcha
 * @author : Рейдж
 **/

fun after(ticks: Long = 1, runnable: Runnable) = Bukkit.getScheduler().runTaskLater(app, runnable, ticks)

fun sendPlayerToServer(player: Player, server: String) {
    try {
        ByteArrayOutputStream().apply {
            DataOutputStream(this).apply {
                writeUTF("Connect")
                writeUTF(server)
                player.sendPluginMessage(app, "BungeeCord", toByteArray())
            }.close()
        }.close()
    } catch (e: Exception) {
        player.kickPlayer("${replaceSymbol("errorConnectMessage")} $server")
    }
}

fun hideFromAll(player: Player) {
    Bukkit.getOnlinePlayers().filterNotNull().forEach { current ->
        player.hidePlayer(app, current)
        current.hidePlayer(app, player)
    }
}

fun replaceSymbol(path: String) =
    app.config.getString(path)?.replace("§", "&")?.let { ChatColor.translateAlternateColorCodes('&', it) }
