
package copyAndMove;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;

public class CopyAndMove {

	// public static String outPath = null;
	// public static String filePath = "example.txt";
	public static byte[] buf = new byte[100];
	public static byte[] data = null;
	public static int dataIdx = 0;

	public static void write(byte[] bs, String outPath) {
		// RandomAccessFile f = new RandomAccessFile(outPath, "w");
		try {
			RandomAccessFile f = new RandomAccessFile(outPath, "rw");
			f.write(bs);
			f.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	public static void read(String filePath) throws FileNotFoundException, IOException {

		RandomAccessFile f = new RandomAccessFile(filePath, "r");
		data = new byte[(int) f.length()];
		while (true) {
			int nBytes = f.read(buf);
			if (nBytes == -1) {// read returns -1 when end of file
				break;
			}
			for (int i = 0; i < nBytes; i++) {
				data[dataIdx] = buf[i];
				dataIdx++;
			}
			f.close();
		}
		Charset cs = Charset.forName("UTF-8");
		String s = new String(data, cs);
		System.out.println(s);
	}

	public static void main(String[] args) {
		String fileIn = null;
		String fileOut = null;

		if (args.length == 2) {
			fileIn = args[0];
			fileOut = args[1];
		}
		else{ System.out.println("Please specify two parameters, Inputfile and OutputFile");}
			
		try {
			CopyAndMove.read(fileIn);
			CopyAndMove.write(data, fileOut);
		} catch (FileNotFoundException ex) {
			System.out.println("File was not found, please verify " + fileIn + " exists.");
		} catch (IOException ex) {
				System.out.println(ex);
		}
	}
}