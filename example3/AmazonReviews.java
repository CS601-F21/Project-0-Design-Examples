import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import collection.ReviewList;
import model.Review;

/**
 * AmazonReviews is a Driver Class. It helps in reading
 * and writing to file.
 *
 */

public class AmazonReviews {

	//movies.txt: https://snap.stanford.edu/data/web-Movies.html
	
	/**
	 * Main logic to create input/output objects and write to output file.
	 * 
	 * Expected usage:
	 * 	java cs601.project0.AmazonReviews -input <input file name> -output <output file name>
	 *
	 * Example:
	 * 	java cs601.project0.AmazonReviews -input movies.txt -output output.txt
	 * 
	 * @param args
	 * @throws IOException 
	 * @return void
	 */
	
	public static void main(String[] args) throws IOException {
		Charset charset = StandardCharsets.ISO_8859_1;
		Path inputFile = Paths.get(args[1]);
		Path outputFile = Paths.get(args[3]);
		BufferedReader in = Files.newBufferedReader(inputFile, charset);
		BufferedWriter out = Files.newBufferedWriter(outputFile);
		ReviewList reviews = convertToReviewList(in);
		//System.out.println(reviews.getAverageRatingOf("B000CEVCD8"));
		
		out.write("Users with largest number of reviews:\n");
		for(String i : reviews.getUsersWithLargestReviews()) {
			out.write("\t" + i + "\n");
		}
		
		out.write("Products with largest number of reviews:\n");
		for(String i : reviews.getProductsWithHighestReviews()) {
			out.write("\t" + i + "\n");
		}
		
		out.write("Products with the highest average score:\n");
		for(String i : reviews.getProductsWithHighestAvgScore()) {
			out.write("\t" + i + "\n");
		}
		
		out.close();
		in.close();
	}
	
	/**
	 * Converts input to ReviewList.
	 * 
	 * @param BufferedReader
	 * @throws IOException
	 * @return ReviewList
	 */
	private static ReviewList convertToReviewList(BufferedReader in) throws IOException {
		ReviewList reviews = new ReviewList();
		Review review = new Review();
		String reviewRaw;
		while((reviewRaw = in.readLine()) != null) {
			if(reviewRaw.startsWith("product/productId: ")) {
				review.setProductId(reviewRaw.split("product/productId: ")[1]);
			}
			else if(reviewRaw.startsWith("review/userId: ")) {
				review.setUserId(reviewRaw.split("review/userId: ")[1]);
			}
			else if(reviewRaw.startsWith("review/score: ")) {
				review.setScore(Double.parseDouble(reviewRaw.split("review/score: ")[1]));
				reviews.addReview(review);
				review = new Review();
			}
		}
		return reviews;
	}	
}