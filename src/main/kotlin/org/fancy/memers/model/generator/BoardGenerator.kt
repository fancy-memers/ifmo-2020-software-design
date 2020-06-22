package org.fancy.memers.model.generator

import org.fancy.memers.model.Block
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * Базовый класс для генерации карты
 */
interface BoardGenerator {
    /**
     * Генерирует случайную карту
     */
    fun generateMap(withPlayer: Boolean = true): Map<Position3D, Block>

    companion object {
        fun defaultGenerator(size: Size3D): BoardGenerator =
            CellularAutomataBoardGenerator(size, 8, UniformBoardGenerator(size, 0.5))
    }
}
