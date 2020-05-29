package org.fancy.memers.ui.main.escape

import org.fancy.memers.deserialize
import org.fancy.memers.serialize
import org.fancy.memers.ui.filterKeyboardEvent
import org.fancy.memers.ui.main.MainGameView
import org.fancy.memers.ui.main.board.GameArea
import org.fancy.memers.ui.main.board.World
import org.fancy.memers.ui.start.StartScreenConfig
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


class EscapeMenuView(
    private val tileGrid: TileGrid
    , theme: ColorTheme
    , private val gameArea: GameArea
) : BaseView(tileGrid, theme) {

    private val returnButton = EscapeScreenConfig.BASE_BUTTON_BUILDER
        .withText(EscapeScreenConfig.RETURN)
        .withAlignmentWithin(screen, ComponentAlignment.CENTER)
        .build()


    private val saveToFile = EscapeScreenConfig.BASE_BUTTON_BUILDER
        .withAlignmentAround(returnButton, ComponentAlignment.BOTTOM_CENTER)
        .withText(EscapeScreenConfig.SAVE_FILE)
        .build()

    private val startFromFile = StartScreenConfig.BASE_BUTTON_BUILDER
        .withAlignmentAround(saveToFile, ComponentAlignment.BOTTOM_CENTER)
        .withText(EscapeScreenConfig.START_FILE)
        .build()

    private val filePath = Components.textArea()
        .withDecorations(shadow())
        .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, EscapeScreenConfig.FANCY_FILEPATH))
        .withAlignmentWithin(screen, ComponentAlignment.BOTTOM_RIGHT)
        .withSize(30, 3)
        .withColorTheme(theme)
        .build()

    private val header = Components.textBox(EscapeScreenConfig.TITLE.length)
        .withAlignmentAround(returnButton, ComponentAlignment.TOP_CENTER)
        .addHeader(EscapeScreenConfig.TITLE)
        .addNewLine()
        .build()

    override fun onDock() {
        returnButton.processKeyboardEvents(
            KeyboardEventType.KEY_PRESSED,
            filterKeyboardEvent(KeyCode.ENTER) { _, _ -> returnToGame() }
        )

        saveToFile.processKeyboardEvents(
            KeyboardEventType.KEY_PRESSED,
            filterKeyboardEvent(KeyCode.ENTER) { _, _ ->
                saveToFile(
                    File(
                        filePath.text
                    )
                )
            }
        )

        startFromFile.processKeyboardEvents(
            KeyboardEventType.KEY_PRESSED,
            filterKeyboardEvent(KeyCode.ENTER) { _, _ ->
                startWithFile(
                    File(
                        filePath.text
                    )
                )
            }
        )

        returnButton.processComponentEvents(ComponentEventType.ACTIVATED) { this.returnToGame() }
        saveToFile.processComponentEvents(ComponentEventType.ACTIVATED) { this.saveToFile(File(filePath.text)) }
        startFromFile.processComponentEvents(ComponentEventType.ACTIVATED) { this.startWithFile(File(filePath.text)) }

        screen.addComponent(header)
        screen.addComponents(returnButton, saveToFile, startFromFile)
        screen.addComponent(filePath)
        returnButton.requestFocus()
    }

    private fun startWithFile(file: File) {
        check(file.exists()) { "File $file does not exist" }
        val data = file.readText()
        val world = World.deserialize(data)
        start(GameArea(world))
    }

    private fun saveToFile(file: File) {
        file.writeText(gameArea.world.serialize())
    }

    private fun returnToGame() {
        start(gameArea)
    }

    private fun start(gameArea: GameArea) {
        replaceWith(MainGameView(tileGrid, theme, gameArea))
        screen.close()
    }
}
