package com.skaggsm.sharedmemory.posix;

import com.sun.jna.Platform;
import com.sun.jna.Pointer;

/**
 * Created by Mitchell Skaggs on 5/16/2019.
 */

abstract class MMAN {
    static final int PROT_READ;
    static final int PROT_WRITE;
    static final int MAP_SHARED;
    static final Pointer MAP_FAILED = Pointer.createConstant(-1);

    static {
        switch (Platform.getOSType()) {
            case Platform.MAC:
            case Platform.FREEBSD:
            case Platform.OPENBSD:
            case Platform.KFREEBSD:
            case Platform.NETBSD:

                // Source: https://github.com/nneonneo/osx-10.9-opensource/blob/master/xnu-2422.1.72/bsd/sys/mman.h

                PROT_READ = 0x01;
                PROT_WRITE = 0x02;
                MAP_SHARED = 0x0001;

                break;
            default:

                // Source: /usr/include/x86_64-linux-gnu/bits/mman-linux.h

                PROT_READ = 0x1;
                PROT_WRITE = 0x2;
                MAP_SHARED = 0x01;

                break;
        }
    }
}
