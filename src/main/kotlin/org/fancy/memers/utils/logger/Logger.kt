package org.fancy.memers.utils.logger

import org.hexworks.zircon.internal.Zircon


private object Logger {
    fun log(content: String) {
        Zircon.eventBus.publish(LogEvent(content, this))
    }
}


fun log(content: String) {
    Logger.log(content)
}
