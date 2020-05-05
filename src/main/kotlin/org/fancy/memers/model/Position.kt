package org.fancy.memers.model

import org.hexworks.zircon.api.data.Position3D

fun Position3D.contains2D(xRange: IntRange, yRange: IntRange): Boolean {
    return xRange.contains(x) && yRange.contains(y)
}

fun Position3D.contains3D(xRange: IntRange, yRange: IntRange, zRange: IntRange): Boolean {
    return xRange.contains(x) && yRange.contains(y) && zRange.contains(z)
}

fun Position3D.fetchNeighbours(): List<Position3D> {
    return (-1..1).flatMap { x ->
        (-1..1).map { y ->
            withRelativeX(x).withRelativeY(y)
        }
    }
}
