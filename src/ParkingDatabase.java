package cse3461asn1;

import java.io.IOException;
import java.util.ArrayList;

public class ParkingDatabase extends Database {
	
	private ArrayList<Parking> spots = new ArrayList<Parking>();
	
	public ParkingDatabase(String file) throws IOException {
		super(file);
		
		String line = null;
	    while ((line = this.reader.readLine()) != null) {
	        String[] parts = line.split(",");
	        Parking spot = new Parking(parts[0].trim(), parts[1].trim());	        
	        spots.add(spot);
	    }
	}
	
	public Parking getParkingSpot(String num) {
		for(int i = 0; i < spots.size(); i++)
			if(spots.get(i).getNumber().equals(num))
				return spots.get(i);
		return null;
	}
	
	public Boolean saveParkingStatus(String num, String status) throws IOException {
		String line = null;
		String data = "";
		
		this.reader.reset();
		while ((line = this.reader.readLine()) != null) {
	        String[] parts = line.split(",");
	        if(parts[0] == num)
	        	data += parts[0] + "," + status;
	        else
	        	data += line;
	    }
		
		this.writer.write(data);
		return true;	
	}
}
