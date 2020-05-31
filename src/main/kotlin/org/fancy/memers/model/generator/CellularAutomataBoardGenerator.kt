package org.fancy.memers.model.generator

import org.fancy.memers.model.*
import org.fancy.memers.model.ai.AggressiveEnemyBehaviour
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class CellularAutomataBoardGenerator(
    private val boardSize: Size3D,
    private val iterationNumber: Int = 10,
    private val initialGenerator: BoardGenerator,
    seed: Int? = null
) : RandomBoardGenerator(seed) {

    private fun randomEmptyPosition(board: Map<Position3D, Block>): Position3D {
        val floorPositions = board.filter { it.value is Floor }.keys
        check(floorPositions.isNotEmpty())
        return floorPositions.random(random).withZ(boardSize.zLength - 1)
    }

    override fun generateMap(withPlayer: Boolean, numberEnemies: Int): Map<Position3D, Block> {
        var gameBoard = initialGenerator.generateMap(false).toMutableMap()
        repeat(iterationNumber) {
            gameBoard = iterateMap(gameBoard)
        }
        if (withPlayer) {
            val playerPosition = randomEmptyPosition(gameBoard)
            gameBoard[playerPosition] = Player(playerPosition)
        }
        repeat(numberEnemies) {
            val enemyPosition = randomEmptyPosition(gameBoard)

            gameBoard[enemyPosition] = Enemy(faker.name.firstName(), AggressiveEnemyBehaviour(), enemyPosition)
        }
        addItems(gameBoard)
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

    private fun addItems(board: MutableMap<Position3D, Block>) {
        val itemProbability = 0.01
        for (block in board.values) {
            if (block is Floor && random.nextFloat() < itemProbability) {
                block.item = generateRandomItem()
            }
        }
    }

    private fun generateRandomItem(): Item {
        val attackBonus = random.nextInt(-ITEM_MAX_ATTACK_BONUS, ITEM_MAX_ATTACK_BONUS)
        val defenceBonus = random.nextInt(ITEM_MAX_DEFENCE_BONUS)
        val symbol = ITEMS_SYMBOLS.toCharArray().random(random)
        return Item(attackBonus, defenceBonus, symbol)
    }

    companion object {
        const val ITEMS_SYMBOLS: String = "!@#$%^&*"
        const val ITEM_MAX_ATTACK_BONUS: Int = 10
        const val ITEM_MAX_DEFENCE_BONUS: Int = 10
    }
}
