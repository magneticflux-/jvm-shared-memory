package com.skaggsm.sharedmemory;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.Kernel32;
import com.sun.jna.platform.win32.WinNT;

import static com.sun.jna.platform.win32.WinBase.INVALID_HANDLE_VALUE;
import static com.sun.jna.platform.win32.WinNT.PAGE_READWRITE;

/**
 * Created by Mitchell Skaggs on 5/11/2019.
 */
public class SharedMemoryWin32 implements SharedMemory {
    public static final int FILE_MAP_ALL_ACCESS = 0xf001f;
    private WinNT.HANDLE mapping;
    private Pointer memory;
    private boolean closed;

    SharedMemoryWin32(String name) {
        mapping = Kernel32.INSTANCE.CreateFileMapping(INVALID_HANDLE_VALUE, null, PAGE_READWRITE, 0, 1, name);
        memory = Kernel32.INSTANCE.MapViewOfFile(mapping, FILE_MAP_ALL_ACCESS, 0, 0, 0);
        closed = false;
    }

    @Override
    public Pointer getMemory() {
        return memory;
    }

    @Override
    public void close() {
        if (closed)
            return;

        Kernel32.INSTANCE.UnmapViewOfFile(memory);
        Kernel32.INSTANCE.CloseHandle(mapping);
        mapping = null;
        memory = null;

        closed = true;
    }
}
