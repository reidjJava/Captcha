package me.reidj.captcha.generator

import me.reidj.captcha.replaceSymbol
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

/**
 * @project : Captcha
 * @author : Рейдж
 **/
object ItemGenerator {

    val correctItemStack = MutableList(6) { createDye(Material.LIME_DYE, replaceSymbol("correctItemTitle") ?: "") }
    val incorrectItemStack = MutableList(21) { createDye(Material.GRAY_DYE, replaceSymbol("incorrectItemTitle") ?: "") }

    private fun createDye(material: Material, title: String) =
        ItemStack(material).apply { itemMeta = itemMeta.apply { setDisplayName(title) } }
}