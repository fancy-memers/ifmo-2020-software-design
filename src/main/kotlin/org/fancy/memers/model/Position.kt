package org.fancy.memers.model

import org.hexworks.zircon.api.data.Position3D

fun Position3D.contains2D(xRange: IntRange, yRange: IntRange): Boolean {
    return xRange.contains(x) && yRange.contains(y)
}

fun Position3D.contains3D(xRange: IntRange, yRange: IntRange, zRange: IntRange): Boolean {
    return xRange.contains(x) && yRange.contains(y) && zRange.contains(z)
}
