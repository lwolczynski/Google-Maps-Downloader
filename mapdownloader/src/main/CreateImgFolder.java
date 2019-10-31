package main;

import java.io.File;

/**
Class used to create folder for storing images
Default folder name is 'img'; if folder exists, it will try to create 'img2', 'img3', etc.
*/

public class CreateImgFolder {	
	private final String TARGET_FOLDER_NAME = "img";
    
    public String createImgFolder() {
    	String folderName = TARGET_FOLDER_NAME;
	    File folder = new File(folderName);
	    boolean isFolderCreated = false;
	    int num = 1;
	    while (!isFolderCreated) {
	    	if (!folder.exists()) {
	    		folder.mkdirs();
	    		isFolderCreated = true;
	    	} else {
	    		num++;
	    		folderName = TARGET_FOLDER_NAME+num;
	    		folder = new File(folderName);
	    	}
	    }
		return folderName;
	}
	
}