import java.nio.file.Path;
import java.util.ArrayList;

public class AmazonReviews {

	//movies.txt: https://snap.stanford.edu/data/web-Movies.html	
	private static ArrayList<String> usersWithHighestReviews = new ArrayList<String>();
	private static ArrayList<String> productsWithHighestReviews = new ArrayList<String>();
	private static ArrayList<String> productsWithHighestAvgScore = new ArrayList<String>();	
	
	public static void main(String[] args) {
		FileOperations fileOperations = new FileOperations();
		OutputComputations output = new OutputComputations();
		ArrayList<FileData> fileInputData = fileOperations.readFile (args[1]);
		usersWithHighestReviews = output.getUsersWithHighestReviews (fileInputData);
		productsWithHighestReviews = output.getProductsWithHighestReviews (fileInputData);
		productsWithHighestAvgScore = output.getProductsWithHighestAvgScore (fileInputData);
		Path path = fileOperations.createFile (args[1], args[3]);
		fileOperations.writeToFile (path, usersWithHighestReviews, productsWithHighestReviews, productsWithHighestAvgScore);
	}		
	
}
