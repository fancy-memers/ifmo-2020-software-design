package org.fancy.memers.ui.main

import org.fancy.memers.ui.main.board.GameArea
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.view.base.BaseView
import java.util.logging.Logger

class MainGameView(
    tileGrid: TileGrid,
    theme: ColorTheme,
    private val gameArea: GameArea
) : BaseView(tileGrid, theme) {

    private val infoPanel = InfoPanelFragment(screen)
    private val logPanel = LogPanelFragment(screen)
    private val board = BoardFragment(gameArea, screen)

    init {
        screen.handleKeyboardEvents(KeyboardEventType.KEY_RELEASED) {
                event: KeyboardEvent, _: UIEventPhase ->
            when (event.code) {
                in KeyboardControls.MOVE_UP -> {
                    gameArea.world.movePlayer(Position3D.create(0, -1, 0))
                    Processed
                }
                in KeyboardControls.MOVE_DOWN -> {
                    gameArea.world.movePlayer(Position3D.create(0, 1, 0))
                    Processed
                }
                in KeyboardControls.MOVE_LEFT -> {
                    gameArea.world.movePlayer(Position3D.create(-1, 0, 0))
                    Processed
                }
                in KeyboardControls.MOVE_RIGHT -> {
                    gameArea.world.movePlayer(Position3D.create(1, 0, 0))
                    Processed
                }
                else -> Pass
            }
        }
    }

    override fun onDock() {
        screen.addFragment(board)
        screen.addFragment(infoPanel)
        screen.addFragment(logPanel)
    }
}
