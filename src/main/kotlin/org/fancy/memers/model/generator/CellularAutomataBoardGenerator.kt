package org.fancy.memers.model.generator

import org.fancy.memers.model.*
import org.fancy.memers.model.ai.AggressiveEnemyBehaviour
import org.fancy.memers.model.ai.PassiveEnemyBehaviour
import org.fancy.memers.model.drawable.*
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * Генерирует случайную карту заданного размера
 * Используется алгоритм "Cellular Automata": http://kidojo.com/cellauto/
 * В качестве изначальной карты для алгоритма использует карту сгенерированную `initialGenerator`
 */
class CellularAutomataBoardGenerator(
    boardSize: Size3D,
    private val iterationNumber: Int = 10,
    private val initialGenerator: BoardGenerator,
    private val itemCount: Int = 5,
    seed: Int? = null
) : RandomBoardGenerator(boardSize, seed) {

    override fun generateMap(withPlayer: Boolean, withItems: Boolean, numberEnemies: Int): Map<Position3D, Block> {
        var gameBoard = initialGenerator.generateMap(false, false).toMutableMap()
        repeat(iterationNumber) {
            gameBoard = iterateMap(gameBoard)
        }
        if (withPlayer) {
            val playerPosition = randomCreaturePosition(gameBoard)
            gameBoard[playerPosition] = Player(playerPosition)
        }
        repeat(numberEnemies) {
            val enemyPosition = randomCreaturePosition(gameBoard)

            gameBoard[enemyPosition] = Enemy(
                faker.name.firstName(),
                AggressiveEnemyBehaviour(),
                enemyPosition
            )
        }
        if (withItems)
            addItems(itemCount, gameBoard)
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
            board[position] = if (emptyCount >= wallsCount) Floor() else Wall()
        }
        return board
    }
}
