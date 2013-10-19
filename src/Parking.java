//represents a parking spot, with a spot number and it's current status (reserved or open)
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
	
	//toString() method is also used in building records
	public String toString() {
		return this.spot + "," + this.status;
	}
}
