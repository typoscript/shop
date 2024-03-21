package shop;

public class FileManager {
	private static FileManager instance = new FileManager();

	private FileManager() { }
	
	public FileManager getInstance() {
		return this.instance;
	}

	public static void save() {
		
	}
	
	public static void load() {
		
	}
}
