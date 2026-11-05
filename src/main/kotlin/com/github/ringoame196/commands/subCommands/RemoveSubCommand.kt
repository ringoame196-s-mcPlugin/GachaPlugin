package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.GachaBlockManager
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

        if (!GachaBlockManager.isGachaBlock(targetBlock)) {
            val message = "登録されていません"
            sender.sendMessage(message)
            return
        }

        GachaBlockManager.removeGachaBlock(targetBlock)
        val message = "ガチャブロックを削除しました"
        sender.sendMessage(message)
    }
}
