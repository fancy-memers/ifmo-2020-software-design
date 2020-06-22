package org.fancy.memers.ui.main.board

import org.fancy.memers.model.Empty
import org.fancy.memers.model.Floor
import org.fancy.memers.model.Player
import org.fancy.memers.model.generator.BoardGenerator
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

/**
 * Основной класс модели
 * Содержит информацию о всех блоках модели и методы для её изменения
 */
class World(val size: Size3D, generator: BoardGenerator = BoardGenerator.defaultGenerator(size)) {
    val actualBoard = generator.generateMap().toMutableMap()

    // This should be fixed at generator level
    val player = actualBoard.values.single { it is Player }
    private val blockChangeEventHandlers = mutableListOf<(Position3D) -> Unit>()

    fun addBlockChangeEventHandler(handler: (Position3D) -> Unit) {
        blockChangeEventHandlers.add(handler)
    }

    private fun triggerBlockChangeEvent(position: Position3D) {
        blockChangeEventHandlers.forEach { it(position) }
    }

    /**
     * Перемещает игрока в соответствующем направлении
     * Если это невозможно (например из-за стены), перемещения не происходит
     */
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

    // Нужен для extension метода World.deserialize
    companion object
}
