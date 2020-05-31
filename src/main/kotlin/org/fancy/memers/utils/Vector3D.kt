package org.fancy.memers.utils

import org.hexworks.zircon.api.data.Position3D
import kotlin.math.roundToInt
import kotlin.math.sqrt

data class Vector3D(private val x: Int, private val y: Int, private val z: Int) {

    operator fun unaryMinus(): Vector3D {
        return Vector3D(-x, -y, -z)
    }

    operator fun unaryPlus(): Vector3D {
        return this.copy()
    }

    operator fun plus(other: Vector3D): Vector3D {
        return Vector3D(x + other.x, y + other.y, z + other.z)
    }

    operator fun minus(other: Vector3D): Vector3D {
        return this + (-other)
    }

    fun with(size: Double): Vector3D {
        val length = length()
        if (length == .0)
            return Vector3D(0, 0, 0)

        val coefficient = size / length

        return Vector3D(
            (x * coefficient).roundToInt(),
            (y * coefficient).roundToInt(),
            (z * coefficient).roundToInt()
        )
    }

    val endPoint: Position3D
        get() = Position3D.create(x, y, z)


    private fun length(): Double {
//        return abs(x) + abs(y) + abs(z)
        return sqrt((x * x + y * y + z * z).toDouble())
    }

    companion object {
        fun create(start: Position3D, destination: Position3D): Vector3D {
            return Vector3D(
                destination.x - start.x,
                destination.y - start.y,
                destination.z - start.z
            )
        }
    }
}
