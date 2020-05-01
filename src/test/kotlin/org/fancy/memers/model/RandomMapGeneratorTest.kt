package org.fancy.memers.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

internal class RandomMapGeneratorTest {

    @Test
    fun createRandomMap() {
        val height = 40
        val width = 40
        val map = RandomMapGenerator(height, width).createRandomMap()
        assertEquals(height, map.height)
        assertEquals(width, map.width)
    }
}
