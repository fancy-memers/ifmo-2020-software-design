package org.fancy.memers.model

import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Position3D

/**
 * Класс описывающий один игровой объект (клетку или entity) с точки зрения представления (view) на экране
 */
interface Drawable {
    /** символ для отображения на экране */
    val symbol: Char

    /** цвет символа */
    val foregroundColor: TileColor
        get() = TileColor.defaultForegroundColor()

    /** цвет клетки (за символом) */
    val backgroundColor: TileColor
        get() = TileColor.defaultBackgroundColor()

    var position: Position3D
}
