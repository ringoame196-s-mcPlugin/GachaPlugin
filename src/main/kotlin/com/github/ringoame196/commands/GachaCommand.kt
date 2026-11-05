package com.github.ringoame196.commands

import com.github.ringoame196.commands.subCommands.RemoveSubCommand
import com.github.ringoame196.commands.subCommands.SetSubCommand
import com.github.ringoame196.commands.subCommands.SubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender

class GachaCommand : BaseCommand() {
    override val subCommands: Map<String, SubCommand> =
        listOf(SetSubCommand(), RemoveSubCommand()).associateBy {
            it.name
        }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean = super.onCommand(sender, command, label, args)
}
