package org.fancy.memers.ui.start

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.extensions.shadow

internal object StartScreenConfig {
    const val FANCY_FILEPATH = "FILE PATH"
    const val FANCY_TITLE = "Fancy RogueLike"
    const val FANCY_START_FILE = "Start from file!"
    const val FANCY_START_GEN = "Start random!"

    val BASE_BUTTON_BUILDER = Components.button()
        .withDecorations(shadow())
        .withDecorations(box())
}