package org.fancy.memers.ui.main

import org.fancy.memers.model.drawable.Item
import org.fancy.memers.ui.main.board.GameArea.Companion.createTile
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.graphics.Symbols

class InventoryRowFragment(width: Int, item: Item) : Fragment {
    val dropButton = Components.button()
        .withText("${Symbols.ARROW_DOWN}")
        .build()

    override val root = Components.hbox()
        .withSpacing(1)
        .withSize(width, 1)
        .build().apply {
            addComponent(
                Components.icon()
                    .withIcon(createTile(item))
            )
            addComponent(
                Components.label()
                    .withSize(InventoryFragment.NAME_COLUMN_WIDTH, 1)
                    .withText(item.displayName)
            )
            addComponent(dropButton)
        }
}
