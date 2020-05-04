package org.fancy.memers

import kotlinx.serialization.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import kotlinx.serialization.modules.serializersModuleOf
import org.fancy.memers.model.*
import org.fancy.memers.model.generator.BoardGenerator
import org.fancy.memers.ui.main.board.GameArea
import org.hexworks.zircon.api.data.Position3D
import org.hexworks.zircon.api.data.Size3D

@Serializer(forClass = Position3D::class)
private object Position3DSerializer : KSerializer<Position3D> {
    @Serializable
    private data class Position3DWrapper(val x: Int, val y: Int, val z: Int)

    override val descriptor: SerialDescriptor = Position3DWrapper.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Position3D) {
        val wrapper = with(value) { Position3DWrapper(x, y, z) }
        Position3DWrapper.serializer().serialize(encoder, wrapper)
    }

    override fun deserialize(decoder: Decoder): Position3D {
        val wrapper = Position3DWrapper.serializer().deserialize(decoder)
        return with(wrapper) { Position3D.create(x, y, z) }
    }
}

@Serializer(forClass = Size3D::class)
private object Size3DSerializer : KSerializer<Size3D> {
    @Serializable
    private data class Size3DWrapper(val xLength: Int, val yLength: Int, val zLength: Int)

    override val descriptor: SerialDescriptor = Size3DWrapper.serializer().descriptor

    override fun serialize(encoder: Encoder, value: Size3D) {
        val wrapper = with(value) { Size3DWrapper(xLength, yLength, zLength) }
        Size3DWrapper.serializer().serialize(encoder, wrapper)
    }

    override fun deserialize(decoder: Decoder): Size3D {
        val wrapper = Size3DWrapper.serializer().deserialize(decoder)
        return with(wrapper) { Size3D.create(xLength, yLength, zLength) }
    }
}

@Serializer(forClass = GameArea::class)
private object GameAreaSerializer : KSerializer<GameArea> {
    @Serializable
    private data class GameAreaWrapper(
        val size: @ContextualSerialization Size3D,
        val boardMap: Map<@ContextualSerialization Position3D, Block>
    )

    override val descriptor: SerialDescriptor = GameAreaWrapper.serializer().descriptor

    override fun serialize(encoder: Encoder, value: GameArea) {
        val wrapper = with(value) { GameAreaWrapper(actualSize, getBoardMap()) }
        GameAreaWrapper.serializer().serialize(encoder, wrapper)
    }

    override fun deserialize(decoder: Decoder): GameArea {
        val wrapper = GameAreaWrapper.serializer().deserialize(decoder)
        val generator = object : BoardGenerator {
            override fun generateMap(isWithPlayer: Boolean): Map<Position3D, Block> {
                return wrapper.boardMap
            }
        }
        return GameArea(wrapper.size, generator)
    }
}

private val context = serializersModuleOf(
    mapOf(
        Position3D::class to Position3DSerializer,
        Size3D::class to Size3DSerializer,
        Empty::class to Empty.serializer(),
        Floor::class to Floor.serializer(),
        Wall::class to Wall.serializer(),
        Player::class to Player.serializer()
    )
)
private val json = Json(JsonConfiguration.Stable, context = context)

fun GameArea.serialize(): String = json.stringify(GameAreaSerializer, this)
fun GameArea.Companion.deserialize(data: String): GameArea = json.parse(GameAreaSerializer, data)
