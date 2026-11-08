package com.github.ringoame196

import org.bukkit.Sound
import org.bukkit.entity.Player

object EffectManager {
    private val ERROR_SOUND = Sound.BLOCK_NOTE_BLOCK_BELL

    fun sendErrorMessage(
        player: Player,
        message: String,
    ) {
        player.sendMessage(message)
        player.playSound(player, ERROR_SOUND, 1f, 1f)
    }
}
