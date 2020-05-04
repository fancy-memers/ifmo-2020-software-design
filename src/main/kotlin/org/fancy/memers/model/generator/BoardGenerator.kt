package org.fancy.memers.model.generator

import org.fancy.memers.model.Block
import org.hexworks.zircon.api.data.Position3D

interface BoardGenerator {
    fun generateMap(): Map<Position3D, Block>
}