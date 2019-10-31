package main;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

/**
Class used to create final map image from tiles
*/

public class CreateResultImg {
	private int tileSize;
	private String imgFolderName;
	
    public CreateResultImg(int tileSize, String imgFolderName) {
		this.tileSize = tileSize;
		this.imgFolderName = imgFolderName;
	}

    public void drawResultImage(int[] firstAndLastXTileNumbers, int[] firstAndLastYTileNumbers, boolean[] isFinalImgToCropXAndY) {
        BufferedImage result = new BufferedImage(
        		tileSize*(firstAndLastXTileNumbers[1]-firstAndLastXTileNumbers[0]-boolToInt(isFinalImgToCropXAndY[0])), tileSize*(firstAndLastYTileNumbers[1]-firstAndLastYTileNumbers[0]-boolToInt(isFinalImgToCropXAndY[1])),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = result.getGraphics();
        
        //cursor position for when pasting tiles to the result image
        //starts half tile off when image is to be cropped
        int xPos = 0-(tileSize*boolToInt(isFinalImgToCropXAndY[0]))/2;
        int yPos = 0-(tileSize*boolToInt(isFinalImgToCropXAndY[1]))/2;
        
        for (int i=firstAndLastXTileNumbers[0]; i<firstAndLastXTileNumbers[1]; i++) {
            for (int j=firstAndLastYTileNumbers[0]; j<firstAndLastYTileNumbers[1]; j++) {	
            	BufferedImage bi = null;
    			try {
    				bi = ImageIO.read(new File(imgFolderName+"/x"+i+"y"+j+".png"));
    			} catch (Exception e) {
    				System.out.println("Exception: " + e);
    			}
            	g.drawImage(bi, xPos+(i-firstAndLastXTileNumbers[0])*tileSize, yPos+(j-firstAndLastYTileNumbers[0])*tileSize, null);  
        	}
        }          
        try {
			ImageIO.write(result, "png", new File(imgFolderName+"/result.png"));
		} catch (Exception e) {
			System.out.println("Exception: " + e);
		}
    }
    
    private int boolToInt(boolean bool) {
    	return bool ? 1 : 0;
    }
    
}
