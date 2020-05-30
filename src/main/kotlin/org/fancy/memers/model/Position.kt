package org.fancy.memers.model

import org.hexworks.zircon.api.data.Position3D

fun Position3D.contains2D(xRange: IntRange, yRange: IntRange): Boolean {
    return xRange.contains(x) && yRange.contains(y)
}

fun Position3D.contains3D(xRange: IntRange, yRange: IntRange, zRange: IntRange): Boolean {
    return xRange.contains(x) && yRange.contains(y) && zRange.contains(z)
}

// позиции соседей по горизонтали и вертикали в радиусе 1 клетка
fun Position3D.hvNeighbours(): List<Position3D> {
    return listOf(1 to 0, -1 to 0, 0 to -1, 0 to 1).map { (xTranslate, yTranslate) ->
        withRelative(Position3D.create(xTranslate, yTranslate, 0))
    }
}

fun Position3D.fetchNeighbours(): List<Position3D> {
    return (-1..1).flatMap { x ->
        (-1..1).map { y ->
            withRelativeX(x).withRelativeY(y)
        }
    }
}
