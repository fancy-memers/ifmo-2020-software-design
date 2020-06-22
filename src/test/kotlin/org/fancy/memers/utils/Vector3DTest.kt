package org.fancy.memers.utils

import org.hexworks.zircon.api.data.Position3D
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class Vector3DTest {
    companion object {
        val ZERO = Vector3D(0, 0, 0)
    }

    @Test
    operator fun unaryMinus() {
        val vector2 = Vector3D(3, 3, 0)
        assertEquals(Vector3D(-3, -3, 0), -vector2)

    }

    @Test
    operator fun unaryPlus() {
        val vector1 = Vector3D(1, 2, 3)
        assertEquals(vector1, +vector1)
    }

    @Test
    fun plus() {
        val vector1 = Vector3D(1, 2, 3)
        val vector2 = Vector3D(3, 3, 0)
        assertEquals(vector1, vector1 - ZERO)
        assertEquals(ZERO, vector1 - vector1)
        assertEquals(Vector3D(2, 1, -3), vector2 - vector1)
        assertEquals(Vector3D(-2, -1, 3), vector1 - vector2)
    }

    @Test
    fun minus() {
        val vector1 = Vector3D(1, 2, 3)
        val vector2 = Vector3D(3, 3, 0)
        assertEquals(vector1, vector1 + ZERO)
        assertEquals(Vector3D(4, 5, 3), vector2 + vector1)
    }

    @Test
    fun with() {
        val vector1 = Vector3D(1, 1, 0)
        assertEquals(vector1, vector1.with(1.0))
        assertEquals(-vector1, vector1.with(-1.0))
    }

    @Test
    fun getEndPoint() {
        val vector1 = Vector3D(1, 1, 0)
        assertEquals(Position3D.create(1, 1, 0), vector1.endPoint)
    }
}
