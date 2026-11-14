package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.managers.EffectManager
import com.github.ringoame196.managers.GachaBlockManager
import com.github.ringoame196.managers.ItemManager
import com.github.ringoame196.model.GachaBlock
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSubCommand : SubCommand {
    override val name: String = "set"

    companion object {
        private const val SHOULD_CONSUME_TAB_NUMBER = 0
        private const val SHOULD_CONSUME_DISPLAY_TAB_NUMBER = 1
    }

    override fun execute(
        sender: CommandSender,
        args: Array<out String>,
    ) {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です")
            return
        }
        val item = sender.inventory.itemInMainHand
        val targetBlock = GachaBlockManager.getTargetBlock(sender) ?: return

        if (GachaBlockManager.gachaBlocks.contains(targetBlock)) {
            val message = "${ChatColor.RED}既に追加済です"
            EffectManager.sendErrorMessage(sender, message)
            return
        }

        val currencyItem = item.takeIf { it.type != Material.AIR }

        val isShouldConsume = args.getOrNull(SHOULD_CONSUME_TAB_NUMBER)?.toBooleanStrictOrNull() ?: false

        val gachaBlock = GachaBlock(targetBlock, currencyItem, isShouldConsume)

        GachaBlockManager.addGachaBlock(gachaBlock)

        sendAddMessage(sender, gachaBlock)
    }

    private fun sendAddMessage(
        player: Player,
        gachaBlock: GachaBlock,
    ) {
        val currencyItem = gachaBlock.currencyItem
        val currencyItemInfo: String
        if (currencyItem != null) {
            val currencyItemName = ItemManager.getItemName(currencyItem)
            val currencyItemAmount = currencyItem.amount
            currencyItemInfo = "通貨アイテム：$currencyItemName 個数：$currencyItemAmount}"
        } else {
            currencyItemInfo = "未設定"
        }
        val isShouldConsume = gachaBlock.isShouldConsume

        val message = "${ChatColor.GOLD}ガチャ追加完了\n${ChatColor.AQUA}通貨アイテム：$currencyItemInfo\n消費：$isShouldConsume"
        EffectManager.sendSetMessage(player, message)
    }

    override fun tabComplete(
        sender: CommandSender,
        args: Array<out String>,
    ): List<String> =
        when (args.size) {
            SHOULD_CONSUME_DISPLAY_TAB_NUMBER -> mutableListOf("true", "false")
            else -> mutableListOf()
        }
}
