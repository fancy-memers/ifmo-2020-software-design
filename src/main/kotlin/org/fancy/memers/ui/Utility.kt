package org.fancy.memers.ui

import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.UIEventPhase


fun filterKeyboardEvent(
    keyCode: KeyCode,
    action: (KeyboardEvent, UIEventPhase) -> Unit
): (KeyboardEvent, UIEventPhase) -> Unit {
    return filterKeyboardEvent({ event, _ -> event.code == keyCode }, action)
}

fun filterKeyboardEvent(
    predicate: (KeyboardEvent, UIEventPhase) -> Boolean,
    action: (KeyboardEvent, UIEventPhase) -> Unit
): (KeyboardEvent, UIEventPhase) -> Unit {
    return { event, phase ->
        if (predicate(event, phase))
            action(event, phase)
    }
}