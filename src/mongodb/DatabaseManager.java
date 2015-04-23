package mongodb;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import main.Statics;

import com.mongodb.BasicDBObject;
import com.mongodb.BasicDBObjectBuilder;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;

import dataTypes.SensorDataPacket;
import tracking.TrackerCallback;

/**
 * Class responsible for storing data in the MongoDB.
 * 
 * @author Dieter
 *
 */
public class DatabaseManager implements TrackerCallback{

	private static DatabaseManager instance = null;
	private static DB db;
	private static List<DBObject> measurements = new ArrayList<DBObject>();
	
	private DatabaseManager(){
		connectToDatabase();
	}
	
	public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
	
	private void connectToDatabase(){	  	
        try{   
		   // To connect to mongodb server
           MongoClient mongoClient = new MongoClient( "localhost" , 27017 );
           // Now connect to your databases
           db = mongoClient.getDB(Statics.DBName);
		   System.out.println("Connected to database");	
		  
        }catch(Exception e){
	       System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	    }       
	}
	
	public static void storeDataInDB() {
		DBCollection sessions = null;
		DBObject options = BasicDBObjectBuilder.start().get();
		if(db.collectionExists("sessions")){
			sessions = db.getCollection("sessions");
		}
		else{
			sessions =  db.createCollection("sessions", options);		
		}		
		BasicDBObject session = new BasicDBObject("SID", Statics.SID)
		.append("name", Statics.reader_name)
		.append("infographic", Statics.inputFolder)
		.append("measurements", measurements);
		sessions.insert(session);
		
        Statics.SID = "not_reading";
        measurements.clear();
	}

	@Override
	public void sendSensorDataPacket(SensorDataPacket packet) {	
		java.util.Date date = new java.util.Date();
		Timestamp timestamp = new Timestamp(date.getTime());
		
		BasicDBObject measurement = new BasicDBObject("timestamp", timestamp)
        .append("ESenseData", new BasicDBObject("attention", packet.getAttentionValue()).append("meditation", packet.getMeditationValue())
        		.append("delta", packet.getDelta()).append("theta", packet.getTheta())
        		.append("lowAlpha", packet.getLowAlpha()).append("highAlpha", packet.getHighAlpha())
        		.append("lowBeta", packet.getLowBeta()).append("highBeta", packet.getHighBeta())
        		.append("lowGamma", packet.getLowGamma()).append("highGamma", packet.getHighGamma()))
        .append("Position", new BasicDBObject("xPos", packet.getPosition().x).append("yPos", packet.getPosition().y));
		
		measurements.add(measurement);
	}

}
