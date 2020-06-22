package org.fancy.memers.model.generator

import org.fancy.memers.model.*
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * Генерирует случайную карту заданного размера
 * Используется алгоритм "Cellular Automata": http://kidojo.com/cellauto/
 * В качестве изначальной карты для алгоритма использует карту сгенерированную `initialGenerator`
 */
class CellularAutomataBoardGenerator(
    private val boardSize: Size3D,
    private val iterationNumber: Int = 10,
    private val initialGenerator: BoardGenerator,
    seed: Int? = null
) : RandomBoardGenerator(seed) {

    private fun randomPlayerPosition(board: Map<Position3D, Block>): Position3D {
        val floorPositions = board.filter { it.value is Floor }.keys
        check(floorPositions.isNotEmpty())
        return floorPositions.random(random).withZ(boardSize.zLength - 1)
    }

    override fun generateMap(withPlayer: Boolean): Map<Position3D, Block> {
        var gameBoard = initialGenerator.generateMap(false).toMutableMap()
        repeat(iterationNumber) {
            gameBoard = iterateMap(gameBoard)
        }
        if (withPlayer) {
            val playerPosition = randomPlayerPosition(gameBoard)
            gameBoard[playerPosition] = Player(playerPosition)
        }
        return gameBoard
    }


    private fun iterateMap(board: MutableMap<Position3D, Block>): MutableMap<Position3D, Block> {
        for (position in boardSize.fetchFloorPositions()) {
            var emptyCount = 0
            var wallsCount = 0
            for (neighbor in position.fetchNeighbours()) {
                val block = board[neighbor] ?: continue
                if (block is Floor)
                    emptyCount++
                else
                    wallsCount++
            }
            board[position] = if (emptyCount >= wallsCount) Floor(position) else Wall(position)
        }
        return board
    }
}
