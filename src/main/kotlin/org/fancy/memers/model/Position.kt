package org.fancy.memers.model

data class Position(val x: Int, val y: Int)

fun Position.contains(xRange: IntRange, yRange: IntRange): Boolean {
    return xRange.contains(x) && yRange.contains(y)
}