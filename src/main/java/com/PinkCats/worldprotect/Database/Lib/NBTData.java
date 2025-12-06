package com.PinkCats.worldprotect.Database.Lib;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtIo;
import net.minecraft.world.item.ItemStack;

import java.io.*;

public class NBTData {


    public static ItemStack DePackageItemStack(byte[] data) throws IOException {
        return NbtToItemStack(blobToNbt(data));
    }

    public static byte[] PackageItemStack(ItemStack itemStack) throws IOException {
        return nbtToBlob(ItemStackToNbt(itemStack));
    }



    public static CompoundTag ItemStackToNbt(ItemStack itemStack) {
        return itemStack.serializeNBT();

    }

    public static ItemStack NbtToItemStack(CompoundTag compoundTag) {
        return ItemStack.of(compoundTag);
    }


    public static byte[] nbtToBlob(CompoundTag nbt) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream = new DataOutputStream(byteArrayOutputStream);
        NbtIo.write(nbt, dataOutputStream); // 使用 NbtIo 的写入方法
        return byteArrayOutputStream.toByteArray();
    }

    public static CompoundTag blobToNbt(byte[] blob) throws IOException {
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(blob);
        DataInputStream dataInputStream = new DataInputStream(byteArrayInputStream);
        return NbtIo.read(dataInputStream);
    }
}
