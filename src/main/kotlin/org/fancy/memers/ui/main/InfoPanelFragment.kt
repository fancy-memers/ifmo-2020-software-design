package org.fancy.memers.ui.main

import org.fancy.memers.ui.main.board.World
import org.hexworks.cobalt.events.api.Event
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.subscribeTo
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Fragment
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.internal.Zircon

class WorldUpdate(val world: World, override val emitter: Any): Event {
    companion object {
        fun publish(world: World) {
            Zircon.eventBus.publish(WorldUpdate(world, this))
        }
    }
}

class InfoPanelFragment(parent: TileGrid) : Fragment {
    override val root = Components.vbox()
        .withSize(MainScreenConfig.infoPanelSize(parent))
        .withAlignmentWithin(parent, ComponentAlignment.RIGHT_CENTER)
        .build()


    private val levelLine = Components.label()
        .withSize(root.width, 1).build()
    private val healthLine = Components.label()
        .withSize(root.width, 1).build()
    private val strengthLine = Components.label()
        .withSize(root.width, 1).build()
    private val agilityLine = Components.label()
        .withSize(root.width, 1).build()
    private val intelligenceLine = Components.label()
        .withSize(root.width, 1).build()

    init {
        root.addComponent(levelLine)
        root.addComponent(healthLine)
        root.addComponent(strengthLine)
        root.addComponent(agilityLine)
        root.addComponent(intelligenceLine)
        Zircon.eventBus.subscribeTo<WorldUpdate> {
            it.world.player.run {
                levelLine.text = "level: $level exp: $experience"
                healthLine.text = "health: $health/$maxHealth"
                strengthLine.text = "str: $strength"
                agilityLine.text = "agi: $agility"
                intelligenceLine.text = "str: $intelligence"
            }
            KeepSubscription
        }
    }
}
