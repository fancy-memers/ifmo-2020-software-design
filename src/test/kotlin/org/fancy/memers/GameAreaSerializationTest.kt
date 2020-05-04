package org.fancy.memers

import org.fancy.memers.ui.main.board.GameArea
import org.hexworks.zircon.api.data.Size3D
import org.junit.jupiter.api.Test

internal class GameAreaSerializationTest {

    @Test
    fun main() {
        val size = Size3D.create(20, 10, 5)
        val before = GameArea(size)

        val after = deserialize(serialize(before))
        check(before.visibleSize == after.visibleSize)
        check(before.getBoardMap() == after.getBoardMap())
    }
}
