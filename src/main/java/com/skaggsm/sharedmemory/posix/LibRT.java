package com.skaggsm.sharedmemory.posix;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.Pointer;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */
public interface LibRT extends Library {
    LibRT INSTANCE = getInstance();

    static LibRT getInstance() {
        if (Platform.isMac())
            return Native.loadLibrary(Platform.C_LIBRARY_NAME, MacOSLibRT.class);
        else
            return Native.loadLibrary("rt", LibRT.class);
    }

    int shm_open(String name, int oFlag, int mode);

    int shm_unlink(String name);

    Pointer mmap(Pointer addr, long length, int prot, int flags, int fd, long offset);

    int munmap(Pointer addr, long length);

    interface MacOSLibRT extends LibRT {
        default int shm_open(String name, int oFlag, int mode) {
            return shm_open(name, oFlag, new Object[]{mode});
        }

        int shm_open(String name, int oFlag, Object... mode);
    }
}
