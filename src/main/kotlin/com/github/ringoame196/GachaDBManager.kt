package com.github.ringoame196

import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.block.Container
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
                    location.world.name,
                    location.x,
                    location.y,
                    location.z,
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
                    location.world.name,
                    location.x,
                    location.y,
                    location.z,
                    isShouldConsume,
                ),
            )
        }
    }

    fun remove(targetBlock: Container) {
        val location = targetBlock.location
        val sql = "DELETE FROM $TABLE_NAME WHERE $WORLD_NAME_KEY = ? AND $X_KEY = ? AND $Y_KEY = ? AND $Z_KEY =?;"
        dataBaseManager.executeUpdate(sql, listOf(location.world.name, location.x, location.y, location.z))
    }

    fun loadDB() {
        val sql = "SELECT * FROM $TABLE_NAME"
        dataBaseManager.query(sql) { rows ->
            rows.forEach { row ->
                val worldName = row[WORLD_NAME_KEY] as String
                val x = row[X_KEY] as Int
                val y = row[Y_KEY] as Int
                val z = row[Z_KEY] as Int
                val curryItemBase64 = row[CURRY_ITEM_KEY] as String?
                val isShouldConsume = row[SHOULD_CONSUME_KEY] as? Boolean ?: false
                val curryItem = curryItemBase64?.let { ItemStackSerializer.itemStackFromBase64(it) }

                val world = Bukkit.getWorld(worldName)
                val loc = Location(world, x.toDouble(), y.toDouble(), z.toDouble())

                val block = loc.block
                val state = block.state as? Container ?: return@query
                val gachaBlock = GachaBlock(state, curryItem, isShouldConsume)

                GachaBlockManager.addData(gachaBlock)
            }
        }
    }
}
