package com.skaggsm.sharedmemory.posix;

import com.skaggsm.sharedmemory.SharedMemory;
import com.sun.jna.Native;
import com.sun.jna.Pointer;

import static com.skaggsm.sharedmemory.posix.FCNTL.O_CREAT;
import static com.skaggsm.sharedmemory.posix.FCNTL.O_RDWR;
import static com.skaggsm.sharedmemory.posix.MMAN.*;
import static com.skaggsm.sharedmemory.posix.STAT.S_IRUSR;
import static com.skaggsm.sharedmemory.posix.STAT.S_IWUSR;
import static com.sun.jna.Pointer.NULL;

/**
 * Created by Mitchell Skaggs on 5/15/2019.
 */

public class SharedMemoryPOSIX implements SharedMemory {

    private int fileDescriptor;
    private Pointer memory;
    private long size;
    private String name;

    private boolean closed;
    private boolean hasOwnership;

    public SharedMemoryPOSIX(String name, long size) {
        this.size = size;

        // TODO refactor to use visitor to safely use getuid?
        this.name = String.format("/%s.%d", name, LibC.INSTANCE.getuid());

        if (this.name.length() > 31)
            System.err.printf("Name %s is probably too long for macOS (max 31 chars)%n", this.name);

        fileDescriptor = LibRT.INSTANCE.shm_open(this.name, O_RDWR, S_IRUSR | S_IWUSR);
        if (fileDescriptor < 0) {
            fileDescriptor = LibRT.INSTANCE.shm_open(this.name, O_RDWR | O_CREAT, S_IRUSR | S_IWUSR);
            hasOwnership = true;
        }
        if (fileDescriptor < 0)
            throw new RuntimeException(LibC.INSTANCE.strerror(Native.getLastError()));

        if (hasOwnership) {
            int ftruncateCode = LibC.INSTANCE.ftruncate(fileDescriptor, size);
            if (ftruncateCode < 0)
                throw new RuntimeException(LibC.INSTANCE.strerror(Native.getLastError()));
        }

        memory = LibRT.INSTANCE.mmap(NULL, size, PROT_READ | PROT_WRITE, MAP_SHARED, fileDescriptor, 0);
        if (memory.equals(MAP_FAILED))
            throw new RuntimeException(LibC.INSTANCE.strerror(Native.getLastError()));
    }

    @Override
    public Pointer getMemory() {
        return memory;
    }

    @Override
    public void close() {
        if (closed)
            return;

        int munmapCode = LibRT.INSTANCE.munmap(memory, size);
        if (munmapCode < 0)
            throw new RuntimeException(LibC.INSTANCE.strerror(Native.getLastError()));

        int closeCode = LibC.INSTANCE.close(fileDescriptor);
        if (closeCode < 0)
            throw new RuntimeException(LibC.INSTANCE.strerror(Native.getLastError()));

        if (hasOwnership) {
            int shmUnlinkCode = LibRT.INSTANCE.shm_unlink(name);
            if (shmUnlinkCode < 0)
                throw new RuntimeException(LibC.INSTANCE.strerror(Native.getLastError()));
        }

        memory = null;
        fileDescriptor = -1;

        closed = true;
    }
}
