package cse3461asn1;

import java.io.IOException;
import java.util.ArrayList;

//the parking spot database, used to manipulate the db file
public class ParkingDatabase extends Database {
	
	//Holds the list of parking spots for easy memory access
	private ArrayList<Parking> spots = new ArrayList<Parking>();
	
	//Just need to pass in the filename
	public ParkingDatabase(String file) throws IOException {
		super(file);
		this.init();
	}
	
	//open the db file and then load all the parking spots into the array
	public void init() throws IOException {
		//we don't want to truncate the file
		super.open(false);
		
		String line = null;
	    while ((line = this.reader.readLine()) != null) {
	        String[] parts = line.split(",");
	        Parking spot = new Parking(parts[0].trim(), parts[1].trim());	        
	        spots.add(spot);
	    }
	    
	    this.close();
	}
	
	public Parking getParkingSpot(String num) {
		for(int i = 0; i < spots.size(); i++)
			if(spots.get(i).getNumber().equals(num))
				return spots.get(i);
		return null;
	}
	
	public Boolean saveParkingStatus(String num, String status) throws IOException {
		//truncate the file so we can rebuild it
		super.open(true);
		
		String data = "";
		
		System.out.println(spots.size());
		
		for(int i = 0; i < spots.size(); i++) {
	        if(spots.get(i).getNumber().equals(num))
	        	data += spots.get(i).getNumber() + "," + status;
	        else
	        	data += spots.get(i).toString();
	        
	        if(i < spots.size() - 1) 
	        	data += "\n";
	    }
		
		this.writer.write(data);
		this.close();
		
		return true;	
	}
}
