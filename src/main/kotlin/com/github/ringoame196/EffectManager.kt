package com.github.ringoame196

import org.bukkit.Sound
import org.bukkit.entity.Player

object EffectManager {
    private val ERROR_SOUND = Sound.BLOCK_NOTE_BLOCK_BELL
    private val SET_SOUND = Sound.BLOCK_ANVIL_USE

    fun sendErrorMessage(
        player: Player,
        message: String,
    ) {
        sendMessage(player, message, ERROR_SOUND)
    }

    fun sendSetMessage(
        player: Player,
        message: String,
    ) {
        sendMessage(player, message, SET_SOUND)
    }

    private fun sendMessage(
        player: Player,
        message: String,
        sound: Sound,
    ) {
        player.sendMessage(message)
        player.playSound(player, sound, 1f, 1f)
    }
}
