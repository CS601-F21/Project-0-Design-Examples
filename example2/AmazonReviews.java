import javax.script.SimpleBindings;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.TreeSet;

/**
 * driver
 *
 */
public class AmazonReviews {
	
	public static void main(String[] args) {
		String inputpath = "", outputpath = "";
		if(args.length != 4) {
			System.out.println("incorrect args length.");
			return;
		}
		for(int i = 0; i < args.length - 1; i++) {
			if (args[i].equals("-input")) {
				inputpath = args[i + 1];
			}
			if (args[i].equals("-output")) {
				outputpath = args[i + 1];
			}
		}
		ReviewAnalyzer ra = new ReviewAnalyzer(inputpath, outputpath);
	}
}