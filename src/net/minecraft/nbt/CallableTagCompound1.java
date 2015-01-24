package net.minecraft.nbt;

import java.util.concurrent.Callable;

class CallableTagCompound1 implements Callable<Object> {
	
	final String field_82585_a;
	
	final NBTTagCompound theNBTTagCompound;
	
	CallableTagCompound1(NBTTagCompound par1NBTTagCompound, String par2Str) {
		theNBTTagCompound = par1NBTTagCompound;
		field_82585_a = par2Str;
	}
	
	public String func_82583_a() {
		return NBTBase.NBTTypes[NBTTagCompound.getTagMap(theNBTTagCompound).get(field_82585_a).getId()];
	}
	
	@Override
	public Object call() {
		return func_82583_a();
	}
}
