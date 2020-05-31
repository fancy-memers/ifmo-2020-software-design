package org.fancy.memers.utils.logger

import org.hexworks.cobalt.events.api.Event


data class LogEvent(val content: String, override val emitter: Any) : Event
