package com.github.ringoame196.managers

import org.bukkit.inventory.ItemStack

object ItemManager {
    fun getItemName(item: ItemStack): String {
        val meta = item.itemMeta ?: return item.type.name
        return if (meta.hasDisplayName()) meta.displayName else item.type.name
    }
}