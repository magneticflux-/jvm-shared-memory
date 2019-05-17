package com.skaggsm.sharedmemory.posix;

/**
 * Created by Mitchell Skaggs on 5/16/2019.
 */

interface MMAN {
    int PROT_READ = 0x1;
    int PROT_WRITE = 0x2;
    int MAP_SHARED = 0x01;
}
