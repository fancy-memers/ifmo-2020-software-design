package org.fancy.memers.ui.main

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid

/**
 * Панель с основной игровой информацией, такой как характеристики игрока
 */
class InfoPanelFragment(parent: TileGrid) : Fragment {
    override val root: Component = Components.panel()
        .withSize(MainScreenConfig.infoPanelSize(parent))
        .withAlignmentWithin(parent, ComponentAlignment.RIGHT_CENTER)
        .withDecorations(box(BoxType.SINGLE))
        .build()
}
