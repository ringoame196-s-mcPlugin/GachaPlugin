package com.github.ringoame196

import org.bukkit.inventory.Inventory

object GachaponInventoryManager {
    private val GachaponInventoryMap = mutableMapOf<Inventory, GachaponInventory>()

    fun addGachaponInventory(
        inventory: Inventory,
        gachaponInventory: GachaponInventory,
    ) {
        GachaponInventoryMap[inventory] = gachaponInventory
    }

    fun removeGachaponInventory(inventory: Inventory) {
        GachaponInventoryMap.remove(inventory)
    }

    fun getGachaponInventory(inventory: Inventory): GachaponInventory? = GachaponInventoryMap[inventory]
}
