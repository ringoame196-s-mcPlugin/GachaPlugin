package com.github.ringoame196.events

import com.github.ringoame196.managers.EffectManager
import com.github.ringoame196.model.GachaBlock
import com.github.ringoame196.managers.GachaBlockManager
import com.github.ringoame196.managers.GachaBlockManager.isRoll
import com.github.ringoame196.managers.GachaponInventoryManager
import com.github.ringoame196.gui.ViewGachaponInventory
import org.bukkit.ChatColor
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.player.PlayerInteractEvent
import org.bukkit.plugin.Plugin

class Events(
    private val plugin: Plugin,
) : Listener {
    @EventHandler
    fun onPlayerInteract(e: PlayerInteractEvent) {
        val player = e.player
        val block = e.clickedBlock ?: return
        val action = e.action
        val container = block.state as? Container ?: return
        val gachaBlock = GachaBlockManager.getGachaponBlock(container) ?: return

        if (action != Action.RIGHT_CLICK_BLOCK) return

        e.isCancelled = true
        if (!player.isSneaking) {
            val item = player.inventory.itemInMainHand
            val currentItem = gachaBlock.currencyItem
            if (!isRoll(item, currentItem)) {
                val message = "${ChatColor.RED}消費アイテムが必要です"
                EffectManager.sendErrorMessage(player, message)
                return
            }
            GachaBlockManager.roll(player, gachaBlock, plugin)
        } else {
            if (gachaBlock.currencyItem == null) {
                val message = "通貨アイテムは設定されていません"
                player.sendMessage(message)
            } else {
                openViewInventory(player, gachaBlock)
            }
        }
    }

    private fun openViewInventory(
        player: Player,
        gachaBlock: GachaBlock,
    ) {
        val viewInventory = ViewGachaponInventory(gachaBlock)
        val inv = viewInventory.makeInventory()
        player.openInventory(inv)
        GachaponInventoryManager.addGachaponInventory(inv, viewInventory)
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val inventory = e.clickedInventory ?: return
        val gachaponInventory = GachaponInventoryManager.getGachaponInventory(inventory) ?: return
        gachaponInventory.onInventoryClickEvent(e)
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val inventory = e.inventory
        GachaponInventoryManager.removeGachaponInventory(inventory)
    }

    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        val player = e.player
        val block = e.block
        val container = block.state as? Container ?: return
        if (!GachaBlockManager.gachaBlocks.contains(container)) return
        e.isCancelled = true

        val message = "${ChatColor.RED}ガチャブロックを壊すことは阻止されています"
        EffectManager.sendErrorMessage(player, message)
    }
}
