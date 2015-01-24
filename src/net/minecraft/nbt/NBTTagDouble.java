package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagDouble extends NBTBase {
	
	/** The double value for the tag. */
	public double data;
	
	public NBTTagDouble(String par1Str) {
		super(par1Str);
	}
	
	public NBTTagDouble(String par1Str, double par2) {
		super(par1Str);
		data = par2;
	}
	
	@Override
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeDouble(data);
	}
	
	@Override
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension classes
	 */
	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readDouble();
	}
	
	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 6;
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
		return new NBTTagDouble(getName(), data);
	}
	
	@Override
	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagDouble nbttagdouble = (NBTTagDouble) par1Obj;
			return data == nbttagdouble.data;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		long i = Double.doubleToLongBits(data);
		return super.hashCode() ^ (int) (i ^ (i >>> 32));
	}
}
