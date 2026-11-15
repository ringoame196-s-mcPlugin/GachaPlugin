package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.managers.GachaBlockManager
import com.github.ringoame196.message.MessageKey
import com.github.ringoame196.message.MessageManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin

class TestSubCommand(
    private val plugin: Plugin,
    private val messageManager: MessageManager,
) : SubCommand {
    override val name: String = "test"

    override fun execute(
        sender: CommandSender,
        args: Array<out String>,
    ) {
        if (sender !is Player) {
            messageManager.sendMessage(sender, MessageKey.NO_PLAYER_SENDER)
            return
        }
        val targetBlock = GachaBlockManager.getTargetBlock(sender, messageManager) ?: return
        val gachaBlock = GachaBlockManager.getGachaponBlock(targetBlock)

        if (gachaBlock == null) {
            messageManager.sendMessage(sender, MessageKey.NOT_REGISTERED)
            return
        }

        GachaBlockManager.roll(sender, gachaBlock, plugin, messageManager)
    }
}
