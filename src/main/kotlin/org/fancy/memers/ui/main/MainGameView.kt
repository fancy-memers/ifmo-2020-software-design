package org.fancy.memers.ui.main

import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.GameModification
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.view.base.BaseView

class MainGameView(
    tileGrid: TileGrid,
    theme: ColorTheme,
    private val gameArea: GameArea
) : BaseView(tileGrid, theme) {

    private val infoPanel = InfoPanelFragment(screen)
    private val logPanel = LogPanelFragment(screen)
    private val board = BoardFragment(gameArea, screen)

    init {
        screen.handleKeyboardEvents(KeyboardEventType.KEY_RELEASED) { event, _ ->
            receive(event)
        }
    }

    private fun receive(event: KeyboardEvent): UIEventResponse {
        val playerMove: (Position3D) -> GameModification =
            { position -> GameModification.Move(gameArea.world.player, position) }
        when (event.code) {
            in KeyboardControls.MOVE_UP ->
                gameArea.apply(playerMove(Position3D.create(0, -1, 0)))
            in KeyboardControls.MOVE_DOWN ->
                gameArea.apply(playerMove(Position3D.create(0, 1, 0)))
            in KeyboardControls.MOVE_LEFT ->
                gameArea.apply(playerMove(Position3D.create(-1, 0, 0)))
            in KeyboardControls.MOVE_RIGHT ->
                gameArea.apply(playerMove(Position3D.create(1, 0, 0)))
            else -> return Pass
        }
        gameArea.apply(GameModification.Step)
        return Processed
    }

    override fun onDock() {
        screen.addFragment(board)
        screen.addFragment(infoPanel)
        screen.addFragment(logPanel)
    }
}
