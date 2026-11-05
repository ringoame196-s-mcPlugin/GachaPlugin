package com.github.ringoame196

import org.bukkit.block.Container
import org.bukkit.inventory.ItemStack

data class GachaBlock(
    val container: Container,
    val currencyItem: ItemStack?,
    val isShouldConsume: Boolean,
)
