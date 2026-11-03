package com.github.ringoame196.commands

object GachaBlockManager {
    private val gachaBlockList = mutableListOf<GachaBlock>()

    fun addGachaBlock(gachaBlock: GachaBlock) {
        gachaBlockList.add(gachaBlock)
    }

    fun isGachaBlock(gachaBlock: GachaBlock): Boolean = gachaBlockList.contains(gachaBlock)
}
