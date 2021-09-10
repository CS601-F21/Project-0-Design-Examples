import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;

/**
 *
 * Contains methods for getting processing the input data and getting the desired output. 
 */
public class OutputComputations {
	
	private HashMap<String,ArrayList<FileData>> groupedFileData;
	private HashMap<String,ArrayList<Double>> groupedFileDatabyScore;
	private HashMap<Integer,ArrayList<String>> groupedDataByUserReviewCount = new HashMap<Integer,ArrayList<String>>();
	private HashMap<Integer,ArrayList<String>> groupedDataByProductReviewCount = new HashMap<Integer,ArrayList<String>>();;
	private HashMap<Double,ArrayList<String>> groupedDataByAverageReviewScore = new HashMap<Double,ArrayList<String>>();;
	private ArrayList<String> outputValues;
	
	/**
	 * Traverses the ArrayList containing the FileData gotten from the input, and returns an ArrayList of userIDs with the highest reviews
	 * 
	 * @param fileDataList -  the ArrayList containing the FileData gotten from the input
	 *  @return outputValues - the ArrayList of strings containing userIDs with highest review count
	 */
	public ArrayList<String> getUsersWithHighestReviews (ArrayList<FileData> fileDataList)
	{
		outputValues = new ArrayList<String>();
		groupedFileData = new HashMap<String, ArrayList<FileData>>();
		for (FileData theData: fileDataList)
		{
			if(!groupedFileData.containsKey (theData.getUserID()))
				groupedFileData.put (theData.getUserID(), new ArrayList<FileData>());
			groupedFileData.get (theData.getUserID()).add(theData);
		}
		for (String userID: groupedFileData.keySet())
		{
			int userReviewCount = groupedFileData.get (userID).size();
			if(!groupedDataByUserReviewCount.containsKey (userReviewCount))
				groupedDataByUserReviewCount.put (userReviewCount, new ArrayList<String>());
			groupedDataByUserReviewCount.get (userReviewCount).add(userID);			
		}
		Integer [] userReviewCountArray = new Integer [groupedDataByUserReviewCount.size()];
		userReviewCountArray = groupedDataByUserReviewCount.keySet().toArray (userReviewCountArray);
		Arrays.sort(userReviewCountArray, Collections.reverseOrder());
		int maximumUserReviewCount = userReviewCountArray[0];
		outputValues = groupedDataByUserReviewCount.get (maximumUserReviewCount);
		Collections.sort (outputValues);	//sorting in Lexicographical order
		return outputValues;
	}
	
	/**
	 * Traverses the ArrayList containing the FileData gotten from the input, and returns an ArrayList of productIDs with the highest reviews
	 * 
	 * @param fileDataList -  the ArrayList containing the FileData gotten from the input
	 * @return outputValues - the ArrayList of strings containing userIDs with highest review count
	 */
	public ArrayList<String> getProductsWithHighestReviews (ArrayList<FileData> fileDataList)
	{
		outputValues = new ArrayList<String>();
		groupedFileData = new HashMap<String, ArrayList<FileData>>();
		for (FileData theData: fileDataList)
		{
			if(!groupedFileData.containsKey (theData.getProductID()))
				groupedFileData.put (theData.getProductID(), new ArrayList<FileData>());
			groupedFileData.get (theData.getProductID()).add(theData);
		}		
		for (String productID: groupedFileData.keySet())
		{
			int productReviewCount = groupedFileData.get (productID).size();
			if(!groupedDataByProductReviewCount.containsKey (productReviewCount))
				groupedDataByProductReviewCount.put (productReviewCount, new ArrayList<String>());
			groupedDataByProductReviewCount.get (productReviewCount).add(productID);			
		}
		Integer [] productReviewCountArray = new Integer [groupedDataByProductReviewCount.size()];
		productReviewCountArray = groupedDataByProductReviewCount.keySet().toArray (productReviewCountArray);
		Arrays.sort(productReviewCountArray, Collections.reverseOrder());
		int maximumProductReviewCount = productReviewCountArray[0];
		outputValues = groupedDataByProductReviewCount.get (maximumProductReviewCount);
		Collections.sort (outputValues);	//sorting in Lexicographical order
		return outputValues;
	}	
	
	/**
	 * Traverses the ArrayList containing the FileData gotten from the input, and returns an ArrayList of productIDs with the highest average scores
	 * 
	 * @param fileDataList -  the ArrayList containing the FileData gotten from the input
	 * @return outputValues - the ArrayList of strings containing userIDs with highest review count
	 */
	public ArrayList<String> getProductsWithHighestAvgScore (ArrayList<FileData> fileDataList)
	{
		outputValues = new ArrayList<String>();
		groupedFileDatabyScore = new HashMap<String, ArrayList<Double>>();
		for(FileData theData: fileDataList)
		{
			if(!groupedFileDatabyScore.containsKey (theData.getProductID()))
				groupedFileDatabyScore.put (theData.getProductID(), new ArrayList<Double>());
			groupedFileDatabyScore.get (theData.getProductID()).add(theData.getScore());
		}
		for(String productID: groupedFileDatabyScore.keySet())
		{	
			//https://www.oracle.com/technetwork/articles/java/ma14-java-se-8-streams-2177646.html
			//Learnt about using streams to perform computations on collections from above web link
			double averageReviewScore = groupedFileDatabyScore.get(productID).stream().mapToDouble(Double :: doubleValue).average().getAsDouble();
			if(!groupedDataByAverageReviewScore.containsKey (averageReviewScore))
				groupedDataByAverageReviewScore.put (averageReviewScore, new ArrayList<String>());
			groupedDataByAverageReviewScore.get (averageReviewScore).add(productID);	
		}
		double highestAvgScore =  groupedDataByAverageReviewScore.keySet().stream().mapToDouble(Double :: doubleValue).max().getAsDouble();
		outputValues = groupedDataByAverageReviewScore.get (highestAvgScore);
		Collections.sort (outputValues);	//sorting in Lexicographical order
		return outputValues;
	}
}