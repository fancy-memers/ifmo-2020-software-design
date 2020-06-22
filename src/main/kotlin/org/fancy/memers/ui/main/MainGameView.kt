package org.fancy.memers.ui.main

import org.fancy.memers.model.buffs.ConfusionEffect
import org.fancy.memers.model.generator.BoardGenerator
import org.fancy.memers.model.generator.WorldLevel
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.GameModification
import org.fancy.memers.ui.main.escape.EscapeMenuView
import org.fancy.memers.utils.logger.LogEvent
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.subscribeTo
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.*
import org.hexworks.zircon.api.view.base.BaseView
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.api.extensions.*
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

/**
 * Основной игровой экран
 * Содержит игровое поле, панель с игровыми событиями и панель с игровой информацией
 * Также отвечает за (низкоуровневую) обработку user input
 */
class MainGameView(
    private val tileGrid: TileGrid,
    theme: ColorTheme,
    private val gameArea: GameArea
) : BaseView(tileGrid, theme) {

    private val infoPanel = InfoPanelFragment(screen)
    private val logPanel = LogPanelFragment(screen)
    private val board = BoardFragment(gameArea, screen)

    init {
        subscribeToLogEvents()
        WorldUpdate.publish(gameArea.world)
        screen.handleKeyboardEvents(KeyboardEventType.KEY_RELEASED) { event, _ ->
            receive(event)
        }
    }

    private fun receive(event: KeyboardEvent): UIEventResponse {
        val playerMove: (Position3D) -> GameModification =
            { position ->
                if (gameArea.world.player.hasEffect<ConfusionEffect>()) {
                    GameModification.Identity
                } else {
                    GameModification.Move(gameArea.world.player, position)
                }
            }
        when (event.code) {
            in KeyboardControls.MOVE_UP ->
                gameArea.apply(playerMove(Position3D.create(0, -1, 0)))
            in KeyboardControls.MOVE_DOWN ->
                gameArea.apply(playerMove(Position3D.create(0, 1, 0)))
            in KeyboardControls.MOVE_LEFT ->
                gameArea.apply(playerMove(Position3D.create(-1, 0, 0)))
            in KeyboardControls.MOVE_RIGHT ->
                gameArea.apply(playerMove(Position3D.create(1, 0, 0)))
            in KeyboardControls.SKIP_TURN -> GameModification.Identity
            in KeyboardControls.ESCAPE_MENU -> {
                replaceWith(EscapeMenuView(tileGrid, theme, gameArea))
                screen.close()
                Processed
            }
            in KeyboardControls.INVENTORY_MENU -> showInventory()
            else -> return Pass
        }
        gameArea.apply(GameModification.Step)
        return Processed
    }

    private fun subscribeToLogEvents() {
        Zircon.eventBus.subscribeTo<LogEvent> {
            logPanel.root.addParagraph(it.content)
            KeepSubscription
        }
    }

    private fun showInventory() {
        val panel = Components.panel()
            .withSize(DIALOG_SIZE)
            .withDecorations()
            .withDecorations(box(BoxType.SINGLE, "Inventory"))
            .withDecorations(shadow())
            .build()
        val playerPosition = gameArea.world.player.position
        val itemPosition = playerPosition.withZ(BoardGenerator.boardLevel(gameArea.world.boardSize, WorldLevel.ITEM))
        val canDropItems = gameArea.world[itemPosition] == null
        val fragment = InventoryFragment(gameArea.world.player.inventory, canDropItems, DIALOG_SIZE.width - 3) {
            gameArea.apply(GameModification.DropItem(gameArea.world.player, it))
        }
        panel.addFragment(fragment)
        val modal = ModalBuilder.newBuilder<EmptyModalResult>()
            .withParentSize(screen.size)
            .withComponent(panel)
            .build()

        panel.addComponent(Components.button()
            .withText("Close")
            .withAlignmentWithin(panel, ComponentAlignment.BOTTOM_LEFT)
            .build().apply {
                handleComponentEvents(ComponentEventType.ACTIVATED) {
                    modal.close(EmptyModalResult)
                    Processed
                }
            })
        modal.theme = theme
        screen.openModal(modal)

    }

    override fun onDock() {
        screen.addFragment(board)
        screen.addFragment(infoPanel)
        screen.addFragment(logPanel)
    }

    companion object {
        val DIALOG_SIZE = Size.create(35, 20)
    }
}
