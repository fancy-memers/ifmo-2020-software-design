package org.fancy.memers.model

import org.hexworks.zircon.api.color.TileColor


fun TileColor.Companion.fromString(value: String, alpha: Int): TileColor {
    val color = fromString(value)
    return create(color.red, color.green, color.blue, alpha)
}
