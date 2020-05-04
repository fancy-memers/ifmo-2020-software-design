package org.fancy.memers.model

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position3D

sealed class Block: Drawable

class Empty(override val position: Position3D) : Block() {
    override val symbol: Char
        get() = ' '
}

class Wall(override val position: Position3D) : Block() {
    override val symbol: Char
        get() = '#'
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#75715E")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#3E3D32")
}

class Player(override val position: Position3D) : Block() {
    override val symbol: Char
        get() = '@'
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#FFCD22")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#1e2320")
}
