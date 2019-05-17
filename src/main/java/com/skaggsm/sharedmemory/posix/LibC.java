package com.skaggsm.sharedmemory.posix;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */

public interface LibC extends Library {
    LibC INSTANCE = Native.loadLibrary("c", LibC.class);

    int getuid();

    int ftruncate(int fd, long length);
}
