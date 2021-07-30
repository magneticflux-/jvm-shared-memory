package com.skaggsm.sharedmemory.posix;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Platform;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */

public interface LibC extends Library {
    LibC INSTANCE = Native.loadLibrary(Platform.C_LIBRARY_NAME, LibC.class);

    int getuid();

    int ftruncate(int fd, long length);

    String strerror(int errnum);

    int close(int fd);
}
