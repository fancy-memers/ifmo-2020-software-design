package org.fancy.memers.model.drawable

import org.hexworks.zircon.api.color.TileColor

sealed class Item : Floor() {
    abstract val attackBonus: Int
    abstract val defenceBonus: Int

    override val foregroundColor: TileColor
        get() = TileColor.fromString("#ffff00")
}

object Knife : Item() {
    override val attackBonus: Int
        get() = 3
    override val defenceBonus: Int
        get() = 0
    override val canStepOn: Boolean
        get() = true
    override val symbol: Char
        get() = '^'
    override val displayName: String
        get() = "Knife"
}

object Mace : Item() {
    override val attackBonus: Int
        get() = 7
    override val defenceBonus: Int
        get() = 0
    override val canStepOn: Boolean
        get() = true
    override val symbol: Char
        get() = '*'
    override val displayName: String
        get() = "Mace"
}

object Armor : Item() {
    override val attackBonus: Int
        get() = 0
    override val defenceBonus: Int
        get() = 10
    override val canStepOn: Boolean
        get() = true
    override val symbol: Char
        get() = '+'
    override val displayName: String
        get() = "Armor"
}
