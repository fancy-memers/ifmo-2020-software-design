package org.fancy.memers.model.generator

import org.fancy.memers.model.Block
import org.fancy.memers.model.Empty
import org.fancy.memers.model.Wall
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D
import kotlin.math.abs
import kotlin.random.Random

class CellularAutomataBoardGenerator(
    private val boardSize: Size3D,
    private val fillRate: Double = 0.2,
    seed: Int? = null
) : BoardGenerator {
    private val random: Random = seed?.let { Random(it) } ?: Random
//    private val params = GenParams
//
//    internal class GenParams(private val r1_cutoff: Int,
//                             private val r2_cutoff: Int,
//    ) {
//        var r1_cutoff:Int = 0
//        var r2_cutoff:Int = 0
//        var reps: Int = 0
//    }


    override fun generateMap(): Map<Position3D, Block> {
        val pairs = boardSize.fetchPositions()
            .map { Position3D.create(it.x, it.y, 0) }
            .map { it to Wall(it) }
            .toList()

        val grid = pairs.toMap().toMutableMap()
        val grid2 = pairs.toMap().toMutableMap()

        val size_x = boardSize.xLength
        val size_y = boardSize.yLength


        for (yi in 1 until size_y) {
            for (xi in 1 until size_x) {
                var adjcount_r1 = 0
                var adjcount_r2 = 0

                for (ii in -1..2){
                    for (jj in -1..2) {
                        val pos = Position3D.create(yi+ii, xi+jj, 0)
                        if(grid.get(pos) !is  Wall) {
                            adjcount_r1++;
                        }
                    }
                }

                for (ii in yi-2..yi+2) {
                    for (jj in xi-2..xi+2) {
                        val pos = Position3D.create(yi+ii, xi+jj, 0)

                        if(abs(ii-yi)==2 && abs(jj-xi)==2)
                            continue;
                        if(ii<0 || jj<0 || ii>=size_y || jj>=size_x)
                            continue;
                        if(grid.get(pos) !is Empty)
                            adjcount_r2++;
                    }
                }

                if(adjcount_r1 >= params->r1_cutoff || adjcount_r2 <= params->r2_cutoff) {
                    val pos = Position3D.create(yi, xi, 0)
                    grid2.put(pos, Wall(pos));
                } else {
                    val pos = Position3D.create(yi, xi, 0)
                    grid2.put(pos, Empty(pos));
                }

            }

        }


        TODO("Not yet implemented")
    }

    private fun randomPlayerPosition(): Position3D {
        return Position3D.create(
            random.nextInt(boardSize.xLength),
            random.nextInt(boardSize.yLength),
            boardSize.zLength - 1
        )
    }
}