package infographic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

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
		
		File folder = new File(inputFolder);
		
		int partHeight = 0;
		
		// Get all subfolders, they should be in the order of the infographic.
		// Each folder should contain 1 or 2 files: the first file is the main part, the (optional) second part is the extra part
		// MainParts can be split in two again, by adding another folder
		for (final File subFolder : folder.listFiles()) {
			MainPart mainPart = null;
			
			if(!subFolder.isDirectory()){
				System.out.println("SubFolder is no directory - should not happen");
			} else {
				int count = 0;
				for (final File fileEntry : subFolder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			            mainPart = extractCompositeMainPart(subFolder, partHeight);
		            	partHeight += mainPart.getImageHeight();
			            break; //break because you're going to extract all files from this subfolder in a separate method
			        } else {
			        	BufferedImage bimg = readImageFromDirectory(subFolder+"\\"+fileEntry.getName());
			            if(count == 0){ //read MainPart
			            	mainPart =  new LeafMainPart(bimg, 0, partHeight);
			            	partHeight += bimg.getHeight();
			            }
			            else{ //read ExtraPart
			            	ExtraPart extraPart = new ExtraPart(bimg);
							((LeafMainPart) mainPart).setChild(extraPart);
							extraPart.setParent(mainPart);
			            }
		            	count++;
			        }
			    }
			}
			mainParts.add(mainPart);
		}
		return new Infographic(mainParts);
	}

	private CompositeMainPart extractCompositeMainPart(File subFolder, int partHeight) {
		TreeSet<MainPart> subParts = new TreeSet<MainPart>();
		List<BufferedImage> images = new ArrayList<BufferedImage>();

		int partWidth = 0;
		
		for (final File subsubFolder : subFolder.listFiles()) {
			MainPart subPart = null;
			
			if(!subsubFolder.isDirectory()){
				System.out.println("SubSubFolder is no directory - should not happen");
			} else {
				int count = 0;
				for (final File fileEntry : subsubFolder.listFiles()) {
			        if (fileEntry.isDirectory()) {
			        	System.out.println("Shouldn't come here in this version");
//			            subPart = extractCompositeMainPart(subsubFolder, partHeight);
//			            break; //break because you're going to extract all files from this subfolder in a separate method
			        } else {
			        	BufferedImage bimg = readImageFromDirectory(subsubFolder+"\\"+fileEntry.getName());
			            if(count == 0){ //read MainPart
			            	images.add(bimg);
			            	subPart =  new LeafMainPart(bimg, partWidth, partHeight);
			            	partWidth += subPart.getImageWidth();
			            }
			            else{ //read ExtraPart
			            	ExtraPart extraPart = new ExtraPart(bimg);
							((LeafMainPart) subPart).setChild(extraPart);
							extraPart.setParent(subPart);
			            }
		            	count++;
			        }
			    }
			}
			subParts.add(subPart);
	    }
		BufferedImage composedBimg = composeImagesIntoOneImageHorizontally(images);
		CompositeMainPart compositePart = new CompositeMainPart(composedBimg, 0, partHeight);
		compositePart.setSubParts(subParts);
		return compositePart;
	}

	private BufferedImage composeImagesIntoOneImageHorizontally(List<BufferedImage> images) {
		//HORIZONTAL COMPOSE
		int width = 0;
		int maxHeight = 0;
		
		for(BufferedImage bimg : images){
			width += bimg.getWidth();
			maxHeight = Math.max(bimg.getHeight(), maxHeight);
		}
		
        //Initializing the final image
        BufferedImage finalImg = new BufferedImage(width, maxHeight, images.get(0).getType());
		
        int drawWidth = 0;
		for(BufferedImage bimg : images){
			finalImg.createGraphics().drawImage(bimg, drawWidth, 0, null);
			drawWidth += bimg.getWidth();
		}
		
		return finalImg;
	}

//	private BufferedImage composeImagesIntoOneImageVertically(List<BufferedImage> images) {
//		//VERTICAL COMPOSE
//		int maxWidth = 0;
//		int height = 0;
//		
//		for(BufferedImage bimg : images){
//			maxWidth = Math.max(bimg.getWidth(), maxWidth);
//			height += bimg.getHeight();
//		}
//		
//        //Initializing the final image
//        BufferedImage finalImg = new BufferedImage(maxWidth, height, images.get(0).getType());
//		
//        int drawHeight = 0;
//		for(BufferedImage bimg : images){
//			finalImg.createGraphics().drawImage(bimg, 0, drawHeight, null);
//			drawHeight += bimg.getHeight();
//		}
//		return finalImg;
//	}

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