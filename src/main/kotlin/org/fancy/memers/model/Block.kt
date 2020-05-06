package org.fancy.memers.model

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.graphics.Symbols
import java.util.*

sealed class Block : Drawable {
    override fun equals(other: Any?): Boolean = this === other || javaClass == other?.javaClass
    override fun hashCode(): Int = javaClass.hashCode()
}

class Empty : Block() {
    override val symbol: Char
        get() = ' '
}

class Floor : Block() {
    override val symbol: Char
        get() = Symbols.INTERPUNCT
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#75715E")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#1e2320", 100)
}

class Wall : Block() {
    override val symbol: Char
        get() = '#'
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#75715E")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#3E3D32")
}

abstract class Entity(var position: Position3D) : Block() {
    override val symbol: Char
        get() = '@'
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#FFCD22")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#1e2320")

    override fun equals(other: Any?): Boolean = super.equals(other) && position == (other as Entity).position
    override fun hashCode(): Int = Objects.hashCode(position)
}

class Player(position: Position3D) : Entity(position)

class Enemy(position: Position3D) : Entity(position)
