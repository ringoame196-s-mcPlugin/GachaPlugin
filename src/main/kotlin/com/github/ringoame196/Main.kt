package com.github.ringoame196

import com.github.ringoame196.commands.GachaCommand
import com.github.ringoame196.events.Events
import com.github.ringoame196.message.MessageManager
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

        @Suppress("UnusedPrivateMember")
        val messageManager = MessageManager(plugin)

        registerEvents()
        registerCommands(messageManager)
    }

    private fun registerEvents() {
        server.pluginManager.registerEvents(Events(), plugin)
    }

    private fun registerCommands(messageManager: MessageManager) {
        getCommand("gacha")?.setExecutor(GachaCommand(messageManager))
    }

    override fun onDisable() {
        // プラグイン無効化時の処理
        logger.info("${description.name} が無効化されました。")
    }
}
