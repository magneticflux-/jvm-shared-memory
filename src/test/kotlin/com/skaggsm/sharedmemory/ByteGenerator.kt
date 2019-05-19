package com.skaggsm.sharedmemory

import io.kotlintest.properties.Gen
import java.util.concurrent.ThreadLocalRandom

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */
object ByteGenerator : Gen<Byte> {
    override fun constants(): Iterable<Byte> = listOf(Byte.MIN_VALUE, Byte.MAX_VALUE, 0)

    override fun random(): Sequence<Byte> = generateSequence {
        val byte = byteArrayOf(0)
        ThreadLocalRandom.current().nextBytes(byte)
        byte[0]
    }
}
