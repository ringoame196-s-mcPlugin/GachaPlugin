package com.github.ringoame196

import org.bukkit.ChatColor
import org.bukkit.Material
import org.bukkit.block.Container
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
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

    fun getTargetBlock(player: Player): Container? {
        val targetBlock = player.getTargetBlockExact(MAX_DISTANCE)

        if (targetBlock == null) {
            val message = "${ChatColor.RED}ブロックを指定してください"
            EffectManager.sendErrorMessage(player, message)
            return null
        }

        val state = targetBlock.state
        if (state !is Container) {
            val message = "${ChatColor.RED}コンテナブロックのみ追加可能です"
            EffectManager.sendErrorMessage(player, message)
            return null
        }
        return state
    }

    fun getGachaponBlock(container: Container): GachaBlock? = gachaBlockMap[container]

    fun roll(
        player: Player,
        gachaBlock: GachaBlock,
    ) {
        val item = player.inventory.itemInMainHand
        val currentItem = gachaBlock.currencyItem

        if (!isRoll(item, currentItem)) {
            val message = "${ChatColor.RED}消費アイテムが必要です"
            EffectManager.sendErrorMessage(player, message)
            return
        }

        val container = gachaBlock.container
        val randomItem = getRandomItem(container)

        if (randomItem == null) {
            val message = "${ChatColor.RED}出せるアイテムがありませんでした"
            EffectManager.sendErrorMessage(player, message)
            return
        }

        removeCurryItem(player, currentItem, item)

        if (gachaBlock.isShouldConsume) {
            removeGachaBlockItem(container, randomItem)
        }
        EffectManager.playRoll(player)
        player.inventory.addItem(randomItem)
    }

    private fun isRoll(
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
