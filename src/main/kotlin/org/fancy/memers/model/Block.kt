package org.fancy.memers.model

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.graphics.Symbols

@Serializable
sealed class Block: Drawable {
    abstract fun cloneWithPosition(position: Position3D): Block
}

@Serializable
data class Empty(override val position: @ContextualSerialization Position3D) : Block() {
    override fun cloneWithPosition(position: Position3D): Block = Empty(position)

    override val symbol: Char
        get() = ' '
}

@Serializable
data class Floor(override val position: @ContextualSerialization Position3D) : Block() {
    override fun cloneWithPosition(position: Position3D): Block = Floor(position)

    override val symbol: Char
        get() = Symbols.INTERPUNCT
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#75715E")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#1e2320", 100)
}

@Serializable
data class Wall(override val position: @ContextualSerialization Position3D) : Block() {
    override fun cloneWithPosition(position: Position3D): Block = Wall(position)

    override val symbol: Char
        get() = '#'
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#75715E")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#3E3D32")
}

@Serializable
data class Player(override val position: @ContextualSerialization Position3D) : Block() {
    override fun cloneWithPosition(position: Position3D): Block = Player(position)

    override val symbol: Char
        get() = '@'
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#FFCD22")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#1e2320")
}
