/**
 *
 * Contains fields for storing relevant information when reading the input text file
 */
public class FileData {
	private String userID;
	private String productID;
	private double score;
	
	public String getUserID() {
		return userID;
	}
	public void setUserID(String value) {
		userID = value;
	}
	public String getProductID() {
		return productID;
	}
	public void setProductID(String value) {
		productID = value;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double value) {
		score = value;
	}
	
}