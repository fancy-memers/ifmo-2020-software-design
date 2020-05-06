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

sealed class Entity(
    var position: Position3D,
    val attack: Int = DEFAULT_ATTACK,
    var health: Int = INITIAL_HEALTH
) : Block() {
    override fun equals(other: Any?): Boolean = super.equals(other) && position == (other as Entity).position
    override fun hashCode(): Int = Objects.hashCode(position)

    companion object {
        const val DEFAULT_ATTACK: Int = 20
        const val INITIAL_HEALTH: Int = 100
    }
}

class Player(position: Position3D) : Entity(position) {
    override val symbol: Char get() = '@'
    override val foregroundColor: TileColor get() = TileColor.fromString("#FFCD22")
    override val backgroundColor: TileColor get() = TileColor.fromString("#1e2320")
}

class Enemy(position: Position3D) : Entity(position) {
    override val symbol: Char get() = 'E'
    override val foregroundColor: TileColor get() = TileColor.fromString("#FF0000")
    override val backgroundColor: TileColor get() = TileColor.fromString("#1e2320")
}
