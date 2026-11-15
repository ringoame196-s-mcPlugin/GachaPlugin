package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.managers.GachaBlockManager
import com.github.ringoame196.message.MessageKey
import com.github.ringoame196.message.MessageManager
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class RemoveSubCommand(
    private val messageManager: MessageManager,
) : SubCommand {
    override val name: String = "remove"

    override fun execute(
        sender: CommandSender,
        args: Array<out String>,
    ) {
        if (sender !is Player) {
            messageManager.sendMessage(sender, MessageKey.NO_PLAYER_SENDER)
            return
        }
        val targetBlock = GachaBlockManager.getTargetBlock(sender, messageManager) ?: return

        if (!GachaBlockManager.gachaBlocks.contains(targetBlock)) {
            messageManager.sendMessage(sender, MessageKey.NOT_REGISTERED)
            return
        }
        GachaBlockManager.removeGachaBlock(targetBlock)
        messageManager.sendMessage(sender, MessageKey.REMOVE_GACHA_BLOCK)
    }
}
