package com.github.ringoame196.events

import com.github.ringoame196.GachaBlockManager
import com.github.ringoame196.InventoryManager
import org.bukkit.block.Container
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.Action
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.event.player.PlayerInteractEvent

class Events : Listener {
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
            GachaBlockManager.roll(player, gachaBlock)
        } else {
            if (gachaBlock.currencyItem == null) {
                val message = "通貨アイテムは設定されていません"
                player.sendMessage(message)
            } else {
                InventoryManager.openView(player, gachaBlock)
            }
        }
    }

    @EventHandler
    fun blockBreak(e: BlockBreakEvent) {
        val player = e.player
        val block = e.block
        val container = block.state as? Container ?: return
        if (!GachaBlockManager.gachaBlocks.contains(container)) return
        e.isCancelled = true

        val message = "ガチャブロックを壊すことは阻止されています"
        player.sendMessage(message)
    }
}
