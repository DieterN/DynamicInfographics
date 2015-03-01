package infographic;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

public class Infographic {

	private List<MainPart> mainParts;
	private List<ExtraPart> extraParts;
	
	public Infographic(List<MainPart> mainParts, List<ExtraPart> extraParts){
		this.mainParts = mainParts;
		this.extraParts = extraParts;
	}

	public List<MainPart> getMainParts() {
		return mainParts;
	}

	public void setGraphicParts(List<MainPart> mainParts) {
		this.mainParts = mainParts;
	}

	public List<ExtraPart> getExtraParts() {
		return extraParts;
	}

	public void setExtraParts(List<ExtraPart> extraParts) {
		this.extraParts = extraParts;
	}

	public int getInfographicImageType(){
		return mainParts.get(0).getType();
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

	private int getInfographicHeight() {
		int height = 0;
		for(MainPart part: mainParts){
			height += part.getImageHeight();
		}
		return height;
	}
	
}
