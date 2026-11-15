package com.github.ringoame196.managers

import com.github.ringoame196.message.MessageKey
import com.github.ringoame196.message.MessageManager
import com.github.ringoame196.model.GachaBlock
import org.bukkit.Material
import org.bukkit.block.Barrel
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import kotlin.random.Random

object GachaBlockManager {
    private val gachaBlockMap = mutableMapOf<Container, GachaBlock>()
    private const val MAX_DISTANCE = 10
    val gachaBlocks get() = gachaBlockMap.keys.toSet()

    fun addGachaBlock(gachaBlock: GachaBlock) {
        gachaBlockMap[gachaBlock.container] = gachaBlock
        GachaDBManager.add(gachaBlock)
    }

    fun addData(gachaBlock: GachaBlock) {
        gachaBlockMap[gachaBlock.container] = gachaBlock
    }

    fun removeGachaBlock(targetBlock: Container) {
        gachaBlockMap.remove(targetBlock)
        GachaDBManager.remove(targetBlock)
    }

    fun getTargetBlock(
        player: Player,
        messageManager: MessageManager,
    ): Container? {
        val targetBlock = player.getTargetBlockExact(MAX_DISTANCE)

        if (targetBlock == null) {
            messageManager.sendMessage(player, MessageKey.NO_TARGET_BLOCK)
            return null
        }

        val state = targetBlock.state
        if (state !is Barrel) {
            messageManager.sendMessage(player, MessageKey.NO_BARREL)
            return null
        }

        return state
    }

    fun getGachaponBlock(container: Container): GachaBlock? = gachaBlockMap[container]

    fun roll(
        player: Player,
        gachaBlock: GachaBlock,
        plugin: Plugin,
        messageManager: MessageManager,
    ) {
        val item = player.inventory.itemInMainHand
        val currentItem = gachaBlock.currencyItem

        if (EffectManager.effectingBlocks.contains(gachaBlock.container.location)) {
            messageManager.sendMessage(player, MessageKey.WAIT_ROLL)
            return
        }

        val container = gachaBlock.container
        val hitItem = getRandomItem(container)

        if (hitItem == null) {
            messageManager.sendMessage(player, MessageKey.NO_HIT_ITEM)
            return
        }

        removeCurryItem(player, currentItem, item)

        if (gachaBlock.isShouldConsume) {
            removeGachaBlockItem(container, hitItem)
        }
        EffectManager.roll(player, gachaBlock, hitItem, plugin, messageManager)
    }

    fun isRoll(
        item: ItemStack,
        currentItem: ItemStack?,
    ): Boolean {
        currentItem ?: return true

        if (!item.isSimilar(currentItem)) {
            return false
        }
        return item.amount >= currentItem.amount
    }

    private fun getRandomItem(container: Container): ItemStack? {
        val inventory = container.inventory
        val validItems =
            inventory.contents
                .filterNotNull()
                .filter { it.type != Material.AIR }

        if (validItems.isEmpty()) return null
        val totalAmount = validItems.sumOf { it.amount }
        if (totalAmount <= 0) return null

        var randomWeight = Random.nextInt(totalAmount)

        for (item in validItems) {
            if (randomWeight < item.amount) {
                val singleItem = item.clone()
                singleItem.amount = 1
                return singleItem
            }
            randomWeight -= item.amount
        }

        return null
    }

    private fun removeCurryItem(
        player: Player,
        currentItem: ItemStack?,
        item: ItemStack,
    ) {
        if (currentItem != null) {
            val amount = item.clone().amount
            val currentItemAmount = currentItem.clone().amount
            val newAmount = amount - currentItemAmount
            if (newAmount <= 0) {
                player.inventory.setItemInMainHand(null)
            } else {
                player.inventory.itemInMainHand.amount = newAmount
            }
        }
    }

    private fun removeGachaBlockItem(
        container: Container,
        randomItem: ItemStack,
    ) {
        container.inventory.removeItem(randomItem)
    }
}
