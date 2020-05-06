package org.fancy.memers.model.generator

import kotlin.random.Random

abstract class RandomBoardGenerator(seed: Int? = null) : BoardGenerator {
    protected val random: Random = seed?.let { Random(it) } ?: Random
}
