package com.github.ringoame196

import com.github.ringoame196.commands.GachaCommand
import com.github.ringoame196.events.Events
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {
    companion object {
        // 外部クラスから Main.plugin でアクセスできるようにする
        lateinit var plugin: Main
            private set
    }

    override fun onEnable() {
        plugin = this
        super.onEnable()
        logger.info("${description.name} が有効化されました！")

        registerEvents()
        registerCommands()
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(Events(), plugin)
    }

    private fun registerCommands() {
        getCommand("gacha")?.setExecutor(GachaCommand())
    }

    override fun onDisable() {
        // プラグイン無効化時の処理
        logger.info("${description.name} が無効化されました。")
    }
}
