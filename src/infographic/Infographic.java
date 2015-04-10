package infographic;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;
import java.util.UUID;

public class Infographic {

	private List<MainPart> mainParts = new ArrayList<MainPart>();
	private Map<UUID,MainPart> mappedMainParts = new HashMap<UUID,MainPart>();
	
	public Infographic(List<MainPart> mainParts){
		this.mainParts = mainParts;
		for(MainPart part: mainParts){
			mappedMainParts.putAll(part.getMapWithSelfAndChildren());
		}
	}

	public List<MainPart> getMainParts() {
		return mainParts;
	}

	public void setGraphicParts(List<MainPart> mainParts) {
		this.mainParts = mainParts;
	}

	public Map<UUID, MainPart> getMappedMainParts() {
		return mappedMainParts;
	}
	
	public MainPart getMainPartWithId(UUID id){
		return mappedMainParts.get(id);
	}

	public int getInfographicImageType(){
		return mainParts.get(0).getType();
	}
	
	public TreeSet<LeafMainPart> getAllLeafs(){
		TreeSet<LeafMainPart> result = new TreeSet<LeafMainPart>();
		for(MainPart part: mappedMainParts.values()){
			if(part instanceof LeafMainPart){
				result.add((LeafMainPart) part);
			}
		}
		return result;
	}
	
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
//		int height = 0;
//		for(MainPart part: mainParts){
//			height += part.getImageHeight();
//		}
//		return height;
	}
	
	public UUID getIDofPartAt(double width, double height){
		UUID id = null;
		for(MainPart part: mainParts){
			int topLeftCornerY = part.getTopLeftCornerY();
			if(topLeftCornerY <= height && ((topLeftCornerY + part.getImageHeight()) > height)){
				id = part.getIdOfMainPartAt(width);
				break; //(return previous part ID)
			}
		}
		return id;
	}
	
}
