package com.github.ringoame196

import org.bukkit.plugin.Plugin

object GachaDBManager {
    lateinit var dataBaseManager: DataBaseManager
    private const val FILE_NAME = "data.db"
    private const val TABLE_NAME = "gacha_data"
    private const val WORLD_NAME_KEY = "world_name"
    private const val X_KEY = "x"
    private const val Y_KEY = "y"
    private const val Z_KEY = "z"
    private const val CURRY_ITEM_KEY = "currency_item"
    private const val SHOULD_CONSUME_KEY = "should_consume"

    fun init(plugin: Plugin) {
        dataBaseManager = DataBaseManager(plugin, FILE_NAME)
        dataBaseManager.init()
    }

    fun add(gachaBlock: GachaBlock) {
        val location = gachaBlock.container.location
        val worldName = location.world.name
        val x = location.x
        val y = location.y
        val z = location.z
        val curryItem = gachaBlock.currencyItem
        val isShouldConsume = gachaBlock.isShouldConsume
        if (curryItem != null) {
            val curryItemInfo = ItemStackSerializer.itemStackToBase64(curryItem)
            val sql =
                "INSERT INTO $TABLE_NAME " +
                    "($WORLD_NAME_KEY,$X_KEY,$Y_KEY,$Z_KEY,$CURRY_ITEM_KEY,$SHOULD_CONSUME_KEY) " +
                    "VALUES (?,?,?,?,?,?);"
            dataBaseManager.executeUpdate(
                sql,
                listOf(
                    worldName,
                    x,
                    y,
                    z,
                    curryItemInfo,
                    isShouldConsume,
                ),
            )
        } else {
            val sql =
                "INSERT INTO $TABLE_NAME " +
                    "($WORLD_NAME_KEY,$X_KEY,$Y_KEY,$Z_KEY,$SHOULD_CONSUME_KEY) " +
                    "VALUES (?,?,?,?,?);"
            dataBaseManager.executeUpdate(
                sql,
                listOf(
                    worldName,
                    x,
                    y,
                    z,
                    isShouldConsume,
                ),
            )
        }
    }
}
