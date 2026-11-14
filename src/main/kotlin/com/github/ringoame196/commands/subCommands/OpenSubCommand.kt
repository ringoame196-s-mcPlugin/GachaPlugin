package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.managers.GachaBlockManager
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class OpenSubCommand : SubCommand {
    override val name: String = "open"

    override fun execute(
        sender: CommandSender,
        args: Array<out String>,
    ) {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です")
            return
        }
        val targetBlock = GachaBlockManager.getTargetBlock(sender) ?: return
        sender.openInventory(targetBlock.inventory)
    }
}
