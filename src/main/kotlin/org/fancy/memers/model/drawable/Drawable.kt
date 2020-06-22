package org.fancy.memers.model.drawable

import org.fancy.memers.model.create
import org.fancy.memers.model.toHSV
import org.hexworks.zircon.api.color.TileColor
import kotlin.math.abs

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

    val isVisible: Boolean

    val displayName: String
}

/*
    Считает текущий цвет в зависимости от `ratio`
    minimumColor - значение при ratio == 0
    maximumColor – значение при ratio == 1
*/
fun gradientColor(minimumColor: TileColor, maximumColor: TileColor, ratio: Double): TileColor {
    val minColor = minimumColor.toHSV()
    val maxColor = maximumColor.toHSV()
    val differenceH = offset(maxColor.h, minColor.h, ratio)
    val differenceS = offset(maxColor.s, minColor.s, ratio)
    val differenceV = offset(maxColor.v, minColor.v, ratio)
    val newColor = minColor.copy(
        h = abs(minColor.h + differenceH) % 361,
        s = abs(minColor.s + differenceS) % 361,
        v = abs(minColor.v + differenceV) % 361
    )
    return TileColor.create(newColor)
}


private fun offset(minimum: Int, maximum: Int, ratio: Double): Int {
    return ((maximum - minimum) * ratio).toInt()
}
