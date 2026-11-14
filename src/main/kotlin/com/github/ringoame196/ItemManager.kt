package com.github.ringoame196

import org.bukkit.inventory.ItemStack

object ItemManager {
    fun getItemName(item: ItemStack): String =
        if (item.itemMeta.hasDisplayName()) {
            item.itemMeta.displayName
        } else {
            item.type.name
        }
}
