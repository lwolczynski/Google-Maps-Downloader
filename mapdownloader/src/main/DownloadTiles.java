package main;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

/**
Class used to download Google Maps tiles
*/

public class DownloadTiles {
	private String[] urlDelimiters = {"!1i", "!2i", "!3i", "!4i", "!4e0"};
	private String imgFolderName;
	private Properties prop;
	final int TILE_SIZE = 256;
	
    public DownloadTiles(Properties prop, String imgFolderName) {
		this.prop = prop;
		this.imgFolderName = imgFolderName;
	}

	public String[] getTileUrl() {
		String url = prop.getProperty("url");
    	String startUrlStr = url.substring(0, url.indexOf(urlDelimiters[0]));
    	String endUrlStr = url.substring(url.indexOf(urlDelimiters[3]), url.indexOf(urlDelimiters[4])+urlDelimiters[4].length());
    	return new String[] {startUrlStr, endUrlStr};
    }
    
    public int[] getTileNumbers() {
    	double latitude = Double.parseDouble(prop.getProperty("latitude"));
    	double longitude = Double.parseDouble(prop.getProperty("longitude"));
    	int zoom = Integer.parseInt(prop.getProperty("zoom"));
    	
    	//in accordance to https://developers-dot-devsite-v2-prod.appspot.com/maps/documentation/javascript/examples/map-coordinates
    	
    	double siny = Math.sin(latitude * Math.PI / 180);
    	siny = Math.min(Math.max(siny, -0.9999), 0.9999);
    	double wlatitude = TILE_SIZE * (0.5 - Math.log((1 + siny) / (1 - siny)) / (4 * Math.PI));
    	double wlongitude = TILE_SIZE * (0.5 + longitude / 360);
    	
    	int scale = 1 << zoom;

    	int midTileXNumber=(int) Math.floor(wlongitude * scale / TILE_SIZE);
    	int midTileYNumber=(int) Math.floor(wlatitude * scale / TILE_SIZE);
    	
    	return new int[] {midTileXNumber, midTileYNumber};
    }
    
	public void downloadTilesImg(String startUrlStr, String endUrlStr, int[] firstAndLastXTileNumbers, int[] firstAndLastYTileNumbers) {
		int zoom = Integer.parseInt(prop.getProperty("zoom"));
		for (int i=firstAndLastXTileNumbers[0]; i<firstAndLastXTileNumbers[1]; i++) {
		    for (int j=firstAndLastYTileNumbers[0]; j<firstAndLastYTileNumbers[1]; j++) {	
			try(InputStream in = new URL(startUrlStr+urlDelimiters[0]+zoom+urlDelimiters[1]+i+urlDelimiters[2]+j+endUrlStr).openStream()){
				Files.copy(in, Paths.get(imgFolderName+"/x"+i+"y"+j+".png"));
				} catch (Exception e) {
					System.out.println("Exception: " + e);
				}
			}
		}
	}
	
	public int[] calcFirstAndLastTile(int midTileNumber, String propName) {
		int numberOfTiles = Integer.parseInt(prop.getProperty(propName));
		numberOfTiles = makeOdd(numberOfTiles);
		return new int[] {midTileNumber-((numberOfTiles-1)/2), midTileNumber-((numberOfTiles-1)/2)+numberOfTiles};
	}
	
	public boolean isEven(String propName) {
		int numberOfTiles = Integer.parseInt(prop.getProperty(propName));
		return numberOfTiles%2==0;
	}
	
	private static int makeOdd(int val) {
		if (val%2 == 0) {
			return val+1;
		} else {
			return val;
		}			
	}
	
}