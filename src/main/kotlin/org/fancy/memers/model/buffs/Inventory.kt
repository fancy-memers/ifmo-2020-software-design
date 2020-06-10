package org.fancy.memers.model.buffs

import org.fancy.memers.model.drawable.Item

// Инвентарь – надетые предметы
data class Inventory(private val items: MutableSet<Item> = mutableSetOf()) : MutableSet<Item> by items

