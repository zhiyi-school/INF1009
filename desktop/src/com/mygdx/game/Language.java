package GameLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;


public class Language {
	
	// Whenever this class is called, it will do the following methods
	public Language(){
		
		this.loadLetters();
		this.loadWords();
		this.mapLetterToImg();
	}
	
	private int numOfwords;
	private String selectedWord;

	// Create a list of words
	private ArrayList<String> wordList = new ArrayList<String>();
	
	// Create a list of letters
	private ArrayList<String> letterList = new ArrayList<String>();
	
	// Create a list of image names, e.g 'A.png' file
	private ArrayList<String> imageFileList = new ArrayList<String>();
	
	// This HashMap takes the 'A' as key and the image file name 'A.png'
	private HashMap<String, String> letterToImage = new HashMap<String, String>();

	public void loadLetters()
	{
		// Get a handle to the directory containing the PNG files
        FileHandle directoryHandle = Gdx.files.internal("letters_img");

        // Check if the directory exists
        if (directoryHandle.isDirectory()) 
        {
            // Get an array of all files in the directory
            FileHandle[] files = directoryHandle.list();

            // Loop through each file in the directory
            for (FileHandle file : files) {
                // Check if the file is a PNG file
                if (file.extension().equalsIgnoreCase("png")) {
                    // Add the file name to the ArrayList
                	letterList.add(file.nameWithoutExtension());
                	imageFileList.add("letters_img/"+file.name());
                }
            }

        } 
        else {
            System.err.println("Directory not found: letters_img");
        }
	}
	public void loadWords()
	{
        // Get a handle to the file
        FileHandle fileHandle = Gdx.files.internal("words/words.txt");

        // Check if the file exists
        if (fileHandle.exists()) {
            String fileContent = fileHandle.readString(); // Read the entire file as a string
            String[] words = fileContent.split("\\r?\\n"); // Split the string by newline characters

            // Add each word to the ArrayList
            for (String word : words) {
                wordList.add(word);
            }
        }
        else {
            System.err.println("File not found: words.txt");
        }
        
	}
	public ArrayList<String> getWordList()
	{
		return wordList;
	}
	public ArrayList<String> getLetterList()
	{
		return letterList;
	}
	public ArrayList<String> getImageFileList()
	{
		return imageFileList;
	}
	
	public HashMap<String, String> getLetterToImage()
	{
		return letterToImage;
	}
	
	// This function helps you find the image file path of the letter
	public String imageFilePath(String letter)
	{
		// Get the value associated with the key "C"
        String filePath = letterToImage.get(letter);
        return filePath;
	}
	// Map each Letter to the Image file and store in Hashmap
	public void mapLetterToImg()
	{
		for(int x=0;x<letterList.size();x++)
		{
			letterToImage.put(letterList.get(x), imageFileList.get(x));
		}
	}
	
	// This function randomly generates word
	public String generateWord() 
	{
		if (wordList.isEmpty()) {
			return "List is empty";
		}
		
		// create instance of Random class
		Random rand = new Random();
		numOfwords = wordList.size();

		// Random generate a number, this becomes position index of the List
		// If numOfwords = 5, then only random choose from 0-4, so need add 1
		int randIndex=rand.nextInt(numOfwords+1);
		selectedWord = wordList.get(randIndex);
		return selectedWord;
	}
	
}
