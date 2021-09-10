package collection;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import model.Review;

/**
 * A class to hold list of Review Objects. 
 * Also, this class Implements algorithms
 * to get users with largest number of reviews, 
 * products with highest reviews and products 
 * with highest average score.
 */

public class ReviewList {
	private Map<String, Integer> reviewListByUser = new HashMap<String, Integer>();
	private Map<String, Double> reviewListByScore = new HashMap<String, Double>();
	private Map<String, Integer> reviewListByProduct = new HashMap<String, Integer>();
	/**
	 * Adds review to the Map.
	 * 
	 * @param Review
	 * @return void
	 */
	public void addReview(Review review) {
		if(reviewListByScore.get(review.getProductId()) == null) {
			reviewListByScore.put(review.getProductId(), review.getScore());
			reviewListByProduct.put(review.getProductId(), 1);
		}
		else {
			reviewListByScore.put(review.getProductId(), reviewListByScore.get(review.getProductId()) + review.getScore());
			reviewListByProduct.put(review.getProductId(), reviewListByProduct.get(review.getProductId()) + 1);
		}
		
		if(reviewListByUser.get(review.getUserId()) == null) {
			reviewListByUser.put(review.getUserId(), 1);
		}
		else {
			reviewListByUser.put(review.getUserId(), reviewListByUser.get(review.getUserId()) + 1);
		}
	}
	
	/**
	 * Gets users with largest reviews from the Map.
	 * 
	 * @return List<String>
	 */
	public List<String> getUsersWithLargestReviews() {
		return findMax(reviewListByUser);
	}
	
	/**
	 * Gets products with highest reviews from the Map.
	 * 
	 * @return List<String>
	 */
	public List<String> getProductsWithHighestReviews() {
		return findMax(reviewListByProduct);
	}
	
	/**
	 * Gets products with highest average score from the Map.
	 * 
	 * @return List<String>
	 */
	public List<String> getProductsWithHighestAvgScore() {
		List<String> reviewList = new LinkedList<String>();
		double maxAvg = Double.MIN_VALUE;
		for(Map.Entry<String, Double> i : reviewListByScore.entrySet()) {
			double average = i.getValue() / reviewListByProduct.get(i.getKey());
			if(average > maxAvg) {
				maxAvg = average;
				reviewList = new LinkedList<String>();
				reviewList.add(i.getKey());
			}
			else if(average == maxAvg) {
				reviewList.add(i.getKey());
			}
		}
		Collections.sort(reviewList);
		return reviewList;
	}
	
	/**
	 * Gets keys with maximum value. Returns list to handle multiple values.
	 * 
	 * @return List<String>
	 */
	public List<String> findMax(Map<String, Integer> map) {
		List<String> list = new LinkedList<String>();
		int maximum = Integer.MIN_VALUE;
		for(Map.Entry<String, Integer> i : map.entrySet()) {
			if(i.getValue() > maximum) {
				maximum = i.getValue();
				list = new LinkedList<String>();
				list.add(i.getKey());
			}
			else if(i.getValue() == maximum) {
				list.add(i.getKey());
			}
		}
		Collections.sort(list);
		return list;
	}
}