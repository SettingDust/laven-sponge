package me.settingdust.laven

import com.flowpowered.math.vector.Vector3d
import kotlin.math.log2

private val NUM_X_BITS = 1 + log2(smallestEncompassingPowerOfTwo(30000000).toDouble()).toInt()
private val NUM_Z_BITS = NUM_X_BITS
private val NUM_Y_BITS = 64 - NUM_X_BITS - NUM_Z_BITS
private val Y_SHIFT = 0 + NUM_Z_BITS
private val X_SHIFT = Y_SHIFT + NUM_Y_BITS
private val X_MASK = (1L shl NUM_X_BITS) - 1L
private val Y_MASK = (1L shl NUM_Y_BITS) - 1L
private val Z_MASK = (1L shl NUM_Z_BITS) - 1L

fun longToVector3d(serialized: Long): Vector3d {
    val i = (serialized shl 64 - X_SHIFT - NUM_X_BITS shr 64 - NUM_X_BITS).toDouble()
    val j = (serialized shl 64 - Y_SHIFT - NUM_Y_BITS shr 64 - NUM_Y_BITS).toDouble()
    val k = (serialized shl 64 - NUM_Z_BITS shr 64 - NUM_Z_BITS).toDouble()
    return Vector3d(i, j, k)
}

fun Vector3d.toPacketLong(): Long =
    x.toLong() and X_MASK shl X_SHIFT or (y.toLong() and Y_MASK shl Y_SHIFT) or (z.toLong() and Z_MASK shl 0)

/**
 * Returns the input value rounded up to the next highest power of two.
 */
private fun smallestEncompassingPowerOfTwo(value: Int): Int {
    var i = value - 1
    i = i or i shr 1
    i = i or i shr 2
    i = i or i shr 4
    i = i or i shr 8
    i = i or i shr 16
    return i + 1
}