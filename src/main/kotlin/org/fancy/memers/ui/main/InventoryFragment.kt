package org.fancy.memers.ui.main

import org.fancy.memers.model.buffs.Inventory
import org.fancy.memers.model.drawable.Item
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.uievent.ComponentEventType
import org.hexworks.zircon.api.uievent.Processed

class InventoryFragment(
    private val inventory: Inventory,
    width: Int,
    onDrop: (Item) -> Unit
) : Fragment {
    override val root: Component = Components.vbox()
        .withSize(width, inventory.size + 1)
        .build().apply {
            addComponent(Components.hbox()
                .withSpacing(1)
                .withSize(width, 1)
                .build().apply {
                    addComponent(Components.label().withText("").withSize(1, 1))
                    addComponent(Components.header().withText("NAME").withSize(NAME_COLUMN_WIDTH, 1))
                    addComponent(Components.header().withText("ACTIONS").withSize(ACTIONS_COLUMN_WIDTH, 1))
                })

            for (item in inventory) {
                addFragment(InventoryRowFragment(width, item).apply {
                    dropButton.handleComponentEvents(ComponentEventType.ACTIVATED) {
                        inventory.remove(item)
                        onDrop(item)
                        Processed
                    }
                })
            }


        }

    companion object {
        const val NAME_COLUMN_WIDTH = 15
        const val ACTIONS_COLUMN_WIDTH = 10
    }
}
