package com.skaggsm.sharedmemory.io.kotlintest.provided

import io.kotlintest.AbstractProjectConfig

/**
 * Created by Mitchell Skaggs on 5/18/2019.
 */
object ProjectConfig : AbstractProjectConfig() {
    override fun parallelism(): Int = Runtime.getRuntime().availableProcessors()
}
