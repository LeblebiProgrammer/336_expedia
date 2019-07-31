package spack;

public class Airplane {

	public static String tailNumber = "";
	public String manufacturer = "";
	public String model = "";
	public int economy = 0;
	public int business = 0;
	public int first;
	
	public Airplane(String _tail, String _man, String _mod, int eco, int bus, int fir) {
		tailNumber = _tail;
		manufacturer = _man;
		model = _mod;
		economy = eco;
		business = bus;
		first = fir;
	}
	
	

}
