package util;

import java.awt.Toolkit;

import javax.swing.ImageIcon;
public class ImageLoader {
	
	static Toolkit TK = Toolkit.getDefaultToolkit();
	
	public static ImageIcon loadImageIcon(String _file) {
		return new ImageIcon(ImageLoader.class.getResource("/images/"+_file));
	}
}
