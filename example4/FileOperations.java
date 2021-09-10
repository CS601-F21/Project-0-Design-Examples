import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

/**
 *
 * Contains methods for performing file processing operations.
 * Methods contained are for reading from, creating and writing to a file 
 */
public class FileOperations {
	
	/**
	 * Reads from a file in the project directory given the file name, and returns an ArrayList of FileData object, which contains
	 * the userID, productID and reviewScore of each record set in the file
	 * 
	 * @param fileName - expects the name of the input file
	 * @return fileInputData - this is an ArrayList of FileData Object
	 * 
	 */
	public ArrayList<FileData> readFile (String fileName)
	{
		ArrayList<FileData> fileInputData = new ArrayList<FileData>();
		Charset charset = Charset.forName("ISO-8859-1");
		Path path = Paths.get (fileName);
		try (BufferedReader reader = Files.newBufferedReader (path, charset))
		{	
			FileData data = new FileData();
			String line = null;
			while((line = reader.readLine()) != null)
			{
				
				if(line.startsWith ("product/productId"))
					data.setProductID (line.split(":")[1].trim());
				else if (line.startsWith ("review/userId"))
					data.setUserID (line.split(":")[1].trim());
				else if(line.startsWith ("review/score")) //average score is the last element I need in each iteration. Once it's gotten I can store data for the iteration 
				{
					data.setScore (Double.valueOf(line.split(":")[1].trim()));
					fileInputData.add (data);
					data = new FileData(); //clear data entity here, so a new one can be populated in next iteration
				}
					
				
			}
		}
		catch(Exception ex)
		{
			System.out.print((ex.getMessage()));
		}
		return fileInputData;
	}
	
	/**
	 * Creates the output file in the same directory as the file specified in the first parameter of the method.
	 * Doesn't create the file if a file with same name already exists
	 * 
	 * @param existingFileInDirectory - accepts name of current file in directory
	 * @param nameOfFileToCreate - the name of the output file that will be created.
	 * @return
	 */
	public Path createFile (String existingFileInDirectory, String nameOfFileToCreate)
	{
		Path fullPath = Paths.get (existingFileInDirectory).toAbsolutePath();
		String filePathString = fullPath.toString().replace (existingFileInDirectory, nameOfFileToCreate);
		Path filePath = Paths.get (filePathString);
		try
		{
			if(Files.exists (filePath))
			{
				return filePath;
			}
			Files.createFile (filePath);
		}
		catch (IOException e)
		{
			System.out.println (String.format ("Error creating file {0}. Msg: {1}",nameOfFileToCreate, e.getMessage()));
		}
		return filePath;
	}
	
	/**
	 * writes output to the txt file based on format specified in assignment
	 * 
	 * @param filePath - write path for the txt file
	 * @param usersWithHighestReviews - collection of userIDs with highest reviews
	 * @param productsWithHighestReviews - collection of productIDs with highest reviews
	 * @param productsWithHighestAvgScore - collection of productIDs with highest average score
	 */
	public void writeToFile (Path filePath, ArrayList<String> usersWithHighestReviews, ArrayList<String> productsWithHighestReviews, ArrayList<String> productsWithHighestAvgScore)
	{
		Charset charset = Charset.forName("ISO-8859-1");
		try (BufferedWriter writer = Files.newBufferedWriter (filePath, charset)){
			writer.write ("Users with largest number of reviews:");
			for (String user : usersWithHighestReviews)
			{
				writer.newLine();
				writer.write ("\t"+user);
			}
			writer.newLine();
			writer.write ("Products with largest number of reviews:");
			for (String product : productsWithHighestReviews)
			{
				writer.newLine();
				writer.write ("\t"+product);
			}
			writer.newLine();
			writer.write ("Products with the highest average score:");
			for (String product : productsWithHighestAvgScore)
			{
				writer.newLine();
				writer.write ("\t"+product);
			}
		}
		catch (IOException e)
		{
			System.out.println( String.format ("Error writing to file in path {0}. Msg: {1}",filePath.toString(), e.getMessage()));
		}
	}
}