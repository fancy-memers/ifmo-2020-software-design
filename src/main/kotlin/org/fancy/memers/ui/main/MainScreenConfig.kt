package org.fancy.memers.ui.main

import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid


internal object MainScreenConfig {
    const val FANCY_LOG = "LOG"
    private const val SIDE_PANEL_WIDTH = 20
    private const val LOG_INFO_HEIGHT = 10


    fun boardSize(parent: TileGrid): Size {
        return Size.create(
            parent.size.width - SIDE_PANEL_WIDTH,
            parent.size.height - LOG_INFO_HEIGHT
        )
    }

    fun logPanelSize(parent: TileGrid): Size {
        return Size.create(parent.size.width - SIDE_PANEL_WIDTH, LOG_INFO_HEIGHT)
    }

    fun infoPanelSize(parent: TileGrid): Size {
        return Size.create(SIDE_PANEL_WIDTH, parent.size.height)
    }
}