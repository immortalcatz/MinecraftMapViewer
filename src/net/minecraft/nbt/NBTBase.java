package net.minecraft.nbt;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
// import net.minecraft.crash.CrashReport;
// import net.minecraft.crash.CrashReportCategory;
// import net.minecraft.util.ReportedException;

public abstract class NBTBase {
	
	public static final String[] NBTTypes = new String[] { "END", "BYTE", "SHORT", "INT", "LONG", "FLOAT", "DOUBLE", "BYTE[]", "STRING", "LIST", "COMPOUND", "INT[]" };
	
	/** The UTF string key used to lookup values. */
	private String name;
	
	/**
	 * Write the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	abstract void write(DataOutput dataoutput) throws IOException;
	
	/**
	 * Read the actual data contents of the tag, implemented in NBT extension
	 * classes
	 */
	abstract void load(DataInput datainput) throws IOException;
	
	/**
	 * Gets the type byte for the tag.
	 */
	public abstract byte getId();
	
	protected NBTBase(String par1Str) {
		if (par1Str == null) {
			name = "";
		} else {
			name = par1Str;
		}
	}
	
	/**
	 * Sets the name for this tag and returns this for convenience.
	 */
	public NBTBase setName(String par1Str) {
		if (par1Str == null) {
			name = "";
		} else {
			name = par1Str;
		}
		
		return this;
	}
	
	/**
	 * Gets the name corresponding to the tag, or an empty string if none set.
	 */
	public String getName() {
		return name == null ? "" : name;
	}
	
	/**
	 * Reads and returns a tag from the given DataInput, or the End tag if no
	 * tag could be read.
	 */
	public static NBTBase readNamedTag(DataInput par0DataInput) throws IOException {
		byte b0 = par0DataInput.readByte();
		
		if (b0 == 0) {
			return new NBTTagEnd();
		} else {
			String s = par0DataInput.readUTF();
			NBTBase nbtbase = NBTBase.newTag(b0, s);
			
			try {
				nbtbase.load(par0DataInput);
				return nbtbase;
			} catch (IOException ioexception) {
				// CrashReport crashreport =
				// CrashReport.makeCrashReport(ioexception, "Loading NBT data");
				// CrashReportCategory crashreportcategory =
				// crashreport.makeCategory("NBT Tag");
				// crashreportcategory.addCrashSection("Tag name", s);
				// crashreportcategory.addCrashSection("Tag type",
				// Byte.valueOf(b0));
				// throw new ReportedException(crashreport);
				throw new RuntimeException();
			}
		}
	}
	
	/**
	 * Writes the specified tag to the given DataOutput, writing the type byte,
	 * the UTF string key and then calling the
	 * tag to write its data.
	 */
	public static void writeNamedTag(NBTBase par0NBTBase, DataOutput par1DataOutput) throws IOException {
		par1DataOutput.writeByte(par0NBTBase.getId());
		
		if (par0NBTBase.getId() != 0) {
			par1DataOutput.writeUTF(par0NBTBase.getName());
			par0NBTBase.write(par1DataOutput);
		}
	}
	
	/**
	 * Creates and returns a new tag of the specified type, or null if invalid.
	 */
	public static NBTBase newTag(byte par0, String par1Str) {
		switch (par0) {
			case 0:
				return new NBTTagEnd();
			case 1:
				return new NBTTagByte(par1Str);
			case 2:
				return new NBTTagShort(par1Str);
			case 3:
				return new NBTTagInt(par1Str);
			case 4:
				return new NBTTagLong(par1Str);
			case 5:
				return new NBTTagFloat(par1Str);
			case 6:
				return new NBTTagDouble(par1Str);
			case 7:
				return new NBTTagByteArray(par1Str);
			case 8:
				return new NBTTagString(par1Str);
			case 9:
				return new NBTTagList(par1Str);
			case 10:
				return new NBTTagCompound(par1Str);
			case 11:
				return new NBTTagIntArray(par1Str);
			default:
				return null;
		}
	}
	
	/**
	 * Returns the string name of a tag with the specified type, or 'UNKNOWN' if
	 * invalid.
	 */
	public static String getTagName(byte par0) {
		switch (par0) {
			case 0:
				return "TAG_End";
			case 1:
				return "TAG_Byte";
			case 2:
				return "TAG_Short";
			case 3:
				return "TAG_Int";
			case 4:
				return "TAG_Long";
			case 5:
				return "TAG_Float";
			case 6:
				return "TAG_Double";
			case 7:
				return "TAG_Byte_Array";
			case 8:
				return "TAG_String";
			case 9:
				return "TAG_List";
			case 10:
				return "TAG_Compound";
			case 11:
				return "TAG_Int_Array";
			default:
				return "UNKNOWN";
		}
	}
	
	/**
	 * Creates a clone of the tag.
	 */
	public abstract NBTBase copy();
	
	@Override
	public boolean equals(Object par1Obj) {
		if (!(par1Obj instanceof NBTBase)) {
			return false;
		} else {
			NBTBase nbtbase = (NBTBase) par1Obj;
			return getId() != nbtbase.getId() ? false : (((name != null) || (nbtbase.name == null)) && ((name == null) || (nbtbase.name != null)) ? (name == null) || name.equals(nbtbase.name) : false);
		}
	}
	
	@Override
	public int hashCode() {
		return name.hashCode() ^ getId();
	}
}
