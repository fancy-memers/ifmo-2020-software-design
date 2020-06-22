package org.fancy.memers.model.generator

import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.create
import org.fancy.memers.model.drawable.*
import org.fancy.memers.model.generator.BoardGenerator.Companion.boardLevel
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random
import kotlin.random.asJavaRandom

/**
 * Базовый класс для генерации случайной карты
 */
abstract class RandomBoardGenerator(protected val boardSize: Size3D, seed: Int? = null) : BoardGenerator {
    /**
     * Источник случайности для генерации
     */
    protected val random: Random = seed?.let { Random(it) } ?: Random

    protected val faker: Faker by lazy {
        val boardRandom = random
        Faker(
            FakerConfig.builder().create {
                random = boardRandom.asJavaRandom()
            }
        )
    }

    protected fun randomCreaturePosition(board: Map<Position3D, Block>): Position3D {
        val floorPositions = board.filter { it.value is Floor }.keys
        check(floorPositions.isNotEmpty())
        return floorPositions.random(random).withZ(boardLevel(boardSize, WorldLevel.CREATURE))
    }

    private fun randomFloorPosition(board: Map<Position3D, Block>): Position3D {
        val floorPositions = board.filter { it.value is Floor }.keys
        check(floorPositions.isNotEmpty())
        return floorPositions.random(random).withZ(boardLevel(boardSize, WorldLevel.ITEM))
    }

    private fun generateRandomItem(): Item {
        return listOf(Knife.INSTANCE, Mace.INSTANCE, PlateMail.INSTANCE, Boots.INSTANCE).random()
    }

    protected fun addItems(itemCount: Int, board: MutableMap<Position3D, Block>) {
        repeat(itemCount) {
            val item = generateRandomItem()
            val itemPosition = randomFloorPosition(board)
            board[itemPosition] = item
        }
    }
}
