package org.fancy.memers.model

import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

fun Size3D.fetchFloorPositions(): Sequence<Position3D> = fetchWithZCoordinate(0)

fun Size3D.fetchWithZCoordinate(z: Int): Sequence<Position3D> = fetchPositions().filter { it.z == z }
