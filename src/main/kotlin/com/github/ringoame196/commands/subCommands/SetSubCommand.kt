package com.github.ringoame196.commands.subCommands

import com.github.ringoame196.commands.GachaBlock
import com.github.ringoame196.commands.GachaBlockManager
import org.bukkit.ChatColor
import org.bukkit.block.Container
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class SetSubCommand : SubCommand {
    override val name: String = "set"

    companion object {
        const val MAX_DISTANCE = 10
    }

    override fun execute(
        sender: CommandSender,
        args: Array<out String>,
    ) {
        if (sender !is Player) {
            sender.sendMessage("${ChatColor.RED}このコマンドはプレイヤーのみ実行可能です")
            return
        }
        val targetBlock = getTargetBlock(sender) ?: return

        val gachaBlock = GachaBlock(targetBlock)

        if (GachaBlockManager.isGachaBlock(gachaBlock)) {
            val message = "既に追加済です"
            sender.sendMessage(message)
            return
        }

        GachaBlockManager.addGachaBlock(gachaBlock)
        val message = "ガチャに追加しました"
        sender.sendMessage(message)
    }

    private fun getTargetBlock(player: Player): Container? {
        val targetBlock = player.getTargetBlockExact(MAX_DISTANCE)

        if (targetBlock == null) {
            val message = "ブロックを指定してください"
            player.sendMessage(message)
            return null
        }

        val state = targetBlock.state
        if (state !is Container) {
            val message = "コンテナブロックのみ追加可能です"
            player.sendMessage(message)
            return null
        }
        return state
    }
}
