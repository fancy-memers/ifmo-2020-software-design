package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Block
import org.fancy.memers.model.Empty
import org.fancy.memers.model.Floor
import org.fancy.memers.model.Player
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

class World(
    val size: Size3D,
    val actualBoard: MutableMap<Position3D, Block>
) {

    // This should be fixed at generator level
    val player: Player = actualBoard.values.filterIsInstance<Player>().single()
    private val blockChangeEventHandlers = mutableListOf<(Position3D) -> Unit>()

    fun addBlockChangeEventHandler(handler: (Position3D) -> Unit) {
        blockChangeEventHandlers.add(handler)
    }

    private fun triggerBlockChangeEvent(position: Position3D) {
        blockChangeEventHandlers.forEach { it(position) }
    }

    fun movePlayer(diff: Position3D) {
        val oldPlayerPosition = player.position
        val newPlayerPosition = oldPlayerPosition.withRelative(diff)

        val targetBlockPosition = newPlayerPosition.withZ(0)
        val targetBlock = actualBoard[targetBlockPosition] ?: return
        if (targetBlock !is Empty && targetBlock !is Floor) {
            return
        }

        actualBoard.remove(oldPlayerPosition)
        actualBoard[newPlayerPosition] = player
        player.position = newPlayerPosition

        triggerBlockChangeEvent(oldPlayerPosition)
        triggerBlockChangeEvent(newPlayerPosition)
    }

    companion object
}
