package org.fancy.memers.ui.main.escape

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow

internal object EscapeScreenConfig {
    const val FANCY_FILEPATH = "FILE PATH"
    const val TITLE = "Escape menu"
    const val START_FILE = "Start from file!"
    const val SAVE_FILE = "Save to file!"
    const val RETURN = "RETURN"
    const val START_NEW = "Start New Game!"
    const val DEFAULT_SAVE_FILE = "game.save"

    val BASE_BUTTON_BUILDER = Components.button()
        .withDecorations(shadow())
        .withDecorations(box())
}
