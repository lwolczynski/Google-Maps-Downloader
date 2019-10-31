package main;

import java.io.IOException;
import java.util.Properties;

public class Main {
	
    public static void main(String[] args) throws IOException {
		GetPropertyValues getPropertiesValues = new GetPropertyValues();
		Properties properties = getPropertiesValues.getPropValues();
		
		CreateImgFolder createImgFolder = new CreateImgFolder();
		String imgFolderName = createImgFolder.createImgFolder();
		
		DownloadTiles downloadTiles = new DownloadTiles(properties, imgFolderName);
		String[] startAndEndUrlParts = downloadTiles.getTileUrl();
		int[] midTileNumbers = downloadTiles.getTileNumbers();

		int[] firstAndLastXTileNumbers = downloadTiles.calcFirstAndLastTile(midTileNumbers[0], "xTiles");
		int[] firstAndLastYTileNumbers = downloadTiles.calcFirstAndLastTile(midTileNumbers[1], "yTiles");
		
		downloadTiles.downloadTilesImg(startAndEndUrlParts[0], startAndEndUrlParts[1], firstAndLastXTileNumbers, firstAndLastYTileNumbers);
		
		boolean[] isFinalImgToCropXAndY = {downloadTiles.isEven("xTiles"), downloadTiles.isEven("yTiles")};
		
		CreateResultImg createResultImg = new CreateResultImg(downloadTiles.TILE_SIZE, imgFolderName);
		createResultImg.drawResultImage(firstAndLastXTileNumbers, firstAndLastYTileNumbers, isFinalImgToCropXAndY);
    }   
    
}