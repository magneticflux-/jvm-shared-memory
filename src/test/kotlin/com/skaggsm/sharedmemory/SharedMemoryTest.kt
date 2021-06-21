package com.skaggsm.sharedmemory

import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.property.Arb
import io.kotest.property.arbitrary.byte
import io.kotest.property.checkAll
import java.util.*

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */
class SharedMemoryTest : StringSpec({
    "Given two shared memory references with the same name, when the first has a byte set, then both are updated" {
        val outerName = getName()

        checkAll(Arb.byte()) { byte: Byte ->
            val name = "${outerName}_${this.attempts()}"

            SharedMemory.getSharedMemory(name, 1).use { ref1 ->
                SharedMemory.getSharedMemory(name, 1).use { ref2 ->
                    ref1.memory.setByte(0, byte)

                    ref1.memory.getByte(0) shouldBe byte
                    ref2.memory.getByte(0) shouldBe byte
                }
            }
        }
    }

    "Given two shared memory references with the same name, when the second has a byte set, then both are updated" {
        val outerName = getName()

        checkAll(Arb.byte()) { byte: Byte ->
            val name = "${outerName}_${this.attempts()}"

            SharedMemory.getSharedMemory(name, 1).use { ref1 ->
                SharedMemory.getSharedMemory(name, 1).use { ref2 ->
                    ref2.memory.setByte(0, byte)

                    ref1.memory.getByte(0) shouldBe byte
                    ref2.memory.getByte(0) shouldBe byte
                }
            }
        }
    }

    "Given two shared memory references with different names, when they both have a byte set, then they should have the correct values" {
        val outerName = getName()

        checkAll(Arb.byte(), Arb.byte()) { byte1: Byte, byte2: Byte ->
            val name = "${outerName}_${this.attempts()}"

            SharedMemory.getSharedMemory("${name}_1", 1).use { ref1 ->
                SharedMemory.getSharedMemory("${name}_2", 1).use { ref2 ->
                    ref1.memory.setByte(0, byte1)
                    ref2.memory.setByte(0, byte2)

                    ref1.memory.getByte(0) shouldBe byte1
                    ref2.memory.getByte(0) shouldBe byte2
                }
            }
        }
    }

    "Given a shared memory reference, when another reference to the same name is reopened, then both are usable" {
        val outerName = getName()

        checkAll(Arb.byte()) { byte: Byte ->
            val name = "${outerName}_${this.attempts()}"

            SharedMemory.getSharedMemory(name, 1).use { ref1 ->
                ref1.memory.setByte(0, byte)

                SharedMemory.getSharedMemory(name, 1).use { ref2 ->
                    ref1.memory.getByte(0) shouldBe byte
                    ref2.memory.getByte(0) shouldBe byte
                }

                SharedMemory.getSharedMemory(name, 1).use { ref2 ->
                    ref1.memory.getByte(0) shouldBe byte
                    ref2.memory.getByte(0) shouldBe byte
                }
            }
        }
    }

    "Given two shared memory references with the same name, when the first has a string set, then both are updated" {
        val outerName = getName()

        checkAll { string: String ->
            val name = "${outerName}_${this.attempts()}"

            val size = Char.SIZE_BYTES.toLong() * string.length.coerceAtLeast(1)

            SharedMemory.getSharedMemory(name, size).use { ref1 ->
                SharedMemory.getSharedMemory(name, size).use { ref2 ->
                    ref1.memory.setString(0, string)

                    ref1.memory.getString(0) shouldBe string
                    ref2.memory.getString(0) shouldBe string
                }
            }
        }
    }
})

fun getName(): String {
    return UUID.randomUUID().toString().take(12)
}
