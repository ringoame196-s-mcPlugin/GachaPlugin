package com.github.ringoame196

import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

interface GachaponInventory {
    fun makeInventory(): Inventory

    fun onInventoryClickEvent(e: InventoryClickEvent) {}
}
