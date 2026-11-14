package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.GachaBlockManager
import org.bukkit.ChatColor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class TestSubCommand(
    private val plugin: Plugin,
) : SubCommand {
    override val name: String = "test"

    override fun execute(
        sender: CommandSender,
        args: Array<out String>,
    ) {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です")
            return
        }
        val targetBlock = GachaBlockManager.getTargetBlock(sender) ?: return
        val gachaBlock = GachaBlockManager.getGachaponBlock(targetBlock) ?: return
        GachaBlockManager.roll(sender, gachaBlock, plugin)
    }
}
