package org.fancy.memers.ui.main

import org.hexworks.zircon.api.uievent.KeyCode

object KeyboardControls {
    val MOVE_UP = listOf(KeyCode.KEY_W, KeyCode.UP)
    val MOVE_DOWN = listOf(KeyCode.KEY_S, KeyCode.DOWN)
    val MOVE_LEFT = listOf(KeyCode.KEY_A, KeyCode.LEFT)
    val MOVE_RIGHT = listOf(KeyCode.KEY_D, KeyCode.RIGHT)
    val SKIP_TURN = listOf(KeyCode.KEY_N)
    val ESCAPE_MENU = listOf(KeyCode.ESCAPE)
    val INVENTORY_MENU = listOf(KeyCode.KEY_I)
}
