package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.GachaBlock
import com.github.ringoame196.GachaBlockManager
import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

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
            val message = "既に追加済です"
            sender.sendMessage(message)
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
            val currencyItemName = getCurrencyItemName(currencyItem)
            val currencyItemAmount = currencyItem.amount
            currencyItemInfo = "通貨アイテム：$currencyItemName 個数：$currencyItemAmount}"
        } else {
            currencyItemInfo = "未設定"
        }
        val isShouldConsume = gachaBlock.isShouldConsume

        val message = "ガチャ追加完了\n通貨アイテム：$currencyItemInfo\n消費：$isShouldConsume"
        player.sendMessage(message)
    }

    private fun getCurrencyItemName(currencyItem: ItemStack): String =
        if (currencyItem.itemMeta.hasDisplayName()) {
            currencyItem.itemMeta.displayName
        } else {
            currencyItem.type.name
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
