package org.fancy.memers

import org.fancy.memers.model.generator.BoardGenerator
import org.fancy.memers.ui.main.board.World
import org.hexworks.zircon.api.data.Size3D
import org.junit.jupiter.api.Test

internal class GameAreaSerializationTest {
    @Test
    fun main() {
        val size = Size3D.create(20, 10, 5)
        val board = BoardGenerator.defaultGenerator(size).generateMap()
        val worldBefore = World(size, board.toMutableMap())

        val worldAfter = World.deserialize(worldBefore.serialize())
        check(worldBefore.boardSize == worldAfter.boardSize)
        // for debug
        for (position in worldBefore.board.keys) {
            val blockBefore = worldBefore.board[position]
            val blockAfter = worldAfter.board[position]
            check(blockBefore == blockAfter)
        }
        check(worldBefore.board == worldAfter.board)
    }
}
