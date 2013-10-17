package cse3461asn1;

public class Parking {
	
	private String spot;
	private String status;
	
	public Parking(String spot, String status) {
		this.spot = spot;
		this.status = status;
	}
	
	public String getNumber() {
		return this.spot;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public Boolean equals(Parking spot) {
		if(this == spot) 
			return true;
		if(spot.getNumber().equals(this.spot) && spot.getStatus().equals(this.status))
			return true;
		return false;
	}
	
	public String toString() {
		return "number: " + this.spot + "\nstatus: " + this.status;
	}
}
