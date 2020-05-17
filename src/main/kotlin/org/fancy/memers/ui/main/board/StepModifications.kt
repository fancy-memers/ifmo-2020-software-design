package org.fancy.memers.ui.main.board

class StepModifications {
    val modifications: MutableList<GameModification> = arrayListOf()
    fun add(modification: GameModification) = modifications.add(modification)

    fun clear() = modifications.clear()

    fun movements(): List<GameModification.Move> {
        return modifications
            .mapNotNull { it as? GameModification.Move }
    }
}
