package com.skaggsm.sharedmemory.posix;

import com.skaggsm.sharedmemory.SharedMemory;
import com.sun.jna.Pointer;

import java.io.IOException;

import static com.sun.jna.Pointer.NULL;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */

public class SharedMemoryPOSIX implements SharedMemory {
    private static final int O_RDWR = 0;

    private static final int S_IRUSR = 0;
    private static final int S_IWUSR = 0;

    private static final int PROT_READ = 0;
    private static final int PROT_WRITE = 0;

    private static final int MAP_SHARED = 0;

    private int fileDescriptor;
    private Pointer memory;
    private long size;
    private String name;

    private boolean closed;

    public SharedMemoryPOSIX(String name, long size) {
        this.size = size;

        // TODO refactor to use visitor to safely use getuid?
        this.name = "/" + name + "." + LibC.INSTANCE.getuid();

        fileDescriptor = LibC.INSTANCE.shm_open(this.name, O_RDWR, S_IRUSR | S_IWUSR);
        memory = LibC.INSTANCE.mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, fileDescriptor, 0);
    }

    @Override
    public Pointer getMemory() {
        return memory;
    }

    @Override
    public void close() throws IOException {
        if (closed)
            return;

        LibC.INSTANCE.munmap(memory, size);
        LibC.INSTANCE.shm_unlink(name);
        memory = null;
        fileDescriptor = -1;

        closed = true;
    }
}
