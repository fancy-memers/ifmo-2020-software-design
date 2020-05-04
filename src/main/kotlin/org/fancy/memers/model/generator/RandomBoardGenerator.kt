package org.fancy.memers.model.generator

import org.fancy.memers.model.Block
import org.hexworks.zircon.api.data.Position3D
import kotlin.random.Random

abstract class RandomBoardGenerator(seed: Int? = null) : BoardGenerator {
    protected val random: Random = seed?.let { Random(it) } ?: Random
    abstract override fun generateMap(isWithPlayer: Boolean): Map<Position3D, Block>
}
