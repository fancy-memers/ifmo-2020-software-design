package org.fancy.memers.model.generator

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.create
import kotlin.random.Random
import kotlin.random.asJavaRandom

abstract class RandomBoardGenerator(seed: Int? = null) : BoardGenerator {
    protected val random: Random = seed?.let { Random(it) } ?: Random

    protected val faker: Faker by lazy {
        val boardRandom = random
        Faker(
            FakerConfig.builder().create {
                random = boardRandom.asJavaRandom()
            }
        )
    }
}
