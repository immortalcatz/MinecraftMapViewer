package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagLong extends NBTBase {
	
	/** The long value for the tag. */
	public long data;
	
	public NBTTagLong(String par1Str) {
		super(par1Str);
	}
	
	public NBTTagLong(String par1Str, long par2) {
		super(par1Str);
		data = par2;
	}
	
	@Override
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeLong(data);
	}
	
	@Override
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension classes
	 */
	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readLong();
	}
	
	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 4;
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
		return new NBTTagLong(getName(), data);
	}
	
	@Override
	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagLong nbttaglong = (NBTTagLong) par1Obj;
			return data == nbttaglong.data;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ (int) (data ^ (data >>> 32));
	}
}
