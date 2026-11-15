package com.github.ringoame196.message

import org.bukkit.Sound

enum class MessageKey(
    val path: String,
    val sound: Sound? = null,
) {
    NO_PLAYER_SENDER("no_player_sender", Sound.BLOCK_NOTE_BLOCK_BELL),
    REMOVE_GACHA_BLOCK("remove_gacha_block", Sound.BLOCK_ANVIL_USE),
    ALREADY_REGISTERED("already_registered", Sound.BLOCK_NOTE_BLOCK_BELL),
    NOT_REGISTERED("not_registered", Sound.BLOCK_NOTE_BLOCK_BELL),
    ADD_GACHA_BLOCK("add_gacha_block", Sound.BLOCK_ANVIL_USE),
    NO_BREAK_GACHA_BLOCK("no_break_gacha_block", Sound.BLOCK_NOTE_BLOCK_BELL),
    REQUIRED_CURRENCY_ITEM("required_currency_item", Sound.BLOCK_NOTE_BLOCK_BELL),
    NO_TARGET_BLOCK("no_target_block", Sound.BLOCK_NOTE_BLOCK_BELL),
    NO_BARREL("no_barrel", Sound.BLOCK_NOTE_BLOCK_BELL),
    WAIT_ROLL("wait_roll", Sound.BLOCK_NOTE_BLOCK_BELL),
    NO_HIT_ITEM("no_hit_item", Sound.BLOCK_NOTE_BLOCK_BELL),
    RESULT("result"),
    RELOAD_MESSAGE("reload_message"),
}
