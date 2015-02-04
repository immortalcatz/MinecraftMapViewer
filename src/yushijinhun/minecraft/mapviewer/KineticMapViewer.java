package yushijinhun.minecraft.mapviewer;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.swing.JFrame;

public class KineticMapViewer extends JFrame implements Runnable {
	
	private static final long serialVersionUID = 1L;
	
	private File renderingMap;
	private Thread renderThread;
	private MapInfo info;
	private BufferedImage buffer;
	private boolean fullScreen=false;
	private Window fullScreenWindow;
	private KeyListener keyListener;
	
	private class MapPanel extends Canvas {
		
		private static final long serialVersionUID = 1L;
		
		{
			addComponentListener(new ComponentAdapter() {
				
				@Override
				public void componentResized(ComponentEvent e) {
					if (buffer.getWidth()<getWidth()||buffer.getHeight()<getHeight()){
						buffer=getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
						System.err.println(getWidth()+","+getHeight());
					}
				}
			});
		}
		
		@Override
		public void paint(Graphics g){
			if (info==null){
				return;
			}
			
			Graphics2D g2d=buffer.createGraphics();
			g2d.setColor(Color.BLACK);
			g2d.fillRect(0, 0, buffer.getWidth(), buffer.getHeight());
			g2d.setColor(Color.WHITE);
			render(g2d);
			g.drawImage(buffer, 0, 0, null);
		}
		
		@Override
		public void repaint(){
			paint(getGraphics());
		}
		
		private void render(Graphics2D g){
			g.drawString("X中心: "+info.xCenter, 5, 10);
			g.drawString("Z中心: "+info.zCenter, 5, 25);
			g.drawString("维度: "+info.dimension, 5, 40);
			g.drawString("缩放等级: "+info.scale, 5, 55);
			g.drawImage(info.image, 5, 65, getWidth()-15, getHeight()-75, null);
		}
	}
	
	public KineticMapViewer(File renderingMap) {
		super("Minecraft Map Viewer");
		this.renderingMap=renderingMap;
		this.renderThread=new Thread(this);
		fullScreenWindow=new Window(this);
		
		
		keyListener=new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch(e.getKeyCode()){
					case KeyEvent.VK_F11:
						fullScreen=!fullScreen;
						
						if (fullScreen){
							getGraphicsConfiguration().getDevice().setFullScreenWindow(fullScreenWindow);
						}else{
							getGraphicsConfiguration().getDevice().setFullScreenWindow(null);
						}
				}
			}
		};
		
		add(new MapPanel());
		fullScreenWindow.add(new MapPanel());
		
		setSize(271, 331);
		buffer=getGraphicsConfiguration().createCompatibleImage(getWidth(), getHeight());
		
		addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				renderThread.interrupt();
			}
		});
		
		addKeyListener(keyListener);
//		fullScreenWindow.addKeyListener(keyListener);
		
		setVisible(true);
		renderThread.start();
		try {
			renderThread.join();
		} catch (InterruptedException e1) {
			// errr...
		}
	}
	
	
	
	@Override
	public void run() {
		while(true){
			try {
				info=MapImageGen.getMap(renderingMap);
			} catch (IOException e1) {
				info=null;
				e1.printStackTrace();
			}
			
			(fullScreen?fullScreenWindow:this).repaint();
			
			try {
				Thread.sleep(200);
			} catch (InterruptedException e) {
				close();
				return;
			}
		}
	}
	
	public void close(){
		dispose();
		fullScreenWindow.dispose();
	}
	
	public static void main(String[] args) {
		MapViewer.main(new String[]{"-source","d:/minecraft/ic2-experimental-server/IC2_Experimental-World/data/map_12.dat","-kinetic"});
	}
}
