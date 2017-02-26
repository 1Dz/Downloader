import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * Created by Макс on 19.02.2017.
 */
public class Main {

	public static void main(String[] args) {
		try{
			Object[] input = input(args);
			Map<String, String> fileList = getFiles((String) input[2]);
			ExecutorService service = Executors.newFixedThreadPool((Integer) input[0]);
			for (Map.Entry<String, String> entry : fileList.entrySet()) {
				System.out.println("Downloading from: " + entry.getValue());
				service.submit(new Downloader((Integer) input[1], entry.getValue(), (String) input[3] + entry.getKey()));
			}
			service.shutdown();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	private static Map<String, String> getFiles(String source) throws IOException {
		BufferedReader fileReader = new BufferedReader(new FileReader(source));
		Map<String, String> map = new HashMap<>();
		while (fileReader.ready())
		{
			String[] res = fileReader.readLine().split(" ");
			map.put(res[1], res[0]);
		}
		return map;
	}
	private static Object[] input(String[] args) throws IOException {
		Object[] input = new Object[4];
		int channels = Integer.parseInt(args[1]);
		System.out.println("Channels quantity: " + channels);

		String speedString = args[3];
		int speed = 0;
		if(speedString.contains("k"))
			speed = (Integer.parseInt(speedString.replaceAll("k", "")) * 1000) / channels;
		if(speedString.contains("m"))
			speed = (Integer.parseInt(speedString.replaceAll("m", "")) * 1000000) / channels;
		System.out.println("Speed is set to: " + speedString);
		System.out.println("Speed for each channel is set to: " + speed/1000 + "k");

		String source = args[7];
		System.out.println("Links source is: " + source);

		String dest = args[5];
		System.out.println("Destination folder is set to: " + dest);

		input[0] = channels;
		input[1] = speed;
		input[2] = source;
		input[3] = dest;

		return input;
	}
}
