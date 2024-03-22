package shop;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;

public class FileManager {
	private static final String FILE_NAME = "data.txt";
	private static FileWriter fw;
	private static FileReader fr;
	private static BufferedReader br;

	private FileManager() { }
	
	public static void save(String data) {
		try {
			fw = new FileWriter(FILE_NAME);

			fw.write(data);
			fw.close();
		} catch (Exception e) {
			System.out.println("파일 저장 실패");
			return;
		}
		
		System.out.println("파일 저장 성공");
	}
	
	public static String load() {
		String data = "";

		try {
			fr = new FileReader(FILE_NAME);
			br = new BufferedReader(fr);
			
			while (br.ready())
				data += br.readLine() + "\n";
			
			br.close();
			fr.close();
		} catch (Exception e) {
			System.out.println("파일 로드 실패");
			return "";
		}
		
		return data;
	}
}
