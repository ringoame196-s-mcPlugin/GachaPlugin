package com.github.ringoame196.commands

import com.github.ringoame196.commands.subCommands.OpenSubCommand
import com.github.ringoame196.commands.subCommands.RemoveSubCommand
import com.github.ringoame196.commands.subCommands.SetSubCommand
import com.github.ringoame196.commands.subCommands.SubCommand
import com.github.ringoame196.commands.subCommands.TestSubCommand
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.plugin.Plugin

class GachaCommand(
    plugin: Plugin,
) : BaseCommand() {
    override val subCommands: Map<String, SubCommand> =
        listOf(
            SetSubCommand(),
            RemoveSubCommand(),
            OpenSubCommand(),
            TestSubCommand(plugin),
        ).associateBy {
            it.name
        }

    override fun onCommand(
        sender: CommandSender,
        command: Command,
        label: String,
        args: Array<out String>,
    ): Boolean = super.onCommand(sender, command, label, args)
}
