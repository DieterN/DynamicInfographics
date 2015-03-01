package infographic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 
 * Class that parses the infographic from a directory and returns an infographic object
 * 
 * @author Dieter
 *
 */
public class InfographicParser {

	private String inputFolder;
	
	public InfographicParser(String inputFolder){
		this.inputFolder = inputFolder;
	}
	
	public Infographic parse(){
		List<MainPart> mainParts = new ArrayList<MainPart>();
		List<ExtraPart> extraParts = new ArrayList<ExtraPart>();
		
		File folder = new File(inputFolder);
		
		// Get all subfolders, they should be in the order of the infographic.
		// Each folder should contain 1 or 2 files: the first file is the main part, the (optional) second part is the extra part
		for (final File subFolder : folder.listFiles()) {
			MainPart mainPart = null;
			ExtraPart extraPart = null;
			
			if(!subFolder.isDirectory()){
				System.out.println("Folder is no directory - should not happen");
			} else {
				int count = 0;
				for (final File fileEntry : subFolder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			            //should not happen
			        	System.out.println("Folder in folder - should not happen");
			        } else {
			        	BufferedImage bimg = readImageFromDirectory(subFolder+"\\"+fileEntry.getName());
			            if(count == 0) //read MainPart
			            	mainPart =  new MainPart(bimg);
			            else //read ExtraPart
			            	extraPart = new ExtraPart(bimg);
		            	count++;
			        }
			    }
			}
			if(extraPart != null){
				mainPart.setChild(extraPart);
				extraPart.setParent(mainPart);
				extraParts.add(extraPart);
			}
			mainParts.add(mainPart);
		}
		return new Infographic(mainParts, extraParts);
	}

	private BufferedImage readImageFromDirectory(String absolutePath) {
		BufferedImage bimg = null;
		try {
			bimg = ImageIO.read(new File(absolutePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bimg;
	}
	
	public String getInputFolder() {
		return inputFolder;
	}

	public void setInputFolder(String inputFolder) {
		this.inputFolder = inputFolder;
	}
}