package com.github.ringoame196

import org.bukkit.Bukkit
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.Particle
import org.bukkit.Sound
import org.bukkit.entity.Display
import org.bukkit.entity.ItemDisplay
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin

object EffectManager {
    private val ERROR_SOUND = Sound.BLOCK_NOTE_BLOCK_BELL
    private val SET_SOUND = Sound.BLOCK_ANVIL_USE
    private val ROLL_SOUND = Sound.BLOCK_DISPENSER_FAIL
    private val HIT_SOUND = Sound.BLOCK_ANVIL_USE
    private val HIT_PARTICLE = Particle.EXPLOSION_LARGE
    private const val HIT_PARTICLE_COUNT = 10
    private const val HIT_PARTICLE_ADD_X = 0.5
    private const val HIT_PARTICLE_ADD_Y = 1.0
    private const val HIT_PARTICLE_ADD_Z = 0.5

    private const val DISPLAY_ADD_X = 0.5
    private const val DISPLAY_ADD_Y = 1.5
    private const val DISPLAY_ADD_Z = 0.5
    private const val ROLL_COUNT = 15
    private const val INTERVAL_TICKS = 10L
    private const val REMOVE_DISPLAY_TIME = 20L

    private val _effectingBlocks = mutableSetOf<Location>()
    val effectingBlocks: Set<Location> get() = _effectingBlocks

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

    fun roll(
        player: Player,
        gachaBlock: GachaBlock,
        hitItem: ItemStack,
        plugin: Plugin,
    ) {
        val container = gachaBlock.container
        val location = container.location
        val itemDisplay = summonRollDisplay(location) ?: return
        val inventory = container.inventory
        val itemList = inventory.contents.filter { it != null && it.type != Material.AIR }
        if (itemList.isEmpty()) {
            itemDisplay.remove()
            return
        }
        _effectingBlocks.add(location)

        for (i in 0 until ROLL_COUNT) {
            val delay = i * INTERVAL_TICKS
            Bukkit.getScheduler().runTaskLater(
                plugin,
                Runnable {
                    if (!itemDisplay.isValid) return@Runnable // 万が一消えていたら中断
                    val nextItem = itemList.random() ?: return@Runnable
                    nextDisplayItem(itemDisplay, nextItem)
                    player.playSound(player, ROLL_SOUND, 1f, 1f)
                },
                delay,
            )
        }

        // 当たりアイテム演出
        val finalDelay = ROLL_COUNT * INTERVAL_TICKS
        Bukkit.getScheduler().runTaskLater(
            plugin,
            Runnable {
                if (!itemDisplay.isValid) return@Runnable
                hit(itemDisplay, hitItem, player, location)
            },
            finalDelay,
        )

        val removeDelay = finalDelay + REMOVE_DISPLAY_TIME
        Bukkit.getScheduler().runTaskLater(
            plugin,
            Runnable {
                if (itemDisplay.isValid) {
                    itemDisplay.remove()
                }
                _effectingBlocks.remove(location)
            },
            removeDelay,
        )
    }

    private fun summonRollDisplay(location: Location): ItemDisplay? {
        val location =
            location
                .clone()
                .add(DISPLAY_ADD_X, DISPLAY_ADD_Y, DISPLAY_ADD_Z)
        val world = location.world ?: return null
        val itemDisplay =
            world.spawn(location, ItemDisplay::class.java) { display ->
                display.billboard = Display.Billboard.CENTER // 常にプレイヤーの方を向く設定
            }
        return itemDisplay
    }

    private fun nextDisplayItem(
        itemDisplay: ItemDisplay,
        item: ItemStack,
    ) {
        itemDisplay.itemStack = item
    }

    private fun hit(
        itemDisplay: ItemDisplay,
        hitItem: ItemStack,
        player: Player,
        location: Location,
    ) {
        nextDisplayItem(itemDisplay, hitItem)
        player.inventory.addItem(hitItem)
        player.playSound(itemDisplay.location, HIT_SOUND, 1f, 1f)

        val particleLocation = location.clone().add(HIT_PARTICLE_ADD_X, HIT_PARTICLE_ADD_Y, HIT_PARTICLE_ADD_Z)
        particleLocation.world.spawnParticle(HIT_PARTICLE, particleLocation, HIT_PARTICLE_COUNT)

        val message = "${ChatColor.YELLOW}[結果] ${ItemManager.getItemName(hitItem)}"
        player.sendMessage(message)
    }
}
