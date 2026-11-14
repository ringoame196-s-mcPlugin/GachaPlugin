package com.github.ringoame196.gui

import com.github.ringoame196.model.GachaBlock
import org.bukkit.Bukkit
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.Inventory

class ViewGachaponInventory(
    private val gachaBlock: GachaBlock,
) : GachaponInventory {
    companion object {
        private const val SIZE = 9
        private const val NAME = "プレビュー"
        private const val CURRENCY_ITEM_SLOT = 4
    }

    override fun makeInventory(): Inventory {
        val currentItem = gachaBlock.currencyItem
        val inv = Bukkit.createInventory(null, SIZE, NAME)
        inv.setItem(CURRENCY_ITEM_SLOT, currentItem)
        return inv
    }

    override fun onInventoryClickEvent(e: InventoryClickEvent) {
        e.isCancelled = true
    }
}
