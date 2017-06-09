
package copyAndMove;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyAndMove {

	// public static String outPath = null;
	// public static String filePath = "example.txt";
	public static byte[] buf = new byte[100];
	public static byte[] data = null;
	public static int dataIdx = 0;

	public static void write(byte[] bs, String outPath) {
		// RandomAccessFile f = new RandomAccessFile(outPath, "w");

		int fileIdx = (outPath.lastIndexOf("/") + 1);
		String fileName = outPath.substring(fileIdx);
		String dirPath = outPath.substring(0, fileIdx);
		System.out.println(fileName);
		System.out.println(dirPath);
		Path path = Paths.get(dirPath);
		if (!Files.exists(path)) {
			try {
				Files.createDirectories(path);
			} catch (IOException ex) {
				System.out.println("Exception creating directories " + dirPath);
			}
		}
		System.out.println(path.toString());

		try {
			RandomAccessFile f = new RandomAccessFile(outPath, "rw");
			f.write(bs);
			f.close();
		} catch (IOException ex) {
			System.out.println(ex);
		}
	}

	public static void delete(String filePath) {
		Path fileToDeletePath = Paths.get(filePath);
		System.out.println("Deleting file " + fileToDeletePath);
		if (Files.exists(fileToDeletePath)) {
			try {
				Files.delete(fileToDeletePath);
			} catch (IOException ex) {
				System.out.println("Exception deleting file " + fileToDeletePath);
			}
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

		}
		f.close();
		Charset cs = Charset.forName("UTF-8");
		String s = new String(data, cs);
		System.out.println(s);
	}

	public static void main(String[] args) {
		String fileIn = null;
		String fileOut = null;
		String command = "cp";

		if (args.length == 3) {
			command =args[0];
			System.out.println("command = " + command);
			fileIn = args[1];
			fileOut = args[2];

			try {
				CopyAndMove.read(fileIn);
				CopyAndMove.write(data, fileOut);
				System.out.println("command IS '" + command + "'");
				if (command.equals("mv")) {
					// delete the old file
					System.out.println("command == mv" );
					CopyAndMove.delete(fileIn);
				}
			} catch (FileNotFoundException ex) {
				System.out.println("File was not found, please verify " + fileIn + " exists.");
			} catch (IOException ex) {
				System.out.println(ex);
			}

		} else {
			System.out.println("Please specify three parameters, cp or mv, then Inputfile and OutputFile");
		}
	}
}