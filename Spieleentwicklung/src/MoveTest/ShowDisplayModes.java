package MoveTest;

import java.awt.DisplayMode;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

public class ShowDisplayModes {
	public static void main(String[] args) {
		
		GraphicsEnvironment environment = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice device = environment.getDefaultScreenDevice();
        
        DisplayMode[] ds = device.getDisplayModes();
        
        for (int i = 0; i < ds.length; i++) {
			System.out.println(ds[i].getWidth()+" "+ds[i].getHeight()+" "+ds[i].getBitDepth()+" "+ds[i].getRefreshRate());
		}
	
	}
}
