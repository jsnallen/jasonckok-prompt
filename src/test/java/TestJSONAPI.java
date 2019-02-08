import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.jupiter.api.Test;

public class TestJSONAPI {
	// Test to see if server is reachable (without a subscription key)
	@Test
	public void testAPIServerReachable() {
		String url = "https://api.cognitive.microsoft.com/bing/v7.0/news";
		try {
			HttpURLConnection connection = (HttpURLConnection) new URL(url).openConnection();
			int responseCode = connection.getResponseCode();
			assertTrue(responseCode == HttpURLConnection.HTTP_UNAUTHORIZED);	// Unauthorize response code for when trying to access the api server without a subscription key
		} catch (IOException e) {
			fail(e.toString());
		}
	}

	// Test if subscription key is valid
	@Test
	public void testSubKeyValid() {
		try {
			prompt.getNews(prompt.readSubKeyFile(), "World", "20");
		}
		catch (Exception e) {
			fail(e.toString());
		}
	}
}
