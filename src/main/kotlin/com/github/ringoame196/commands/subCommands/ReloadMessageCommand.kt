package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.message.MessageKey
import com.github.ringoame196.message.MessageManager
import org.bukkit.command.CommandSender

class ReloadMessageCommand(
    private val messageManager: MessageManager,
) : SubCommand {
    override val name: String = "reload_message"

    override fun execute(
        sender: CommandSender,
        args: Array<out String>,
    ) {
        messageManager.reload()
        messageManager.sendMessage(sender, MessageKey.RELOAD_MESSAGE)
    }
}
