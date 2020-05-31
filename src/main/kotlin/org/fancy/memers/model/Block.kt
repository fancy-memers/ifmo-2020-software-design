package org.fancy.memers.model

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.Symbols


abstract class Block : Drawable {
    override fun equals(other: Any?): Boolean = this === other || javaClass == other?.javaClass
    override fun hashCode(): Int = javaClass.hashCode()

    abstract val canStepOn: Boolean
    override val isVisible: Boolean get() = true
}

class Empty : Block() {
    override val symbol: Char
        get() = ' '

    override val canStepOn: Boolean
        get() = true
    override val displayName: String
        get() = ""
}

class Floor : Block() {
    override val symbol: Char
        get() = Symbols.INTERPUNCT
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#75715E")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#1e2320", 100)
    override val canStepOn: Boolean
        get() = true
    override val displayName: String
        get() = "Floor"
}

class Wall : Block() {
    override val symbol: Char
        get() = '#'
    override val foregroundColor: TileColor
        get() = TileColor.fromString("#75715E")
    override val backgroundColor: TileColor
        get() = TileColor.fromString("#3E3D32")
    override val canStepOn: Boolean
        get() = false
    override val displayName: String
        get() = "Wall"
}
