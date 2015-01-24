package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagByteArray extends NBTBase {
	
	/** The byte array stored in the tag. */
	public byte[] byteArray;
	
	public NBTTagByteArray(String par1Str) {
		super(par1Str);
	}
	
	public NBTTagByteArray(String par1Str, byte[] par2ArrayOfByte) {
		super(par1Str);
		byteArray = par2ArrayOfByte;
	}
	
	@Override
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeInt(byteArray.length);
		par1DataOutput.write(byteArray);
	}
	
	@Override
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension classes
	 */
	void load(DataInput par1DataInput) throws IOException {
		int i = par1DataInput.readInt();
		byteArray = new byte[i];
		par1DataInput.readFully(byteArray);
	}
	
	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 7;
	}
	
	@Override
	public String toString() {
		return "[" + byteArray.length + " bytes]";
	}
	
	/**
	 * Creates a clone of the tag.
	 */
	@Override
	public NBTBase copy() {
		byte[] abyte = new byte[byteArray.length];
		System.arraycopy(byteArray, 0, abyte, 0, byteArray.length);
		return new NBTTagByteArray(getName(), abyte);
	}
	
	@Override
	public boolean equals(Object par1Obj) {
		return super.equals(par1Obj) ? Arrays.equals(byteArray, ((NBTTagByteArray) par1Obj).byteArray) : false;
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(byteArray);
	}
}
