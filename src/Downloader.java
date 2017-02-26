import java.io.*;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Макс on 19.02.2017.
 */
public class Downloader extends Thread
{
	private int speed;
	private String source;
	private String dest;

	public Downloader(int speed, String source, String dest) throws FileNotFoundException {
		this.speed = speed;
		this.source = source;
		this.dest = dest;
	}

	public void run()
	{
		try {
			URL web = new URL(source);
			ReadableByteChannel reader = Channels.newChannel(web.openStream());
			FileOutputStream fos = new FileOutputStream(dest);
			int read;
			ByteBuffer buffer = ByteBuffer.allocate(speed);
			while ((read = reader.read(buffer)) > 0) {
				long start = System.currentTimeMillis();
				buffer.rewind();
				buffer.limit(read);
				while (read > 0)
					read -= fos.getChannel().write(buffer);
				buffer.clear();
				long time = System.currentTimeMillis() - start;
				Thread.currentThread().sleep(1000 - time);
			}
			fos.close();
			reader.close();
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
