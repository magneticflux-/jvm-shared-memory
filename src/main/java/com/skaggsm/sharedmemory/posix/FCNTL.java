package com.skaggsm.sharedmemory.posix;

import com.sun.jna.Platform;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */

@SuppressWarnings("OctalInteger")
abstract class FCNTL {
    final static int O_CREAT;
    final static int O_RDWR;

    static {
        switch (Platform.getOSType()) {
            case Platform.MAC:
            case Platform.FREEBSD:
            case Platform.OPENBSD:
            case Platform.KFREEBSD:
            case Platform.NETBSD:
                O_CREAT = 0x0200;

                O_RDWR = 2;
                break;
            default:
                O_CREAT = 0100;

                O_RDWR = 02;
                break;
        }
    }
}
