package org.fancy.memers.model.generator

import org.fancy.memers.model.Block
import org.hexworks.zircon.api.data.Position3D
import kotlin.random.Random

/**
 * Базовый класс для генерации случайной карты
 */
abstract class RandomBoardGenerator(seed: Int? = null) : BoardGenerator {
    /**
     * Источник случайности для генерации
     */
    protected val random: Random = seed?.let { Random(it) } ?: Random
    abstract override fun generateMap(withPlayer: Boolean): Map<Position3D, Block>
}
