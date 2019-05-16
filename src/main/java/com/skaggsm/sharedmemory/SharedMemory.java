package com.skaggsm.sharedmemory;

import com.skaggsm.sharedmemory.win32.SharedMemoryWin32;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

import java.io.Closeable;

/**
 * Created by Mitchell Skaggs on 5/11/2019.
 */
public interface SharedMemory extends Closeable {

    /**
     * @param name The name of the shared memory
     * @return A {@link SharedMemory} object pointing referring to the requested memory
     * @deprecated Use {@link SharedMemory#getSharedMemory(String, long)} and supply a size
     */
    @Deprecated
    static SharedMemory getSharedMemory(String name) {
        return getSharedMemory(name, 1);
    }

    /**
     * @param name The name of the shared memory
     * @param size The size of the shared memory, in bytes
     * @return A {@link SharedMemory} object pointing referring to the requested memory
     */
    static SharedMemory getSharedMemory(String name, long size) {
        if (Platform.isWindows())
            return new SharedMemoryWin32(name, size);
        else
            throw new UnsupportedOperationException("Shared memory is not supported outside of Windows right now!");
    }

    Pointer getMemory();
}
