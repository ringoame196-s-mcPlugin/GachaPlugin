package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.managers.EffectManager
import com.github.ringoame196.managers.GachaBlockManager
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RemoveSubCommand : SubCommand {
    override val name: String = "remove"

    override fun execute(
        sender: CommandSender,
        args: Array<out String>,
    ) {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です")
            return
        }
        val targetBlock = GachaBlockManager.getTargetBlock(sender) ?: return

        if (!GachaBlockManager.gachaBlocks.contains(targetBlock)) {
            val message = "${ChatColor.RED}登録されていません"
            EffectManager.sendErrorMessage(sender, message)
            return
        }
        GachaBlockManager.removeGachaBlock(targetBlock)

        sendRemoveMessage(sender)
    }

    private fun sendRemoveMessage(player: Player) {
        val message = "ガチャブロックを削除しました"
        EffectManager.sendSetMessage(player, message)
    }
}
