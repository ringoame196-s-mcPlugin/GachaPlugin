package com.github.ringoame196

import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory

object InventoryManager {
    private const val VIEW_INV_SIZE = 9
    private const val VIEW_INV_NAME = "プレビュー"
    private const val CURRENCY_ITEM_SLOT = 4

    fun openView(
        player: Player,
        gachaBlock: GachaBlock,
    ) {
        val inv = makeViewInv(gachaBlock)
        player.openInventory(inv)
    }

    private fun makeViewInv(gachaBlock: GachaBlock): Inventory {
        val currentItem = gachaBlock.currencyItem
        val inv = Bukkit.createInventory(null, VIEW_INV_SIZE, VIEW_INV_NAME)
        inv.setItem(CURRENCY_ITEM_SLOT, currentItem)
        return inv
    }
}
