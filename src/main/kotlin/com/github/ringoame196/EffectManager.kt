package com.github.ringoame196

import org.bukkit.Sound
import org.bukkit.entity.Display
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object EffectManager {
    private val ERROR_SOUND = Sound.BLOCK_NOTE_BLOCK_BELL
    private val SET_SOUND = Sound.BLOCK_ANVIL_USE

    private const val DISPLAY_ADD_X = 0.5
    private const val DISPLAY_ADD_Y = 1.5
    private const val DISPLAY_ADD_Z = 0.5

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

    fun summonRollDisplay(gachaBlock: GachaBlock): ItemDisplay? {
        val location =
            gachaBlock.container.location
                .clone()
                .add(DISPLAY_ADD_X, DISPLAY_ADD_Y, DISPLAY_ADD_Z)
        val world = location.world ?: return null
        val itemDisplay =
            world.spawn(location, ItemDisplay::class.java) { display ->
                display.billboard = Display.Billboard.CENTER // 常にプレイヤーの方を向く設定
            }
        return itemDisplay
    }

    fun nextItem(
        itemDisplay: ItemDisplay,
        item: ItemStack,
    ) {
        itemDisplay.itemStack = item
    }
}
