package org.fancy.memers.model

import kotlinx.serialization.ContextualSerialization
import kotlinx.serialization.Serializable
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.graphics.Symbols

@Serializable
sealed class Block : Drawable

@Serializable
data class Empty(override var position: @ContextualSerialization Position3D) : Block() {
    override val symbol: Char
        get() = ' '
}

@Serializable
data class Floor(override var position: @ContextualSerialization Position3D) : Block() {
    override val symbol: Char
        get() = Symbols.INTERPUNCT
    override val foregroundColor: TileColor
        get() = FOREGROUND_COLOR
    override val backgroundColor: TileColor
        get() = BACKGROUND_COLOR

    companion object {
        private val FOREGROUND_COLOR: TileColor = TileColor.fromString("#75715E")
        private val BACKGROUND_COLOR: TileColor = TileColor.fromString("#1e2320", 100)
    }
}

@Serializable
data class Wall(override var position: @ContextualSerialization Position3D) : Block() {
    override val symbol: Char
        get() = '#'
    override val foregroundColor: TileColor
        get() = FOREGROUND_COLOR
    override val backgroundColor: TileColor
        get() = BACKGROUND_COLOR

    companion object {
        private val FOREGROUND_COLOR: TileColor = TileColor.fromString("#75715E")
        private val BACKGROUND_COLOR: TileColor = TileColor.fromString("#3E3D32")
    }
}

@Serializable
data class Player(override var position: @ContextualSerialization Position3D) : Block() {
    override val symbol: Char
        get() = '@'
    override val foregroundColor: TileColor
        get() = FOREGROUND_COLOR
    override val backgroundColor: TileColor
        get() = BACKGROUND_COLOR

    companion object {
        private val FOREGROUND_COLOR: TileColor = TileColor.fromString("#FFCD22")
        private val BACKGROUND_COLOR: TileColor = TileColor.fromString("#1e2320")
    }
}
