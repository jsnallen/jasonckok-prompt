import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.junit.jupiter.api.Test;

public class TestSubKeyFile {

	String workingDir = System.getProperty("user.dir");
	File file = new File(workingDir + "/api_key.txt");
	
	// Test for api_key.txt is in project directory
	@Test
	public void testFileExists() {
		assertTrue(file.exists());
	}
	
	// Test to see if api_key.txt is empty
	@Test
	public void testFileEmpty() {
		String subKey = "";
		try {
			Scanner sc = new Scanner(file);
			sc.useDelimiter("\\Z");
			subKey = sc.next();
			sc.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		
		assertFalse(subKey.isEmpty());
	}
}
