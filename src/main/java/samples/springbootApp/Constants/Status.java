package samples.springbootApp.Constants;

public enum Status {

	SUCCESS(1),INVALID_REQUEST(2),INTERNAL_SERVER_ERROR(5);
	
	private int statusCode;
	
	private Status(int statusCode){
		this.statusCode = statusCode;
	}
	
	
	public int getValue(){
		return statusCode;
	}
}
