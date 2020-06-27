package org.fancy.memers.model

import org.fancy.memers.model.drawable.Floor
import org.fancy.memers.model.drawable.Player
import org.fancy.memers.model.drawable.Wall
import org.fancy.memers.model.generator.BoardGenerator
import org.hexworks.zircon.api.data.Size3D
import org.junit.jupiter.api.Test

internal class RandomBoardGeneratorTest {

    @Test
    fun createRandomMap() {
        doCreateRandomMapTest(20, 40, 5)
        doCreateRandomMapTest(40, 20, 6)
    }

    private fun doCreateRandomMapTest(xLength: Int, yLength: Int, zLength: Int) {
        val size = Size3D.create(xLength, yLength, zLength)
        val generator = BoardGenerator.defaultGenerator(size)
        val map = generator.generateMap()

        val playerPosition = map.entries.singleOrNull { it.value is Player }?.key
            ?: error("Expected single player")

        check(map[playerPosition.withZ(0)] is Floor) { "Expected player to be on empty block" }

        val numberBlocks = xLength * yLength
        val numberWalls = map.values.count { it is Wall }
        val numberFloors = map.values.count { it is Floor }
        check(numberWalls >= 2) { "Expected enough number of walls" }
        check(numberFloors >= numberBlocks / 10) { "Expected enough number of empty blocks" }
    }
}
