package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagFloat extends NBTBase {
	
	/** The float value for the tag. */
	public float data;
	
	public NBTTagFloat(String par1Str) {
		super(par1Str);
	}
	
	public NBTTagFloat(String par1Str, float par2) {
		super(par1Str);
		data = par2;
	}
	
	@Override
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeFloat(data);
	}
	
	@Override
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension classes
	 */
	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readFloat();
	}
	
	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 5;
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
		return new NBTTagFloat(getName(), data);
	}
	
	@Override
	public boolean equals(Object par1Obj) {
		if (super.equals(par1Obj)) {
			NBTTagFloat nbttagfloat = (NBTTagFloat) par1Obj;
			return data == nbttagfloat.data;
		} else {
			return false;
		}
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ Float.floatToIntBits(data);
	}
}
