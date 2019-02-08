/*
 * The Prompt
 * @author Jason Kok (github.com/jasonckok)
 * 
 */

import java.net.*;
import java.util.*;
import java.util.Map.Entry;
import java.io.*;
import javax.net.ssl.HttpsURLConnection;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class prompt {
	static String host = "https://api.cognitive.microsoft.com";
	static String path = "/bing/v7.0/news";

	static String articleCategory = "World";
	static String articleCount = "20";

	/*
	 * Read subscription key from api_key.txt
	 * 
	 * This was created as a way to allow the user to change the subscription key
	 * because the key that I'm using will expire in 7 days (Azure trial account)
	*/ 
	public static String readSubKeyFile () throws FileNotFoundException {
		String workingDir = System.getProperty("user.dir");
		
		File file = new File(workingDir + "\\api_key.txt");
		Scanner sc = new Scanner(file);
		
		sc.useDelimiter("\\Z");
		String subKey = sc.next();
		sc.close();
		
		return subKey;
	}
	
	/*
	 *  Get news from Bing
	 *  
	 *  Combines the parameters category and the # of articles to the URL.
	 *  Attaches the subscription key to the header before sending the request to the server.
	 */
	public static String getNews (String subKey, String articleCategory, String articleCount) throws Exception {
		// Construct URL of search request (endpoint + parameters)
		URL url = new URL(host + path + "?category=" +  URLEncoder.encode(articleCategory, "UTF-8") + "&count=" + URLEncoder.encode(articleCount, "UTF-8"));
		HttpsURLConnection connection = (HttpsURLConnection)url.openConnection();
		connection.setRequestProperty("Ocp-Apim-Subscription-Key", subKey);

		// Receive JSON body
		InputStream stream = connection.getInputStream();
		String response = new Scanner(stream).useDelimiter("\\A").next();

		stream.close();
		return response;
	}

	// Pretty-printer for JSON; uses GSON parser to parse and re-serialize
	public static String prettify(String json_text) {
		JsonParser parser = new JsonParser();
		JsonObject json = parser.parse(json_text).getAsJsonObject();
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		return gson.toJson(json);
	}

	/*
	 * Parses the response for headlines and providers
	 * 
	 * Headlines and its respective provider will be parsed from the JSON response
	 * and stored in the hashmap.
	 */
	public static Map<String, String> parseResponse(String json_response) {
		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(json_response);

		JsonObject jsonObject = jsonTree.getAsJsonObject();
		JsonArray jsonArticle = jsonObject.getAsJsonArray("value");
		
		Map<String, String> articles  = new HashMap<String, String>();

		for (int i = 0; i < jsonArticle.size(); i++) {
			String headlineKey = jsonArticle.get(i).getAsJsonObject().get("name").getAsString();
			String providerValue = jsonArticle.get(i).getAsJsonObject().get("provider").getAsJsonArray().get(0).getAsJsonObject().get("name").getAsString();
			articles.put(headlineKey, providerValue);
		}
		
		return articles;
	}
	
	/*
	 *  Print headlines
	 *  
	 *  Prints all the keys in the hashmap articles which are headlines.
	 */
	public static void printHeadlines(Map<String, String> articles) {
		for (String headline : articles.keySet()) {
			System.out.print(headline + "\r\n");
		}
	}

	/*
	 *  Print tally count of each provider
	 *  
	 *  The values in the hashmap will be checked for each provider and its count and placed in a treemap.
	 *  Then prints out to console.
	 */
	public static void printTally(Map<String, String> articles) {
		TreeMap<String, Integer> tmap = new TreeMap<String, Integer>();
		for (String t : articles.values()) {
			Integer c = tmap.get(t);
			tmap.put(t, (c == null) ? 1 : c + 1);
		}
		
		for (Entry<String, Integer> m : tmap.entrySet()) {
			System.out.print(m.getKey() + "\t" + m.getValue() + "\r\n");
		}
	}

	public static void main (String[] args) {
		try {
			System.out.println("Searching for the 20 latest World News from Bing...\n");
			
			String result = getNews(readSubKeyFile(), articleCategory, articleCount);
			Map<String, String> response = new HashMap<String, String>();
			response = parseResponse(result);
			printHeadlines(response);
			
			System.out.print("\r\n---------------------------\r\n");
			System.out.print("Provider" + "\t" + "Count \r\n");
			System.out.print("---------------------------\r\n");
			
			printTally(response);
		}
		catch (FileNotFoundException e) {
			System.out.print("The subscription key file api_key.txt is not found in the directory. Please make sure the file is in the root directory of this project.\r\n");
		}
		catch (UnknownHostException e) {
			System.out.print("The server cannot be reached. Please check your internet connection or firewall.\r\n");
		}
		catch (IOException e) {
			System.out.print("The request is unauthorized by the server. Please check that you have the correct subscription key.\r\n");
		}
		catch (Exception e) {
			System.out.print("Something went wrong. :( Please see the error below.\r\n");
			e.printStackTrace(System.out);
			System.exit(1);
		}
	}
}