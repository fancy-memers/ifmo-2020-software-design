package org.fancy.memers.model

import org.hexworks.zircon.api.color.TileColor

interface Drawable {
    val symbol: Char

    val foregroundColor: TileColor
        get() = TileColor.defaultForegroundColor()

    val backgroundColor: TileColor
        get() = TileColor.defaultBackgroundColor()

    val position: Position
}