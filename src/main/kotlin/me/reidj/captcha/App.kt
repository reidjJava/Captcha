package me.reidj.captcha

import me.reidj.captcha.data.CaptchaItem
import me.reidj.captcha.inventory.CaptchaGui
import me.reidj.captcha.listener.PlayerJoin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.scheduler.BukkitTask
import java.io.ByteArrayOutputStream
import java.io.DataOutputStream
import java.util.*


/**
 * @project : Captcha
 * @author : Рейдж
 **/

lateinit var app: App

class App : JavaPlugin() {

    val captchaPlayer = mutableMapOf<UUID, Pair<MutableList<CaptchaItem>, Long>>()

    override fun onEnable() {
        app = this

        // Регистрация конфига
        config.options().copyDefaults(true)
        saveConfig()

        server.messenger.registerOutgoingPluginChannel(this, "BungeeCord")

        Timer().runTaskTimer(app, 0, 1)

        // Регистрация обработчиков событий
        listOf(PlayerJoin, CaptchaGui).forEach { Bukkit.getPluginManager().registerEvents(it, this) }
    }
}