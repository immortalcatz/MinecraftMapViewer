package yushijinhun.minecraft.mapviewer;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import net.minecraft.block.material.MapColor;
import net.minecraft.nbt.CompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;

public class MapImageGen {
	
	private InputStream in;
	
	public MapImageGen(InputStream in) {
		this.in = in;
	}
	
	public MapInfo readImage() throws IOException {
		NBTTagCompound tag = CompressedStreamTools.readCompressed(in).getCompoundTag("data");
		BufferedImage img = new BufferedImage(128, 128, BufferedImage.TYPE_INT_ARGB);
		
		byte[] bytArray = tag.getByteArray("colors");
		for (int i = 0; i < bytArray.length; i++) {
			int color = toIntColor(bytArray[i]);
			
			if (color != Color.BLACK.getRGB()) {
				img.setRGB(i % 128, i / 128, color);
			}
		}
		
		return new MapInfo(tag.getInteger("xCenter"), tag.getInteger("zCenter"), tag.getInteger("dimension"), tag.getByte("scale"), img);
	}
	
	private int toIntColor(byte value) {
		int j = MapColor.mapColorArray[value / 4].colorValue;
		int k = value & 3;
		short short1 = 220;
		
		if (k == 2) {
			short1 = 255;
		}
		
		if (k == 0) {
			short1 = 180;
		}
		
		int l = (((j >> 16) & 255) * short1) / 255;
		int i1 = (((j >> 8) & 255) * short1) / 255;
		int j1 = ((j & 255) * short1) / 255;
		
		return -16777216 | (l << 16) | (i1 << 8) | j1;
	}
	
	public static MapInfo getMap(File source) throws IOException {
		InputStream din = null;
		MapInfo mapInfo;
		
		try {
			din = new FileInputStream(source);
			mapInfo = new MapImageGen(din).readImage();
		} finally {
			if (din != null) {
				din.close();
			}
		}
		return mapInfo;
	}
}
