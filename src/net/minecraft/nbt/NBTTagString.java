package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

public class NBTTagString extends NBTBase {
	
	/** The string value for the tag (cannot be empty). */
	public String data;
	
	public NBTTagString(String par1Str) {
		super(par1Str);
	}
	
	public NBTTagString(String par1Str, String par2Str) {
		super(par1Str);
		data = par2Str;
		
		if (par2Str == null) {
			throw new IllegalArgumentException("Empty string not allowed");
		}
	}
	
	@Override
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension classes
	 */
	void write(DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeUTF(data);
	}
	
	@Override
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension classes
	 */
	void load(DataInput par1DataInput) throws IOException {
		data = par1DataInput.readUTF();
	}
	
	/**
	 * Gets the type byte for the tag.
	 */
	@Override
	public byte getId() {
		return (byte) 8;
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
		return new NBTTagString(getName(), data);
	}
	
	@Override
	public boolean equals(Object par1Obj) {
		if (!super.equals(par1Obj)) {
			return false;
		} else {
			NBTTagString nbttagstring = (NBTTagString) par1Obj;
			return ((data == null) && (nbttagstring.data == null)) || ((data != null) && data.equals(nbttagstring.data));
		}
	}
	
	@Override
	public int hashCode() {
		return super.hashCode() ^ data.hashCode();
	}
}
