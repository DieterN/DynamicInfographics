package infographic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

/**
 * Class representing an infographic.
 * An infographic contains mainParts and extra parts.
 * The extra parts can be extracted out of the mainparts.
 * 
 * @author Dieter
 *
 */
public class Infographic {

	private List<MainPart> mainParts = new ArrayList<MainPart>();
	private Map<UUID,MainPart> mappedMainParts = new HashMap<UUID,MainPart>();
	
	/**
	 * Create a new infographic.
	 * 
	 * @param mainParts: mainParts that make up this infographic together
	 */
	public Infographic(List<MainPart> mainParts){
		this.mainParts = mainParts;
		for(MainPart part: mainParts){
			mappedMainParts.putAll(part.getMapWithSelfAndChildren());
		}
	}

	/**
	 * Gets the main parts.
	 *
	 * @return the main parts
	 */
	public List<MainPart> getMainParts() {
		return mainParts;
	}

	/**
	 * Sets the main parts.
	 *
	 * @param mainParts: the new main parts
	 */
	public void setMainParts(List<MainPart> mainParts) {
		this.mainParts = mainParts;
	}
	
	/**
	 * Return the MainPart with the given ID.
	 * 
	 * @param id: ID of the requested mainPart
	 * @return MainPart with the given ID.
	 */
	public MainPart getMainPartWithId(UUID id){
		return mappedMainParts.get(id);
	}

	/**
	 * Gets the infographic image type.
	 *
	 * @return the infographic image type
	 */
	public int getInfographicImageType(){
		return mainParts.get(0).getType();
	}
	
	/**
	 * Get all LeafMainParts of this infographic.
	 *
	 * @return the LeafMainParts of this infographic.
	 */
	public TreeSet<LeafMainPart> getAllLeafs(){
		TreeSet<LeafMainPart> result = new TreeSet<LeafMainPart>();
		for(MainPart part: mappedMainParts.values()){
			if(part instanceof LeafMainPart){
				result.add((LeafMainPart) part);
			}
		}
		return result;
	}
	
	/**
	 * Gets the complete infographic image.
	 *
	 * @return the complete infographic image.
	 */
	public BufferedImage getImage() {
		MainPart first = mainParts.get(0);
		
        //Initializing the final image
        BufferedImage finalImg = new BufferedImage(first.getImageWidth(), getInfographicHeight(), first.getBimg().getType());
		
        int height = 0;
		for(MainPart mainPart: mainParts){
			finalImg.createGraphics().drawImage(mainPart.getBimg(), 0, height, null);
			height += mainPart.getImageHeight();
		}
		
//        System.out.println("Image concatenated.....");
//        try {
//			ImageIO.write(finalImg, "jpeg", new File("finalImg.jpg"));
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		return finalImg;

	}

	/**
	 * Gets the infographic height. This is the sum of the height of all MainParts.
	 * For the last part though, the highest of the mainPart and the ExtraPart is returned.
	 *
	 * @return the height of this infographic
	 */
	public int getInfographicHeight() {
		MainPart lastPart = mainParts.get(mainParts.size()-1);
		int height = lastPart.getTopLeftCornerY();
		ExtraPart extraPart = lastPart.getHighestChild();
		if(extraPart != null){
			height +=  Math.max(lastPart.getImageHeight(), lastPart.getHighestChild().getImageHeight()); 
		}
		else{
			height += lastPart.getImageHeight();
		}
		return height;
	}
	
	/**
	 * Return the id of the MainPart at the given coordinates.
	 * 
	 * @param x: x-coordinate of the searched MainPart
	 * @param y: y-coordinate of the searched MainPart
	 * @return ID of the MainPart at the given coordinates. 
	 */
	public UUID getIDofPartAt(double x, double y){
		UUID id = null;
		for(MainPart part: mainParts){
			int topLeftCornerY = part.getTopLeftCornerY();
			if(topLeftCornerY <= y && ((topLeftCornerY + part.getImageHeight()) > y)){
				id = part.getIdOfMainPartAt(x);
				break;
			}
		}
		return id;
	}
	
}
