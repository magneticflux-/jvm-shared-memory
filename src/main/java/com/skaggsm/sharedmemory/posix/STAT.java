package com.skaggsm.sharedmemory.posix;

import com.sun.jna.Platform;

/**
 * Created by Mitchell Skaggs on 5/16/2019.
 */

@SuppressWarnings("OctalInteger")
abstract class STAT {
    final static int S_IRUSR;
    final static int S_IWUSR;

    static {
        switch (Platform.getOSType()) {
            case Platform.MAC:
            case Platform.FREEBSD:
            case Platform.OPENBSD:
            case Platform.KFREEBSD:
            case Platform.NETBSD:

                S_IRUSR = 0000400;
                S_IWUSR = 0000200;

                break;
            default:

                S_IRUSR = 0400;
                S_IWUSR = 0200;

                break;
        }
    }
}
