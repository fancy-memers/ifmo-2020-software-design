package org.fancy.memers

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.extensions.toScreen

fun main() {
    val tileGrid = SwingApplications.startTileGrid(
        AppConfig.newBuilder()
            .withSize(60, 30)
            .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
            .build()
    )

    val screen = tileGrid.toScreen()

    screen.addComponent(
        Components.label()
            .withText("Hello!")
            .withPosition(23, 10)
    )

    screen.display()
    screen.theme = ColorThemes.arc()
}
