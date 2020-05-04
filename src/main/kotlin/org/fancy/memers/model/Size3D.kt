package org.fancy.memers.model

import org.hexworks.zircon.api.data.Size3D

fun Size3D.fetchFloorPositions() = fetchWithZCoordinate(0)

fun Size3D.fetchWithZCoordinate(z: Int) = fetchPositions().filter { it.z == z }
