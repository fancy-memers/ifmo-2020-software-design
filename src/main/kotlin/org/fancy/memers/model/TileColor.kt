package org.fancy.memers.model

import com.github.ajalt.colormath.ConvertibleColor
import com.github.ajalt.colormath.HSV
import com.github.ajalt.colormath.RGB
import org.hexworks.zircon.api.color.TileColor


fun TileColor.Companion.fromString(value: String, alpha: Int): TileColor {
    val color = fromString(value)
    return create(color.red, color.green, color.blue, alpha)
}

fun TileColor.Companion.create(source: ConvertibleColor): TileColor {
    val rgb = source.toRGB()
    return create(rgb.r, rgb.g, rgb.b)
}

fun TileColor.toHSV(): HSV {
    return RGB(red, green, blue).toHSV()
}
