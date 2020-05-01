package org.fancy.memers.model

abstract class Tile {
    abstract val isEmpty: Boolean
}

class EmptyTile : Tile() {
    override val isEmpty: Boolean = true
}

class WallTile : Tile() {
    override val isEmpty: Boolean = false
}

abstract class Entity(var i: Int, var j: Int)
class Player(i: Int, j: Int) : Entity(i, j)

class Map internal constructor(
    private val map: Array<Array<Tile>>
) {

    val entities: MutableList<Entity> = mutableListOf()
    val height: Int get() = map.size
    val width: Int get() = map[0].size

    fun getTile(i: Int, j: Int): Tile = map[i][j]

    fun isEmpty(i: Int, j: Int): Boolean = getTile(i, j).isEmpty

    fun addEntity(entity: Entity) {
        entities.add(entity)
    }
}
