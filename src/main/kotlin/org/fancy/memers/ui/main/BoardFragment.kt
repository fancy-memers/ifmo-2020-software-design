package org.fancy.memers.ui.main

import org.fancy.memers.ui.main.board.GameArea
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid

/**
 * Панель с игровым миром
 */
class BoardFragment(gameArea: GameArea, parent: TileGrid) : Fragment {
    override val root: Component = Components
        .gameComponent<Tile, Block<Tile>>()
        .withGameArea(gameArea)
        .withSize(gameArea.visibleSize.to2DSize())
        .withAlignmentWithin(parent, ComponentAlignment.TOP_LEFT)
        .build()
}
