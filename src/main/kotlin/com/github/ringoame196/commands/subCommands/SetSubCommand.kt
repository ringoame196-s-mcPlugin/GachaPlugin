package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.managers.GachaBlockManager
import com.github.ringoame196.managers.ItemManager
import com.github.ringoame196.message.MessageKey
import com.github.ringoame196.message.MessageManager
import com.github.ringoame196.model.GachaBlock
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSubCommand(
    private val messageManager: MessageManager,
) : SubCommand {
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
            messageManager.sendMessage(sender, MessageKey.NO_PLAYER_SENDER)
            return
        }
        val item = sender.inventory.itemInMainHand
        val targetBlock = GachaBlockManager.getTargetBlock(sender, messageManager) ?: return

        if (GachaBlockManager.gachaBlocks.contains(targetBlock)) {
            messageManager.sendMessage(sender, MessageKey.REMOVE_GACHA_BLOCK)
            messageManager.sendMessage(sender, MessageKey.ALREADY_REGISTERED)
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

        messageManager.sendMessage(
            player,
            MessageKey.ADD_GACHA_BLOCK,
            Pair("{currencyItemInfo}", currencyItemInfo),
            Pair("{isShouldConsume}", isShouldConsume.toString()),
        )
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
