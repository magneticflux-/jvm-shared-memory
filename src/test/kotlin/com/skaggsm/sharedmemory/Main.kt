package com.skaggsm.sharedmemory

import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure

class MumbleLinkMemory(p: Pointer) : Structure(p) {
    override fun getFieldOrder(): MutableList<String> {
        return mutableListOf("uiVersion", "uiTick", "fAvatarPosition", "fAvatarFront", "fAvatarTop", "name")
    }

    @JvmField
    var uiVersion = 0
    @JvmField
    var uiTick = 0
    @JvmField
    var fAvatarPosition = FloatArray(3)
    @JvmField
    var fAvatarFront = FloatArray(3)
    @JvmField
    var fAvatarTop = FloatArray(3)
    @JvmField
    var name = CharArray(256)
}

fun main() {
    SharedMemory.getSharedMemory("MumbleLink").use {
        val mem = MumbleLinkMemory(it.memory)
        mem.clear()

        val start = System.currentTimeMillis()
        while (true) {
            mem.read()
            mem.uiVersion = 2
            mem.uiTick++
            Native.toCharArray("Test app").copyInto(mem.name)
            mem.write()
            if (System.currentTimeMillis() - start > 30000)
                break
        }
    }
}
