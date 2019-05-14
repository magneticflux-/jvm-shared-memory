package com.skaggsm.sharedmemory;

import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import java.io.Closeable;

/**
 * Created by Mitchell Skaggs on 5/11/2019.
 */
public interface SharedMemory extends Closeable {

    @Deprecated
    static SharedMemory getSharedMemory(String name) {
        return getSharedMemory(name, 1);
    }

    static SharedMemory getSharedMemory(String name, long size) {
        if (Platform.isWindows())
            return new SharedMemoryWin32(name, size);
        else
            throw new UnsupportedOperationException("Shared memory is not supported outside of Windows right now!");
    }

    Pointer getMemory();
}
