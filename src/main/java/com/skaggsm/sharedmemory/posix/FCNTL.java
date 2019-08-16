package com.skaggsm.sharedmemory.posix;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */

interface FCNTL {
    int O_CREAT = 0x0200;

    int O_RDONLY = 0;
    int O_WRONLY = 1;
    int O_RDWR = 2;
}
