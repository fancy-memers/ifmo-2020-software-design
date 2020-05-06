package org.fancy.memers

import org.fancy.memers.ui.main.board.World
import org.hexworks.zircon.api.data.Size3D
import org.junit.jupiter.api.Test

internal class GameAreaSerializationTest {
    @Test
    fun main() {
        val size = Size3D.create(20, 10, 5)
        val before = World(size)

        val after = World.deserialize(before.serialize())
        check(before.size == after.size)
        check(before.actualBoard == after.actualBoard)
    }
}
