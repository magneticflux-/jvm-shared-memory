package com.skaggsm.sharedmemory.posix;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */
interface LibRT extends Library {
    LibRT INSTANCE = Native.loadLibrary("rt", LibRT.class);

    int shm_open(String name, int oFlag, int mode);

    int shm_unlink(String name);

    Pointer mmap(Pointer addr, long length, int prot, int flags, int fd, long offset);

    int munmap(Pointer addr, long length);
}