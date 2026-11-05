package com.github.ringoame196.commands

import org.bukkit.block.Container

object GachaBlockManager {
    private val gachaBlockMap = mutableMapOf<Container, GachaBlock>()

    fun addGachaBlock(gachaBlock: GachaBlock) {
        gachaBlockMap[gachaBlock.container] = gachaBlock
    }

    fun isGachaBlock(container: Container): Boolean = gachaBlockMap.keys.contains(container)
}
