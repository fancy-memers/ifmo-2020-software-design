package org.fancy.memers.ui.main.board

import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

/**
 * Класс требуемый фреймворком (zircon), описывает каким образом игровой объект должен отображаться на экране
 */
class GameBlock(emptyTile: Tile) : BaseBlock<Tile>(emptyTile, persistentMapOf())
