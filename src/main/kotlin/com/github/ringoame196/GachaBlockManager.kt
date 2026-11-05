package com.github.ringoame196

import org.bukkit.block.Container
import org.bukkit.entity.Player

object GachaBlockManager {
    private val gachaBlockMap = mutableMapOf<Container, GachaBlock>()
    private const val MAX_DISTANCE = 10

    fun addGachaBlock(gachaBlock: GachaBlock) {
        gachaBlockMap[gachaBlock.container] = gachaBlock
    }

    fun removeGachaBlock(targetBlock: Container) {
        gachaBlockMap.remove(targetBlock)
    }

    fun isGachaBlock(container: Container): Boolean = gachaBlockMap.keys.contains(container)

    fun getTargetBlock(player: Player): Container? {
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