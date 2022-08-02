package me.reidj.captcha.inventory

import me.reidj.captcha.after
import me.reidj.captcha.app
import me.reidj.captcha.data.CaptchaItem
import me.reidj.captcha.generator.ItemGenerator.correctItemStack
import me.reidj.captcha.generator.ItemGenerator.incorrectItemStack
import me.reidj.captcha.replaceSymbol
import me.reidj.captcha.sendPlayerToServer
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import kotlin.properties.Delegates.notNull

/**
 * @project : Captcha
 * @author : Рейдж
 **/
object CaptchaGui : Listener {

    private var captchaGui: Inventory by notNull()

    fun open(player: Player) {
        captchaGui = Bukkit.createInventory(player, 27, replaceSymbol("inventoryTitle") ?: "Captcha")

        val slots = (0..26).toMutableList()

        setItem(correctItemStack, slots)
        setItem(incorrectItemStack, slots)

        player.openInventory(captchaGui)
    }

    @EventHandler
    fun onClose(event: InventoryCloseEvent) {
        if (event.inventory == captchaGui)
            after { event.player.openInventory(captchaGui) }
    }

    @EventHandler
    fun onClick(event: InventoryClickEvent) {
        val player = event.whoClicked as Player
        val captchaPlayer = app.captchaPlayer[player.uniqueId] ?: return
        val items = captchaPlayer.first
        val itemStack = event.currentItem
        val slot = event.slot

        if (itemStack == null)
            return

        if (event.inventory == captchaGui) {
            event.isCancelled = true
            if (itemStack in correctItemStack && items.none { it.slot == slot }) {
                items.add(CaptchaItem(itemStack, slot))
                event.currentItem = incorrectItemStack[0]
                if (items.size == 6)
                    app.config.getString("sendPlayerTo")?.let { sendPlayerToServer(player, it) }
            } else {
                player.kickPlayer(replaceSymbol("kickMessage") ?: "Try again")
            }
        }
    }

    private fun setItem(list: List<ItemStack>, slots: MutableList<Int>) {
        list.forEach {
            val slot = slots.random()
            captchaGui.setItem(slot, it)
            slots.remove(slot)
        }
    }
}