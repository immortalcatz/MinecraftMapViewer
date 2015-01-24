package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagShort extends NBTBase {
	
	/** The short value for the tag. */
	public short data;
	
	public NBTTagShort(String par1Str) {
		super(par1Str);
	}
	
	public NBTTagShort(String par1Str, short par2) {
		super(par1Str);
		data = par2;
	}
	
	@Override
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeShort(data);
	}
	
	@Override
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension classes
	 */
	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readShort();
	}
	
	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 2;
	}
	
	@Override
	public String toString() {
		return "" + data;
	}
	
	/**
	 * Creates a clone of the tag.
	 */
	@Override
	public NBTBase copy() {
		return new NBTTagShort(getName(), data);
	}
	
	@Override
	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagShort nbttagshort = (NBTTagShort) par1Obj;
			return data == nbttagshort.data;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ data;
	}
}
