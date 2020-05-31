package org.fancy.memers.ui.start

import org.fancy.memers.deserialize
import org.fancy.memers.model.generator.BoardGenerator
import org.fancy.memers.utils.filterKeyboardEvent
import org.fancy.memers.ui.main.MainGameView
import org.fancy.memers.ui.main.MainScreenConfig
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.World
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.view.base.BaseView
import java.io.File


class StartMenuView(private val tileGrid: TileGrid, theme: ColorTheme) : BaseView(tileGrid, theme) {
    private val startGenerated = StartScreenConfig.BASE_BUTTON_BUILDER
        .withText(StartScreenConfig.FANCY_START_GEN)
        .withAlignmentWithin(screen, ComponentAlignment.CENTER)
        .build()
    private val startFromFile = StartScreenConfig.BASE_BUTTON_BUILDER
        .withAlignmentAround(startGenerated, ComponentAlignment.BOTTOM_CENTER)
        .withText(StartScreenConfig.FANCY_START_FILE)
        .build()

    private val filePath = Components.textArea()
        .withDecorations(shadow())
        .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, StartScreenConfig.FANCY_FILEPATH))
        .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
        .withSize(30, 3)
        .withColorTheme(theme)
        .build()


    private val header = Components.textBox(StartScreenConfig.FANCY_TITLE.length)
        .withAlignmentAround(startGenerated, ComponentAlignment.TOP_CENTER)
        .addHeader(StartScreenConfig.FANCY_TITLE)
        .addNewLine()
        .build()

    override fun onDock() {
        startGenerated.processKeyboardEvents(
            KeyboardEventType.KEY_PRESSED,
            filterKeyboardEvent(KeyCode.ENTER) { _, _ -> startGeneratedWorld() }
        )
        startFromFile.processKeyboardEvents(
            KeyboardEventType.KEY_PRESSED,
            filterKeyboardEvent(KeyCode.ENTER) { _, _ ->
                startWithFile(File(filePath.text))
            }
        )
        startGenerated.processComponentEvents(ComponentEventType.ACTIVATED) { this.startGeneratedWorld() }
        startFromFile.processComponentEvents(ComponentEventType.ACTIVATED) { this.startWithFile(File(filePath.text)) }


        screen.addComponent(header)
        screen.addComponents(startGenerated, startFromFile)
        screen.addComponent(filePath)
        startGenerated.requestFocus()
    }

    private fun startWithFile(file: File) {
        check(file.exists()) { "File $file does not exist" }
        val data = file.readText()
        val world = World.deserialize(data)
        start(GameArea(world))
    }

    private fun startGeneratedWorld() {
        val size = MainScreenConfig.boardSize(screen)
        val board = BoardGenerator.defaultGenerator(size).generateMap()
        start(GameArea(World(size, board.toMutableMap())))
    }

    private fun start(gameArea: GameArea) {
        replaceWith(MainGameView(tileGrid, theme, gameArea))
        screen.close()
    }
}
