package com.PinkCats.worldprotect.Database.Lib;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.item.ItemStack;

import java.io.*;

public final class NBTData {

    private NBTData() {}

    public static ItemStack dePackageItemStack(byte[] data) throws IOException {
        if (data == null || data.length == 0) return ItemStack.EMPTY;
        return nbtToItemStack(blobToNbt(data));
    }

    public static byte[] packageItemStack(ItemStack itemStack) throws IOException {
        if (itemStack == null || itemStack.isEmpty()) return new byte[0];
        return nbtToBlob(itemStackToNbt(itemStack));
    }

    public static CompoundTag itemStackToNbt(ItemStack itemStack) {
        return itemStack.serializeNBT();
    }

    public static ItemStack nbtToItemStack(CompoundTag compoundTag) {
        if (compoundTag == null) return ItemStack.EMPTY;
        return ItemStack.of(compoundTag);
    }

    public static byte[] nbtToBlob(CompoundTag nbt) throws IOException {
        if (nbt == null) return new byte[0];

        try (ByteArrayOutputStream Stream = new ByteArrayOutputStream();
             DataOutputStream dos = new DataOutputStream(Stream)) {

            NbtIo.write(nbt, dos);
            dos.flush();
            return Stream.toByteArray();
        }
    }

    public static CompoundTag blobToNbt(byte[] blob) throws IOException {
        if (blob == null || blob.length == 0) return new CompoundTag();

        try (ByteArrayInputStream bais = new ByteArrayInputStream(blob);
             DataInputStream dis = new DataInputStream(bais)) {

            return NbtIo.read(dis);
        }
    }
}
