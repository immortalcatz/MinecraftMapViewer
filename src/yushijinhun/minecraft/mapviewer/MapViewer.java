package yushijinhun.minecraft.mapviewer;

import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class MapViewer {
	
	public static void main(String[] args) {
		int errorlevel;
		
		if (args.length == 0) {
			errorlevel = MapViewer.guiMode();
		} else {
			errorlevel = MapViewer.commandMode(args);
		}
		
		System.exit(errorlevel);
	}
	
	public static int commandMode(String[] args) {
		boolean argHelp = false;
		boolean argInfo = false;
		boolean argGuiInfo = false;
		boolean argWrite = false;
		boolean argKinetic=false;
		String argSource = null;
		String argOutput = null;
		
		for (int i = 0; i < args.length; i++) {
			switch (args[i]) {
				case "-help":
					argHelp = true;
					break;
				
				case "-source":
					i++;
					if (i >= args.length) {
						System.err.println("非法参数。使用-help查看帮助。");
						return 254;
					}
					
					argSource = args[i];
					break;
				
				case "-info":
					argInfo = true;
					break;
				
				case "-write":
					argWrite = true;
					i++;
					if (i >= args.length) {
						System.err.println("非法参数。使用-help查看帮助。");
						return 254;
					}
					
					argOutput = args[i];
					break;
					
				case "-guiinfo":
					argGuiInfo=true;
					break;
					
				case "-kinetic":
					argKinetic=true;
					break;
				
				default:
					System.err.println("非法参数。使用-help查看帮助。");
					return 254;
			}
		}
		
		if (argHelp) {
			System.out.println("用法：");
			System.out.println("mapviewer -help");
			System.out.println("mapviewer <-source sourcefile> [-info] [-guiinfo] [-write outputfile] [-kinetic]");
			System.out.println("");
			System.out.println("\t-help    \t显示帮助文档");
			System.out.println("\t-source  \t表示从sourcefile中读取地图数据");
			System.out.println("\t-info    \t输出地图信息");
			System.out.println("\t-guiinfo \t以GUI模式输出地图信息");
			System.out.println("\t-write   \t将地图以PNG格式输出到outputfile");
			System.out.println("\t-kinetic \t动态地图查看器");
			System.out.println("");
			System.out.println("ERRORLEVEL数值：");
			System.out.println("\t0\t正常退出");
			System.out.println("\t1\t发生了IO异常");
			System.out.println("\t254\t所给的参数不正确");
			System.out.println("\t255\t用户取消了操作");
			return 0;
		}
		
		File sourceFile = new File(argSource);
		MapInfo info;
		try {
			info = MapImageGen.getMap(sourceFile);
		} catch (IOException e) {
			System.err.println("在读取地图数据时发生了IO异常。");
			e.printStackTrace();
			return 1;
		}
		
		if (argInfo) {
			System.out.println("x中心=" + info.xCenter);
			System.out.println("z中心=" + info.zCenter);
			System.out.println("维度=" + info.dimension);
			System.out.println("缩放级别=" + info.scale + "");
		}
		
		if (argGuiInfo){
			JOptionPane.showMessageDialog(null, "地图信息：\n" + "x中心=" + info.xCenter + "\n" + "z中心=" + info.zCenter + "\n" + "维度=" + info.dimension + "\n" + "缩放级别=" + info.scale, "Minecraft Map Viewer", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(info.image));
		}
		
		if (argWrite) {
			try {
				ImageIO.write(info.image, "png", new File(argOutput));
			} catch (IOException e) {
				System.err.println("在保存地图图像时发生了IO异常。");
				e.printStackTrace();
			}
		}
		
		if (argKinetic){
			new KineticMapViewer(sourceFile);
		}
		
		return 0;
	}
	
	public static int guiMode() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		
		File source;
		chooser.setDialogTitle("选择要读取的地图数据文件");
		
		chooser.showOpenDialog(null);
		source = chooser.getSelectedFile();
		if (source == null) {
			JOptionPane.showMessageDialog(null, "你没有选择任何文件。", "Minecraft Map Viewer", JOptionPane.WARNING_MESSAGE);
			return 255;
		}
		
		MapInfo info;
		try {
			info = MapImageGen.getMap(source);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "在读取地图时发生了IO异常。", "Minecraft Map Viewer", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return 1;
		}
		int value = JOptionPane.showConfirmDialog(null, "地图信息：\n" + "x中心=" + info.xCenter + "\n" + "z中心=" + info.zCenter + "\n" + "维度=" + info.dimension + "\n" + "缩放级别=" + info.scale + "\n\n" + "是否保存此地图？", "Minecraft Map Viewer", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, new ImageIcon(info.image));
		
		if ((value == JOptionPane.NO_OPTION) || (value == JOptionPane.CLOSED_OPTION)) {
			return 255;
		}
		
		File output;
		chooser.setDialogTitle("选择保存路径");
		chooser.setSelectedFile(null);
		
		chooser.showSaveDialog(null);
		output = chooser.getSelectedFile();
		if (output == null) {
			JOptionPane.showMessageDialog(null, "你没有选择任何文件。", "Minecraft Map Viewer", JOptionPane.WARNING_MESSAGE);
			return 255;
		}
		
		try {
			ImageIO.write(info.image, "png", output);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "在保存地图时发生了IO异常。", "Minecraft Map Viewer", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
			return 1;
		}
		
		JOptionPane.showMessageDialog(null, "地图已保存在 " + output.getPath() + "", "Minecraft Map Viewer", JOptionPane.INFORMATION_MESSAGE);
		return 0;
	}
}
