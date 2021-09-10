import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map.Entry;

public class AmazonReviews {

	//movies.txt: https://snap.stanford.edu/data/web-Movies.html
	
	public static void main(String[] args) {
		if(args.length != 4) {
			System.out.println("Usage: java cs601.project0.AmazonReviews -input movies.txt -output output.txt");
			System.exit(1);
		}
		HashMap<String, Product> products = new HashMap<>();
		HashMap<String, Integer> users = new HashMap<>();
		Path moviePath = Paths.get(args[1]);
		Path output = Paths.get(args[3]);
		try(
				BufferedReader br = Files.newBufferedReader(moviePath, Charset.forName("ISO-8859-1"))
				){
			//Delete the output file
			Files.deleteIfExists(output);
			
			//Read movies file to get review
			String line = "";
			while((line = br.readLine()) != null) {
				String productId = "";
				String userId = "";
				double score;
				do {

					String[] firstSplit = line.split("/",2);
					if(firstSplit.length != 0 
							&& (firstSplit[0].equals("product") || firstSplit[0].equals("review"))) {
						String[] secondSplit = firstSplit[1].split(":", 2);
						if(secondSplit[0].equals("productId")) {
							productId = secondSplit[1].trim();
						}
						if(secondSplit[0].equals("userId")) {
							userId = secondSplit[1].trim();
							if(users.get(userId) == null) {
								users.put(userId, 1);
							}
							else {
								users.put(userId, users.get(userId) + 1);
							}
						}
						if(secondSplit[0].equals("score")) {
							score = Double.parseDouble(secondSplit[1]);
							Product p = products.get(productId);
							if(p == null) {
								p = new Product(1, score);
							} else {
								p.addScore(score);
							}
							products.put(productId, p);
						}
					}
				}
				while(!(line=br.readLine()).isEmpty());
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
		
		//Get the max number of review by looping through the hashmap
		int maxReviewOfProduct = 0;
		int maxReviewOfUser = 0;
		double maxAvgScore = 0;
		for(Entry<String, Product> entry : products.entrySet()) {
			Product p = entry.getValue();
			//Update the avg score
			p.setAvgScore(p.getAvgScore() / p.getNumOfReview());
			if(p.getNumOfReview() > maxReviewOfProduct) {
				maxReviewOfProduct = p.getNumOfReview();
			}
			if(p.getAvgScore() > maxAvgScore) {
				maxAvgScore = p.getAvgScore();
			}
		}
		
		//Get the max number review by users
		for(Entry<String, Integer> entry : users.entrySet()) {
			if(entry.getValue() > maxReviewOfUser) {
				maxReviewOfUser = entry.getValue();
			}
		}
		
		//Add userId and productID base on number of review and score
		ArrayList<String> listUserByReview = new ArrayList<String>();
		ArrayList<String> listProductByReview = new ArrayList<String>();
		ArrayList<String> listProductByScore = new ArrayList<String>();
		for(Entry<String, Integer> entry : users.entrySet()) {
			if(entry.getValue() == maxReviewOfUser) {
				listUserByReview.add(entry.getKey());
			}
		}
		
		for(Entry<String, Product> entry : products.entrySet()) {
			Product p = entry.getValue();
			if(p.getNumOfReview() == maxReviewOfProduct) {
				listProductByReview.add(entry.getKey());
			}
		}
		
		for(Entry<String, Product> entry : products.entrySet()) {
			Product p = entry.getValue();
			if(p.getAvgScore() == maxAvgScore) {
				listProductByScore.add(entry.getKey());
			}
		}
		
		Collections.sort(listUserByReview);
		Collections.sort(listProductByReview);
		Collections.sort(listProductByScore);

		//Print ProductId and UserId base on review and score
		printToFile(output, "Users with largest number of reviews:\n", listUserByReview);
		printToFile(output, "Products with largest number of reviews:\n", listProductByReview);
		printToFile(output, "Products with the highest average score:\n", listProductByScore);

//		
//		try(BufferedWriter bw = Files.newBufferedWriter(output, Charset.forName("ISO-8859-1"))
//				){
//			bw.write("Users with largest number of reviews:\n");
//			for(String uI : listUserByReview) {
//				bw.write("\t" + uI + "\n");
//			}
//			
//			bw.write("Products with largest number of reviews:\n");
//			for(String pI : listProductByReview) {
//				bw.write("\t" + pI + "\n");
//			}
//
//			bw.write("Products with the highest average score:\n");
//			for(String pI : listProductByScore) {
//				bw.write("\t" + pI + "\n");
//			}
//		} catch (IOException ioe) {
//			ioe.printStackTrace();
//		}
	}	
	
	public static void printToFile(Path output, String arg, ArrayList<String> list) {
		try(	
				FileWriter fw = new FileWriter(output.toString(), true);
				BufferedWriter bw = new BufferedWriter(fw)
				){
			bw.append(arg);
			for(String str : list) {
				bw.append("\t" + str + "\n");
			}
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}
}