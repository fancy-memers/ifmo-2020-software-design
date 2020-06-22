package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Floor
import org.fancy.memers.model.fetchNeighbours
import org.hexworks.zircon.api.data.Size3D
import org.junit.jupiter.api.Test

class WorldTest {

    @Test
    fun testMovePlayer() {
        val world = createGameArea()
        repeat(100) {
            val positionOld = world.player.position
            check(world.actualBoard[positionOld] == world.player)

            val diff = positionOld.fetchNeighbours().random()
            world.movePlayer(diff)
            val positionNew = positionOld.withRelative(diff)
            val positionNewActual = if (world.actualBoard[positionNew.withRelativeZ(0)] is Floor) {
                positionOld.withRelative(diff)
            } else {
                positionOld
            }
            check(world.actualBoard[positionNewActual] == world.player)
        }
    }

    private fun createGameArea(xLength: Int = 10, yLength: Int = 20, zLength: Int = 5): World {
        val size = Size3D.create(xLength, yLength, zLength)
        return World(size)
    }
}
