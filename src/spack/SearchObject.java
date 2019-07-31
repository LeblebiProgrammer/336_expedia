package spack;

import java.util.ArrayList;

public class SearchObject {

	private String origin;
	private String destination;
	private String onDate;
	private String returnDate;
	private boolean oneWay;
	private boolean roundTrip;
	
	//private ArrayList<String>results;
	
	public SearchObject(String _origin, String _destination, String _onDate, String _returnDate, boolean _oneWay, boolean _roundTrip) {
		this.setOrigin(_origin);
		this.setDestination(_destination);
		this.setOnDate(_onDate);
		this.setReturnDate(_returnDate);
		this.setOneWay(_oneWay);
		this.setRoundTrip(_roundTrip);
	}
	
	public void setOrigin(String _origin) {
		this.origin = _origin;
	}
	
	public void setDestination(String _destination) {
		this.destination = _destination;
	}
	
	public void setOnDate(String _date) {
		this.onDate = _date;
	}
	
	public void setReturnDate(String _date) {
		this.returnDate = _date;
	}
	
	public void setOneWay(boolean _variable) {
		this.oneWay = _variable;
	}
	
	public void setRoundTrip(boolean _variable) {
		this.roundTrip = _variable;
	}
	
	public String getOrigin() {
		return origin;
	}
	
	public String getDestionation() {
		return destination;
	}
	public String getOnDate() {
		return onDate;
	}
	public String getReturnDate() {
		return returnDate;
	}
	public boolean getOneWay() {
		return oneWay;
	}
	public boolean getRoundTrip() {
		return roundTrip;
	}
	
	
}
