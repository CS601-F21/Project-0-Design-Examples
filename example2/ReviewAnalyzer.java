import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

/**
 * this class processes the input file
 *
 */
public class ReviewAnalyzer {
	private String inputpath, outputpath;
	private Map<String, Integer> userToReviewNum, productToReviewNum;
	private Map<String, Double> productToScore;
	private HashSet<String> usersWithMaxReviews, productsWithMaxReviews;
	private TreeSet<String> productsWithMaxScore;
	
	
	public ReviewAnalyzer(String inputpath, String outputpath){
		this.inputpath = inputpath;
		this.outputpath = outputpath;
		readFile(inputpath);
		processReviews();
		writeToFile(outputpath);
	}

	/**
	 * read in input file
	 * @param inputpath
	 */
	public void readFile(String inputpath) {
		userToReviewNum = new HashMap<>();
		productToReviewNum = new HashMap<>();
		productToScore = new HashMap<>();
		
		try(BufferedReader br = new BufferedReader(
				new InputStreamReader(new BufferedInputStream(new FileInputStream(
						new File(inputpath))), "ISO-8859-1"))){

			String productId = "", userId = "";
			double score = -1;
			
			for (String line = br.readLine(); line != null; line = br.readLine()) {
				if(line.startsWith("product/productId")){
					productId = line.split(" ")[1];
					if(productToReviewNum.get(productId) == null){
						productToReviewNum.put(productId, 1);
					}else{
						int previous = productToReviewNum.get(productId);
						productToReviewNum.put(productId, previous + 1);
					}
					continue;
				}
				if(line.startsWith("review/userId")){
					userId = line.split(" ")[1];
					if(userToReviewNum.get(userId) == null){
						userToReviewNum.put(userId, 1);
					}else{
						int previous = userToReviewNum.get(userId);
						userToReviewNum.put(userId, previous + 1);
					}
					continue;
				}
				if(line.startsWith("review/score")){
					score = Double.parseDouble(line.split(" ")[1]);
					if(productToScore.get(productId) == null){
						productToScore.put(productId, score);
					}else{
						//this is total score, average later
						productToScore.put(productId, productToScore.get(productId) + score);
					}
					continue;
				}
			}
		}
		catch (UnsupportedEncodingException e){
			System.out.println("Unsupported Encoding");
		}catch (IOException e) {
			e.getMessage();
		}
		
		//get average score
		for(String product : productToScore.keySet()) {
			productToScore.put(product, productToScore.get(product)/productToReviewNum.get(product));
		}
	}
	
	/**
	 * get desired info from stored reviews
	 */
	public void processReviews() {
		int maxReviews = -1;
		usersWithMaxReviews = new HashSet<>();
		for(String user : userToReviewNum.keySet()){
			if(userToReviewNum.get(user) > maxReviews){
				maxReviews = userToReviewNum.get(user);
			}
		}
		for(String user : userToReviewNum.keySet()){
			if(userToReviewNum.get(user) == maxReviews){
				usersWithMaxReviews.add(user);
			}
		}
		
		//reset maxReviews
		maxReviews = -1;
		productsWithMaxReviews = new HashSet<>();
		for(String product : productToReviewNum.keySet()){
			if(productToReviewNum.get(product) > maxReviews){
				maxReviews = productToReviewNum.get(product);
			}
		}
		for(String product : productToReviewNum.keySet()){
			if(productToReviewNum.get(product) == maxReviews){
				productsWithMaxReviews.add(product);
			}
		}

		double maxScore = -1;
		productsWithMaxScore = new TreeSet<>();
		for(String product : productToScore.keySet()){
			if(productToScore.get(product) > maxScore){
				maxScore = productToScore.get(product);
			}
		}
		for(String product : productToScore.keySet()){
			if(productToScore.get(product) == maxScore){
				productsWithMaxScore.add(product);
			}
		}
	}

	/**
	 * write result to output path
	 * @param outputpath
	 */
	public void writeToFile(String outputpath) {
		try(BufferedWriter br = Files.newBufferedWriter(Paths.get(outputpath), StandardCharsets.UTF_8)) {
			br.write("Users with largest number of reviews:\n");
			for(String user : usersWithMaxReviews) {
				br.write("        " + user + "\n");
			}
			br.write("Products with largest number of reviews:\n");
			for(String product : productsWithMaxReviews) {
				br.write("        " + product + "\n");
			}
			br.write("Products with the highest average score:\n");
			for(String product : productsWithMaxScore) {
				br.write("        " + product + "\n");
			}
			
		}catch (FileNotFoundException e){
			System.out.println("file not found");
		}catch (UnsupportedEncodingException e){
			System.out.println("unsupported encoding");
		}catch (IOException e) {
			e.printStackTrace();
		}
	}
}