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

        if (GachaBlockManager.isGachaBlock(targetBlock)) {
            val message = "既に追加済です"
            sender.sendMessage(message)
            return
        }

        val currencyItem =
            if (item.type == Material.AIR) {
                null
            } else {
                item
            }

        val gachaBlock = GachaBlock(targetBlock, currencyItem)

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

        val message = "ガチャ追加完了\n通貨アイテム：$currencyItemInfo"
        player.sendMessage(message)
    }

    private fun getCurrencyItemName(currencyItem: ItemStack): String =
        if (currencyItem.itemMeta.hasDisplayName()) {
            currencyItem.itemMeta.displayName
        } else {
            currencyItem.type.name
        }
}
