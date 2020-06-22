package org.fancy.memers.ui.main

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size3D
import org.hexworks.zircon.api.grid.TileGrid

/**
 * Класс с основными игровыми константами
 */
internal object MainScreenConfig {
    private const val BOARD_DEPTH = 5
    const val FANCY_LOG = "LOG"
    private const val SIDE_PANEL_WIDTH = 20
    private const val LOG_INFO_HEIGHT = 10


    fun boardSize(parent: TileGrid): Size3D {
        return Size3D.create(
            parent.size.width - SIDE_PANEL_WIDTH,
            parent.size.height - LOG_INFO_HEIGHT,
            BOARD_DEPTH
        )
    }

    fun logPanelSize(parent: TileGrid): Size {
        return Size.create(parent.size.width - SIDE_PANEL_WIDTH, LOG_INFO_HEIGHT)
    }

    fun infoPanelSize(parent: TileGrid): Size {
        return Size.create(SIDE_PANEL_WIDTH, parent.size.height)
    }
}
