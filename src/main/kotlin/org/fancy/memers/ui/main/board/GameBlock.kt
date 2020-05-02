package org.fancy.memers.ui.main.board

import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.base.BaseBlock

class GameBlock(emptyTile: Tile) : BaseBlock<Tile>(emptyTile, persistentMapOf())