public class Product {
	private int numOfReview;
	private double avgScore;
	
	public int getNumOfReview() {
		return numOfReview;
	}
	public void setNumOfReview(int numOfReview) {
		this.numOfReview = numOfReview;
	}
	public double getAvgScore() {
		return avgScore;
	}
	public void setAvgScore(double avgScore) {
		this.avgScore = avgScore;
	}
	public Product(int numOfReview, double avgScore) {
		super();
		this.numOfReview = numOfReview;
		this.avgScore = avgScore;
	}
	
	//Find one more review and update the score
	public void addScore(double score) {
		this.numOfReview++;
		this.avgScore += score;
	}
	
}