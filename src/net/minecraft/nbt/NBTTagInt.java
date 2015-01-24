package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagInt extends NBTBase {
	
	/** The integer value for the tag. */
	public int data;
	
	public NBTTagInt(String par1Str) {
		super(par1Str);
	}
	
	public NBTTagInt(String par1Str, int par2) {
		super(par1Str);
		data = par2;
	}
	
	@Override
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeInt(data);
	}
	
	@Override
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension classes
	 */
	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readInt();
	}
	
	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 3;
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
		return new NBTTagInt(getName(), data);
	}
	
	@Override
	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagInt nbttagint = (NBTTagInt) par1Obj;
			return data == nbttagint.data;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ data;
	}
}
