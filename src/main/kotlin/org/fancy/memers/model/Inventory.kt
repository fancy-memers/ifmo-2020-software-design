package org.fancy.memers.model

data class Item(
    // прибавляется к значению атаки игрока
    val attackBonus: Int,
    // вычитается из значения здоровья игрока
    val defenceBonus: Int,
    val symbol: Char
)

class Inventory {
    val items: MutableSet<Item> = mutableSetOf()

    // надетые предметы, подмножество items
    val activeItems: MutableSet<Item> = mutableSetOf()
}
