package draw;

import infographic.Infographic;
import infographic.InfographicParser;

import java.awt.Color;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import main.Statics;

import org.tc33.jheatchart.HeatChart;

import com.mongodb.DBObject;

/**
 * Class responsible for drawing heatcharts. This class requires a list of DBObjects (sessions) to work properly.
 * 
 * @author Dieter
 *
 */
public class DrawHeatChart {
	
	private List<DBObject> sessions = new ArrayList<DBObject>();
	
	/**
	 * Constructor for a DrawHeatChart object, this requires a list of DBobjects
	 * 
	 * @param sessions: list of DBObject that contains one session per experiment
	 */
	public DrawHeatChart(List<DBObject> sessions){
		this.sessions = sessions;
	}
		
	/**
	 * Call this method to draw heatcharts for all sessions in the sessions field.
	 * The output of this method can be controlled by adjusting some parameters in the Statics class.
	 */
	public void makeHeatCharts() {
		for(DBObject session: sessions){
			Infographic infographic = extractInfographic(session);
			String infographicName = (String) session.get("infographic");
			BufferedImage bimg = infographic.getImage();
			
			String name = (String) session.get("name");
			 
			for(String eSense: Statics.eSensesToExtract){
				
				double[][] data = createMatrix(session, bimg, eSense);
				if(Statics.absoluteHeatChart){
					data[0][1] = 1;
					data[1][0] = 100;
				}
				makeHeatChart(data, name, infographicName, eSense, bimg);
			}
			
		}
	}

	private void makeHeatChart(double[][] data, String name, String infographicName, String eSenseName, BufferedImage infographicBimg) {
		double[][] averagedData = computeAveragedMatrix(data);
		HeatChart map = new HeatChart(averagedData);
		map.setTitle("This is my heat chart title");
		map.setXAxisLabel("X Axis");
		map.setYAxisLabel("Y Axis");
		map.setShowXAxisValues(false);
		map.setShowYAxisValues(false);
		map.setHighValueColour(Color.RED);
		map.setLowValueColour(Color.YELLOW);
			
		String fileName = Statics.heatChartsFolder + name + "_" + infographicName + "_" + eSenseName + ".jpg";
		
		try {
			map.saveToFile(new File(fileName));
			BufferedImage chartBimg = readImageFromDirectory(fileName);
			BufferedImage scaledInfographic = scaleImage(0.5, 0.5, infographicBimg);
			double chartScaleX = scaledInfographic.getWidth()/ (double) chartBimg.getWidth();
			double chartScaleY = scaledInfographic.getHeight()/ (double) chartBimg.getHeight();
			BufferedImage scaledChart = scaleImage(chartScaleX, chartScaleY, chartBimg);
			BufferedImage finalImg = new BufferedImage(scaledChart.getWidth()+scaledInfographic.getWidth(), Math.max(scaledChart.getHeight(),scaledInfographic.getHeight()), scaledInfographic.getType());
			finalImg.createGraphics().drawImage(scaledInfographic, 0, 0, null);
			finalImg.createGraphics().drawImage(scaledChart, scaledInfographic.getWidth(), 0, null);
			File outputfile = new File(fileName);
		    ImageIO.write(finalImg, "jpg", outputfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Processed " + infographicName + " from " + name + " for " + eSenseName);
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
	
	private BufferedImage scaleImage(double scaleX, double scaleY, BufferedImage before){
		int w = (int) (before.getWidth()*scaleX);
		int h = (int) (before.getHeight()*scaleY);
		BufferedImage after = new BufferedImage(w, h, before.getType());
		AffineTransform at = new AffineTransform();
		at.scale(scaleX, scaleY);
		AffineTransformOp scaleOp = new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
		return scaleOp.filter(before, after);	
	}
	
	private Infographic extractInfographic(DBObject measurement){
		String folder = (String) measurement.get("infographic");
		InfographicParser parser = new InfographicParser(folder);
		Infographic infographic = parser.parse();
		
		return infographic;
	}

	private double[][] createMatrix(DBObject session, BufferedImage bimg, String dataString) {		
		@SuppressWarnings("unchecked")
		List<DBObject> measurements = (List<DBObject>) session.get("measurements");	
		
		double[][] data = new double[bimg.getHeight()/5][bimg.getWidth()/5];
		
		for(DBObject m : measurements){
			int measuredValue = 0;
			measuredValue = extractMeasuredValue(dataString, m);
			DBObject position = (DBObject) m.get("Position");			
			int x = (int) position.get("xPos")/5;
			int y = (int) position.get("yPos")/5;
			if(x < data[0].length && x >= 0 && y < data.length && y >= 0){
				data[y][x] = measuredValue;
			}
		}
		return data;
	}

	private int extractMeasuredValue(String dataString, DBObject m) {
		int measuredValue = -1;
		DBObject eSense = (DBObject) m.get("ESenseData");
		measuredValue = (int) eSense.get(dataString);
		
		return measuredValue;
	}
	
	private double[][] computeAveragedMatrix(double[][] data) {	
		double[][] averagedData = new double[data.length][data[0].length];
//		System.out.println("Start");
		for (int i = 0; i < data.length; i++) {
            for (int j = 0; j < data[i].length; j++) {
            	if(data[i][j] == 0){
//            		averagedData[i][j] = cellNeighborsAverage(data, i, j, 1);
            		averagedData[i][j] = optimalCellNeighboursAverage(data, i, j, 1, 0, 0, 0);
            	}
            	else
            		averagedData[i][j] = data[i][j];
            }
        }
//		System.out.println("Stop");
		return averagedData;
	}
	
	private double optimalCellNeighboursAverage(double[][] data, int i, int j, int k, double sum, double count, int nbCounted){

        for (int x = i - k; x <= i + k; x += 2*k) {
            for (int y = j - k; y <= j + k; y++) {
                if (x >= 0 && y >= 0 && x < data.length && y < data[i].length) {
                	if(data[x][y] != 0){
                		int diff = Math.abs(x-i) + Math.abs(y-j);
                		sum += data[x][y]/diff;
                		count += 1.0/diff;
                		nbCounted++;
                	}
                }
            }
        }
        
        for (int y = j - k; y <= j + k; y += 2*k) {
            for (int x = i - k; x <= i + k; x++) {
                if (x >= 0 && y >= 0 && x < data.length && y < data[i].length) {
                	if(data[x][y] != 0){
                		int diff = Math.abs(x-i) + Math.abs(y-j);
                		sum += data[x][y]/diff;
                		count += 1.0/diff;
                		nbCounted++;
                	}
                }
            }
        }
        
        if(nbCounted >= 5){
        	return sum/count;
        }
        else{
        	return optimalCellNeighboursAverage(data, i, j, k+1, sum, count, nbCounted);
        }
    }
	
//	private static double cellNeighborsAverage(double[][] data, int i, int j, int k){
//		double averaged = 0;
//		double sum = 0;
//		double count = 0;
//        for (int x = Math.max(0, i - k); x <= Math.min(i + k, data.length); x++) {
//            for (int y = Math.max(0, j - k); y <= Math.min(j + k, data[i].length); y++) {
//                if (x >= 0 && y >= 0 && x < data.length && y < data[i].length) {
//                    if(x!=i || y!=j){
//                    	if(data[x][y] != 0){
//                    		int diff = Math.abs(x-i) + Math.abs(y-j);
//                    		sum += data[x][y]/diff;
//                    		count += 1.0/diff;
//                    	}
//                    }
//                }
//            }
//        }
//        if(count > 0)
//        	averaged = sum/count;
////        else if(k < 15)
//        else
//        	averaged = cellNeighborsAverage(data, i, j, k+1);
//		return averaged;
//    }
}
