package model;

/**
 * A class to represent each Review as a 
 * structure of productId, userId and score.
 * This class also implements getter and setter
 * methods for this variables.
 *
 */

public class Review {
	private String productId;
	private String userId;
	private double score;
	
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}
}