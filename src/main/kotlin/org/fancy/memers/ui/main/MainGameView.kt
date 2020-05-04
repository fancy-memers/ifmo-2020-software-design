package org.fancy.memers.ui.main

import org.fancy.memers.ui.main.board.GameArea
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.view.base.BaseView

class MainGameView(
    tileGrid: TileGrid,
    theme: ColorTheme,
    private val gameArea: GameArea
) : BaseView(tileGrid, theme) {

    private val infoPanel = InfoPanelFragment(screen)
    private val logPanel = LogPanelFragment(screen)
    private val board = BoardFragment(gameArea, screen)

    override fun onDock() {
        screen.addFragment(board)
        screen.addFragment(infoPanel)
        screen.addFragment(logPanel)
    }
}
