package com.skaggsm.sharedmemory.posix;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */

@SuppressWarnings("OctalInteger")
interface FCNTL {
    int O_CREAT = 00000100;
    int O_RDWR = 00000002;
}
