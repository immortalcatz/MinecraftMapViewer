package yushijinhun.minecraft.mapviewer;

import java.awt.image.BufferedImage;

public class MapInfo {
	
	public final int xCenter;
	public final int zCenter;
	public final int dimension;
	public final byte scale;
	public final BufferedImage image;
	
	public MapInfo(int xCenter, int zCenter, int dimension, byte scale, BufferedImage image) {
		super();
		this.xCenter = xCenter;
		this.zCenter = zCenter;
		this.dimension = dimension;
		this.scale = scale;
		this.image = image;
	}
}
