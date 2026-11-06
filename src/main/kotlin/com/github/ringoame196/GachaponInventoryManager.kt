package com.github.ringoame196

import org.bukkit.inventory.Inventory

object GachaponInventoryManager {
    private val gachaponInventories = mutableMapOf<Inventory, GachaponInventory>()

    fun addGachaponInventory(
        inventory: Inventory,
        gachaponInventory: GachaponInventory,
    ) {
        gachaponInventories[inventory] = gachaponInventory
    }

    fun removeGachaponInventory(inventory: Inventory) {
        gachaponInventories.remove(inventory)
    }

    fun getGachaponInventory(inventory: Inventory): GachaponInventory? = gachaponInventories[inventory]
}
