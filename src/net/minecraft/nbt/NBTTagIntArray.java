package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Arrays;

public class NBTTagIntArray extends NBTBase {
	
	/** The array of saved integers */
	public int[] intArray;
	
	public NBTTagIntArray(String par1Str) {
		super(par1Str);
	}
	
	public NBTTagIntArray(String par1Str, int[] par2ArrayOfInteger) {
		super(par1Str);
		intArray = par2ArrayOfInteger;
	}
	
	@Override
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeInt(intArray.length);
		
		for (int i = 0; i < intArray.length; ++i) {
			par1DataOutput.writeInt(intArray[i]);
		}
	}
	
	@Override
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension classes
	 */
	void load(DataInput par1DataInput) throws IOException {
		int i = par1DataInput.readInt();
		intArray = new int[i];
		
		for (int j = 0; j < i; ++j) {
			intArray[j] = par1DataInput.readInt();
		}
	}
	
	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 11;
	}
	
	@Override
	public String toString() {
		return "[" + intArray.length + " bytes]";
	}
	
	/**
	 * Creates a clone of the tag.
	 */
	@Override
	public NBTBase copy() {
		int[] aint = new int[intArray.length];
		System.arraycopy(intArray, 0, aint, 0, intArray.length);
		return new NBTTagIntArray(getName(), aint);
	}
	
	@Override
	public boolean equals(Object par1Obj) {
		if (!super.equals(par1Obj)) {
			return false;
		} else {
			NBTTagIntArray nbttagintarray = (NBTTagIntArray) par1Obj;
			return ((intArray == null) && (nbttagintarray.intArray == null)) || ((intArray != null) && Arrays.equals(intArray, nbttagintarray.intArray));
		}
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ Arrays.hashCode(intArray);
	}
}
