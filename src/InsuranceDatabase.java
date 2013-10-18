package cse3461asn1;

import java.io.IOException;
import java.util.ArrayList;

//the insurance company database, used to manipulate the db file
public class InsuranceDatabase extends Database {
	
	//Holds the list of companies for easy memory access
	private ArrayList<Insurance> companies = new ArrayList<Insurance>();
	
	//Just need to pass in the filename
	public InsuranceDatabase(String file) throws IOException {
		super(file);
		this.init();
	}
	
	//open the db file and then load all the companies into the array
	public void init() throws IOException {
		//we don't want to truncate the file
		super.open(false);
		
		String line = null;
	    while ((line = this.reader.readLine()) != null) {
	        Insurance company = new Insurance(line.trim());
	        companies.add(company);
	    }
	    
	    this.reader.reset();
	}
	
	//return a shallow copy of the companies array
	public ArrayList<Insurance> getCompanies() {
		ArrayList<Insurance> copy = new ArrayList<Insurance>();
		copy.addAll(companies);
		return copy;
	}
}
