package main;

import java.util.ArrayList;
import java.util.List;

import com.mongodb.DBObject;

import draw.DrawHeatChart;
import gui.GUIController;
import mongodb.DataExtractor;
import mongodb.DatabaseManager;
import infographic.InfographicParser;
import infographic.Infographic;
import tracking.MouseTracker;
import tracking.Tracker;
import tracking.TrackerCallback;
import brainwave.BrainwaveListener;
import brainwave.BrainwaveListenerCallback;

public class Main {

	/**
	 * Main method that starts this program.
	 */
	public static void main(String[] args){
		if(Statics.showGUI){
			//Extract infographic from files (can be list in the future maybe)
    		String inputFolder = "infographic_" + Statics.infographicList.get(0).toLowerCase();
    		Statics.currentInfographicFolder = inputFolder;
			InfographicParser parser = new InfographicParser(inputFolder);
			Infographic infographic = parser.parse();
	
			DatabaseManager manager = DatabaseManager.getInstance();
			
			List<TrackerCallback> trackerCallbacks = new ArrayList<TrackerCallback>();
			trackerCallbacks.add(manager);
	
			Tracker tracker = new MouseTracker(trackerCallbacks); //Get from statics later on and change to MODUS (-> mouse/eye)
			GUIController guicontroller = new GUIController(infographic, tracker);
			
			List<BrainwaveListenerCallback> brainwaveListenerCallbacks = new ArrayList<BrainwaveListenerCallback>();
			brainwaveListenerCallbacks.add(guicontroller);
			
			// Start brainwave sensor listener
			BrainwaveListener listener = new BrainwaveListener(brainwaveListenerCallbacks); //Start listening to the brainwave sensor
			listener.startSensor();
			
			//Start GUI (takes control from now on)
			guicontroller.startGUI();	
		}
		
		else if(Statics.extractData){
			DataExtractor extractor = new DataExtractor();
			List<DBObject> sessions = extractor.extractData();
			
			DrawHeatChart drawHeatChart = new DrawHeatChart(sessions);
			drawHeatChart.makeHeatCharts();
		}
	}
}
