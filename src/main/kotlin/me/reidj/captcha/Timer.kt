package me.reidj.captcha

import org.bukkit.Bukkit
import org.bukkit.scheduler.BukkitRunnable

/**
 * @project : Captcha
 * @author : Рейдж
 **/
class Timer : BukkitRunnable() {

    override fun run() {
        Bukkit.getOnlinePlayers().forEach {
            val captchaPlayer = app.captchaPlayer[it.uniqueId] ?: return@forEach
            if (System.currentTimeMillis() - captchaPlayer.second > app.config.getInt("timeout").toLong() * 1000)
                it.kickPlayer(replaceSymbol("failedToEnter"))
        }
    }
}