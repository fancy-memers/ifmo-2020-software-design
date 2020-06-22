package org.fancy.memers.model

import org.hexworks.zircon.api.data.Position3D

/**
 * проверяет содержится ли текущая клетка в заданных интервалах xRange и yRange
 */
fun Position3D.contains2D(xRange: IntRange, yRange: IntRange): Boolean {
    return xRange.contains(x) && yRange.contains(y)
}

/**
 * @return список из 8 клеток соседей
 */
fun Position3D.fetchNeighbours(): List<Position3D> {
    return (-1..1).flatMap { x ->
        (-1..1).map { y ->
            withRelativeX(x).withRelativeY(y)
        }
    }
}
