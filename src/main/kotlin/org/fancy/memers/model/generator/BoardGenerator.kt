package org.fancy.memers.model.generator

import org.fancy.memers.model.drawable.Block
import org.fancy.memers.model.drawable.Item
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

interface BoardGenerator {
    fun generateMap(withPlayer: Boolean = true, withItems: Boolean = true, numberEnemies: Int = 6): Map<Position3D, Block>


    companion object {
        fun defaultGenerator(size: Size3D): BoardGenerator =
            CellularAutomataBoardGenerator(size, 8, UniformBoardGenerator(size, 0.45))

        fun boardLevel(boardSize: Size3D, level: WorldLevel): Int {
            return when (level) {
                WorldLevel.FLOOR -> 0
                WorldLevel.ITEM -> 1
                WorldLevel.CREATURE -> boardSize.zLength - 1
            }
        }
    }
}
