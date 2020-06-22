package org.fancy.memers.model

import org.fancy.memers.model.generator.BoardGenerator
import org.hexworks.zircon.api.data.Size3D
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RandomBoardGeneratorTest {

    @Test
    fun createRandomMap() {
        doCreateRandomMapTest(20, 10, 5)
        doCreateRandomMapTest(10, 20, 6)
        doCreateRandomMapTest(20, 20, 3)
    }

    private fun doCreateRandomMapTest(xLength: Int, yLength: Int, zLength: Int) {
        val size = Size3D.create(xLength, yLength, zLength)
        val generator = BoardGenerator.defaultGenerator(size)
        val map = generator.generateMap()

        val player = map.values.singleOrNull { it is Player }
            ?: error("Expected single player")

        val playerPosition = player.position.withZ(0)
        check(map[playerPosition] is Floor) { "Expected player to be on empty block" }

        val numberBlocks = xLength * yLength
        val numberWalls = map.values.count { it is Wall }
        val numberFloors = map.values.count { it is Floor }
        check(numberWalls >= 4) { "Expected enough number of walls" }
        check(numberFloors >= numberBlocks / 10) { "Expected enough number of empty blocks" }
        assertEquals(numberWalls + numberFloors, numberBlocks)
    }
}
