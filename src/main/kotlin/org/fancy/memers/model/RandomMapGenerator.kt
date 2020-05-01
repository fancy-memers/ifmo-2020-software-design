package org.fancy.memers.model

import kotlin.random.Random

class RandomMapGenerator(
    private val height: Int,
    private val width: Int,
    private val fillRate: Double = 0.2,
    seed: Int? = null
) {

    private val random: Random = if (seed == null) Random else Random(seed)

    fun createRandomMap(): Map {
        check(0 < fillRate && fillRate < 1)
        check(height > 2 && width > 2)

        val map = createMap()
        val player = createPlayer(map)
        map.addEntity(player)
        return map
    }

    private fun createMap(): Map {
        val map = Array(height) { i -> Array(width) { j -> createRandomTile(i, j) } }
        return Map(map)
    }

    private fun createRandomTile(i: Int, j: Int): Tile {
        if (i == 0 || j == 0 || i + 1 == height || j + 1 == width) {
            return WallTile()
        }
        return if (random.nextDouble() < fillRate) {
            WallTile()
        } else {
            EmptyTile()
        }
    }

    private fun createPlayer(map: Map): Player {
        while (true) {
            val (i, j) = getRandomPosition()
            if (map.isEmpty(i, j)) {
                return Player(i, j)
            }
        }
    }

    private fun getRandomPosition(): Pair<Int, Int> {
        val i = random.nextInt(height)
        val j = random.nextInt(width)
        return i to j
    }
}
