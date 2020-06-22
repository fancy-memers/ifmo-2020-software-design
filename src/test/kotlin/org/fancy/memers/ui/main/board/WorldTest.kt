package org.fancy.memers.ui.main.board

import org.fancy.memers.model.drawable.Floor
import org.fancy.memers.model.fetchNeighbours
import org.fancy.memers.model.generator.BoardGenerator
import org.hexworks.zircon.api.data.Size3D
import org.junit.jupiter.api.Test

class WorldTest {

    @Test
    fun testMovePlayer() {
        val world = createGameArea()
        repeat(100) {
            val positionOld = world.player.position
            check(world[positionOld] == world.player)

            val diff = positionOld.fetchNeighbours().random()
            val positionNew = positionOld.withRelative(diff)
            world.move(world.player, positionNew)
            val positionNewActual = if (world[positionNew.withRelativeZ(0)] is Floor) {
                positionOld.withRelative(diff)
            } else {
                positionOld
            }
            check(world[positionNewActual] == world.player)
        }
    }

    private fun createGameArea(xLength: Int = 10, yLength: Int = 20, zLength: Int = 5): World {
        val size = Size3D.create(xLength, yLength, zLength)
        return World(size, BoardGenerator.defaultGenerator(size).generateMap().toMutableMap())
    }
}
