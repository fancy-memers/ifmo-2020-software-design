package org.fancy.memers.model.drawable

import org.hexworks.zircon.api.color.TileColor

sealed class Item : Floor() {
    abstract val attackBonus: Int
    abstract val defenceBonus: Int

    open val strength: Int = 0
    open val agility: Int = 0
    open val intelligence: Int = 0
}

sealed class Weapon: Item() {
    override val defenceBonus: Int
        get() = 0

    override val foregroundColor: TileColor
        get() = TileColor.fromString("#E600FF")
}

sealed class Armor: Item() {
    override val attackBonus: Int
        get() = 0

    override val foregroundColor: TileColor
        get() = TileColor.fromString("#0000FF")
}

class Knife private constructor(): Weapon() {
    override val attackBonus: Int
        get() = 3
    override val canStepOn: Boolean
        get() = true
    override val symbol: Char
        get() = '^'
    override val displayName: String
        get() = "Offhand Lambda Knife"

    override val agility: Int
        get() = 12

    companion object {
        val INSTANCE: Knife = Knife()
    }
}

class Mace private constructor() : Item() {
    override val attackBonus: Int
        get() = 7
    override val defenceBonus: Int
        get() = 4
    override val canStepOn: Boolean
        get() = true
    override val symbol: Char
        get() = '*'
    override val displayName: String
        get() = "Head Smashing Mace"

    override val strength: Int
        get() = 4

    companion object {
        val INSTANCE: Mace = Mace()
    }
}

class PlateMail private constructor(): Armor() {
    override val defenceBonus: Int
        get() = 10
    override val canStepOn: Boolean
        get() = true
    override val symbol: Char
        get() = 'p'
    override val displayName: String
        get() = "Plate Mail"

    override val strength: Int
        get() = 10
    override val agility: Int
        get() = 4

    companion object {
        val INSTANCE: PlateMail = PlateMail()
    }
}

class Boots private constructor(): Armor() {
    override val defenceBonus: Int
        get() = 10
    override val canStepOn: Boolean
        get() = true
    override val symbol: Char
        get() = 'g'
    override val displayName: String
        get() = "Iron Greaves"

    override val strength: Int
        get() = 6
    override val agility: Int
        get() = 2

    companion object {
        val INSTANCE: Boots = Boots()
    }
}
