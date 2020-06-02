package org.fancy.memers

import com.squareup.moshi.Moshi
import com.squareup.moshi.adapters.PolymorphicJsonAdapterFactory
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import org.fancy.memers.model.ai.AggressiveEnemyBehaviour
import org.fancy.memers.model.ai.EnemyBehaviour
import org.fancy.memers.model.ai.FunkyEnemyBehaviour
import org.fancy.memers.model.ai.PassiveEnemyBehaviour
import org.fancy.memers.model.drawable.*
import org.fancy.memers.ui.main.board.World
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

private val MOSHI = Moshi.Builder()
    .add(
        PolymorphicJsonAdapterFactory.of(Creature::class.java, "entityType")
            .withSubtype(Player::class.java, "player")
            .withSubtype(Enemy::class.java, "enemy")
    )
    .add(
        PolymorphicJsonAdapterFactory.of(Item::class.java, "itemType")
            .withSubtype(Knife::class.java, "knife")
            .withSubtype(Mace::class.java, "mace")
            .withSubtype(Armor::class.java, "armor")
    )
    .add(
        PolymorphicJsonAdapterFactory.of(Block::class.java, "blockType")
            .withSubtype(Empty::class.java, "empty")
            .withSubtype(Floor::class.java, "floor")
            .withSubtype(Wall::class.java, "wall")

            .withSubtype(Creature::class.java, "entity")
            .withSubtype(Player::class.java, "player")
            .withSubtype(Enemy::class.java, "enemy")

            .withSubtype(Item::class.java, "item")
            .withSubtype(Knife::class.java, "knife")
            .withSubtype(Mace::class.java, "mace")
            .withSubtype(Armor::class.java, "armor")
    )
    .add(
        PolymorphicJsonAdapterFactory.of(EnemyBehaviour::class.java, "enemyBehaviour")
            .withSubtype(AggressiveEnemyBehaviour::class.java, "aggressiveEnemyBehaviour")
            .withSubtype(FunkyEnemyBehaviour::class.java, "funkyEnemyBehaviour")
            .withSubtype(PassiveEnemyBehaviour::class.java, "passiveEnemyBehaviour")
    )
    .add(KotlinJsonAdapterFactory())
    .build()
private val ADAPTER = MOSHI.adapter(WorldWrapper::class.java)

private data class WorldWrapper(
    val size: Size3D,

    // moshi does not have adapter for pairs: https://github.com/square/moshi/issues/508
    val positions: List<Position3D>,
    val blocks: List<Block>
)

fun World.serialize(): String {
    val wrapper = WorldWrapper(boardSize, board.keys.toList(), board.values.toList())
    return ADAPTER.toJson(wrapper)
}

fun World.Companion.deserialize(json: String): World {
    val wrapper = ADAPTER.fromJson(json)!!
    val board = (wrapper.positions zip wrapper.blocks).toMap(mutableMapOf())
    return World(wrapper.size, board)
}
