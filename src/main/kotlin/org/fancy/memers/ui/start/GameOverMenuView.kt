package org.fancy.memers.ui.start

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.grid.TileGrid

class GameOverMenuView(tileGrid: TileGrid, theme: ColorTheme) : StartMenuView(tileGrid, theme) {
    override val header = Components.textBox(StartScreenConfig.FANCY_TITLE.length)
        .withAlignmentAround(startGenerated, ComponentAlignment.TOP_CENTER)
        .addHeader("Game Over")
        .addNewLine()
        .build()
}
