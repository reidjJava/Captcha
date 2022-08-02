package me.reidj.captcha.listener

import me.reidj.captcha.app
import me.reidj.captcha.generator.ItemGenerator
import me.reidj.captcha.hideFromAll
import me.reidj.captcha.inventory.CaptchaGui
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

/**
 * @project : Captcha
 * @author : Рейдж
 **/
object PlayerJoin : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val player = event.player

        hideFromAll(player)

        event.joinMessage(null)

        app.captchaPlayer[player.uniqueId] = Pair(mutableListOf(), System.currentTimeMillis())

        CaptchaGui.open(player)
    }

    @EventHandler
    fun onQuit(event: PlayerQuitEvent) {
        val uuid = event.player.uniqueId

        event.quitMessage(null)

        app.captchaPlayer.remove(uuid)
    }
}