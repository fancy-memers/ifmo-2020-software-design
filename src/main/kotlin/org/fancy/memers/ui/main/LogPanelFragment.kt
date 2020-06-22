package org.fancy.memers.ui.main

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.grid.TileGrid

/**
 * Панель с информацией об игровых событиях
 */
class LogPanelFragment(parent: TileGrid) : Fragment {
    override val root: LogArea = Components.logArea()
        .withSize(MainScreenConfig.logPanelSize(parent))
        .withAlignmentWithin(parent, ComponentAlignment.BOTTOM_LEFT)
        .withDecorations(box(BoxType.SINGLE, MainScreenConfig.FANCY_LOG))
        .build()
}
