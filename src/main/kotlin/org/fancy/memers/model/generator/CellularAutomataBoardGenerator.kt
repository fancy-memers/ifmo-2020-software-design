package org.fancy.memers.model.generator

import org.fancy.memers.model.Block
import org.fancy.memers.model.Empty
import org.fancy.memers.model.Player
import org.fancy.memers.model.Wall
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.random.Random

class CellularAutomataBoardGenerator(private val boardSize: Size3D) : BoardGenerator {

    private var board: MutableMap<Position3D, Block> = mutableMapOf()
    private val random: Random = Random

    fun get(): MutableMap<Position3D, Block> {
        return board
    }

    private fun forAllPositions(fn: (Position3D) -> Unit) {
        boardSize.fetchPositions().forEach(fn)
    }

    private fun MutableMap<Position3D, Block>.whenPresent(pos: Position3D, fn: (Block) -> Unit) {
        this[pos]?.let(fn)
    }

    fun Position3D.sameLevelNeighborsShuffled(): List<Position3D> {
        return (-1..1).flatMap { x ->
            (-1..1).map { y ->
                withRelativeX(x).withRelativeY(y)
            }
        }.minus(this).shuffled()
    }

    private fun randomizeTiles() {
        forAllPositions { pos ->
            board[pos] = if (Math.random() < 0.5) {
                Empty(pos)
            } else Wall(pos)
        }
    }

    private fun smooth(iterations: Int) {
        val newBlocks = mutableMapOf<Position3D, Block>()
        repeat(iterations) {
            forAllPositions { pos ->
                val (x, y, z) = pos
                var empty = 0
                var walls = 0
                pos.sameLevelNeighborsShuffled().plus(pos).forEach { neighbor ->
                    board.whenPresent(neighbor) { block ->
                        if (block is Empty) {
                            empty++
                        } else walls++
                    }
                }
                newBlocks[Position3D.create(x, y, z)] = if (empty >= walls) Empty(pos) else Wall(pos)
            }
            board = newBlocks
        }
    }

    private fun randomPlayerPosition() {
        var pos = Position3D.create(
            random.nextInt(boardSize.xLength),
            random.nextInt(boardSize.yLength),
            boardSize.zLength - 1
        )

        while (board[pos] !is Empty) {
            pos = Position3D.create(
                random.nextInt(boardSize.xLength),
                random.nextInt(boardSize.yLength),
                boardSize.zLength - 1
            )
        }

        board[pos] = Player(pos)
    }

    override fun generateMap(): Map<Position3D, Block> {
        randomizeTiles()
        smooth(10)
        randomPlayerPosition()
        return board
    }
}