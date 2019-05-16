package com.skaggsm.sharedmemory

import io.kotlintest.properties.assertAll
import io.kotlintest.specs.StringSpec
import org.amshove.kluent.`should equal`

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */
class SharedMemoryTest : StringSpec({
    "Given two shared memory references with the same name, when the first is modified, both are updated" {
        assertAll(ByteGenerator()) { byte: Byte ->
            SharedMemory.getSharedMemory("TestMemory", 1).use { ref1 ->
                SharedMemory.getSharedMemory("TestMemory", 1).use { ref2 ->
                    ref1.memory.setByte(0, byte)

                    ref1.memory.getByte(0) `should equal` byte
                    ref2.memory.getByte(0) `should equal` byte
                }
            }
        }
    }

    "Given two shared memory references with the same name, when the second is modified, both are updated" {
        assertAll(ByteGenerator()) { byte: Byte ->
            SharedMemory.getSharedMemory("TestMemory", 1).use { ref1 ->
                SharedMemory.getSharedMemory("TestMemory", 1).use { ref2 ->
                    ref2.memory.setByte(0, byte)

                    ref1.memory.getByte(0) `should equal` byte
                    ref2.memory.getByte(0) `should equal` byte
                }
            }
        }
    }

    "Given two shared memory references with different names, when they are both modified, they should have the correct values" {
        assertAll(ByteGenerator(), ByteGenerator()) { byte1: Byte, byte2: Byte ->
            SharedMemory.getSharedMemory("TestMemory_1", 1).use { ref1 ->
                SharedMemory.getSharedMemory("TestMemory_2", 1).use { ref2 ->
                    ref1.memory.setByte(0, byte1)
                    ref2.memory.setByte(0, byte2)

                    ref1.memory.getByte(0) `should equal` byte1
                    ref2.memory.getByte(0) `should equal` byte2
                }
            }
        }
    }
})
