package org.fancy.memers.ui.start

import org.fancy.memers.ui.filterKeyboardEvent
import org.fancy.memers.ui.main.MainGameView
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.*
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
                startWithFile(
                    File(
                        filePath.text
                    )
                )
            }
        )
        startGenerated.processComponentEvents(ComponentEventType.ACTIVATED) { this.startGeneratedWorld() }
        startFromFile.processComponentEvents(ComponentEventType.ACTIVATED) { this.startWithFile(File(filePath.text)) }


        screen.addComponent(header)
        screen.addComponents(startGenerated, startFromFile)
        screen.addComponent(filePath)
        startGenerated.requestFocus()
    }

    private fun startWithFile(filePath: File) {
//        TODO("")
    }

    private fun startGeneratedWorld() {

        replaceWith(MainGameView(tileGrid, theme))
        screen.close()
    }
}