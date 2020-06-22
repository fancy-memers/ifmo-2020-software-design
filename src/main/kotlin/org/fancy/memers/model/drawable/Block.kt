package org.fancy.memers.model.drawable

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.Symbols

/**
 * Класс описывающий один игровой объект (клетку или entity) с точки зрения модели
 */
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

/**
 * Пустая клетка игрового поля, по которой может перемещаться игрок
 */
open class Floor : Block() {
    override val symbol: Char
        get() = Symbols.INTERPUNCT
    override val foregroundColor: TileColor
        get() = FOREGROUND_COLOR
    override val backgroundColor: TileColor
        get() = BACKGROUND_COLOR
    override val canStepOn: Boolean
        get() = true
    override val displayName: String
        get() = "Floor"

    companion object {
        private val FOREGROUND_COLOR: TileColor = TileColor.fromString("#75715E")
        private val BACKGROUND_COLOR: TileColor = TileColor.fromString("#1e2320")
    }
}

/**
 * Занятая клетка игрового поля, по которой не может перемещаться игрок
 */
class Wall : Block() {
    override val symbol: Char
        get() = '#'
    override val foregroundColor: TileColor
        get() = FOREGROUND_COLOR
    override val backgroundColor: TileColor
        get() = BACKGROUND_COLOR
    override val canStepOn: Boolean
        get() = false
    override val displayName: String
        get() = "Wall"

    companion object {
        private val FOREGROUND_COLOR: TileColor = TileColor.fromString("#75715E")
        private val BACKGROUND_COLOR: TileColor = TileColor.fromString("#3E3D32")
    }
}
