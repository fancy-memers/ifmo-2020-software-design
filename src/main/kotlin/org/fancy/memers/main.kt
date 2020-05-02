package org.fancy.memers

import org.fancy.memers.ui.start.StartMenuView
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig

fun main() {
    val config = AppConfig.newBuilder()
        .withSize(70, 40)
        .withDefaultTileset(CP437TilesetResources.rexPaint16x16())
        .withTitle("RogueLike")
        .build()

    val theme = ColorThemes.arc()
    val tileGrid = SwingApplications.startTileGrid(config)

    val startMenu = StartMenuView(tileGrid, theme)

    startMenu.dock()
}
