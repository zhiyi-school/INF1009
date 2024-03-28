package GameLayer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

public class languageFactory {
	private static Random rand = randomSingleton.getInstance();
	
	// Whenever this class is called, it will do the following methods
	public languageFactory(){
		
		this.loadLetters();
		this.loadWords();
		this.mapLetterToImg();
	}
	
	private int numOfwords;
	private String selectedWord,eachLetter;

	// Create a list of words
	private ArrayList<String> wordList = new ArrayList<String>();
	
	// Create a list of letters
	private ArrayList<String> letterList = new ArrayList<String>();
	
	// Create a list of image names, e.g 'A.png' file
	private ArrayList<String> allImageList = new ArrayList<String>();
	
	// This HashMap takes the 'A' as key and the image file name 'A.png'
	private HashMap<String, String> letterToImage = new HashMap<String, String>();
	
	// This ArrayList is to return user
	private ArrayList<String> imageList = new ArrayList<String>();

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
                	allImageList.add("letters_img/"+file.name());
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
	public ArrayList<String> getallImageList()
	{
		return allImageList;
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
			letterToImage.put(letterList.get(x), allImageList.get(x));
		}
	}
	
	// This function randomly generates word
	public String generateWord() 
	{
		if (wordList.isEmpty()) {
			return "List is empty";
		}
		
		// create instance of Random class
		numOfwords = wordList.size();

		// Random generate a number, this becomes position index of the List
		// If numOfwords = 5, then only random choose from 0-4, so need add 1
		int randIndex = rand.nextInt(numOfwords+1);
		selectedWord = wordList.get(randIndex);
		return selectedWord;
	}
	public ArrayList<String> spawnLetters(String randWord)
	{
		ArrayList<String> correctLetterList = new ArrayList<String>();
		ArrayList<String> wrongLetterList = new ArrayList<String>();
		String imgFilePath;
		imageList.removeAll(imageList);
		
		// For each letter in the word, put the image file path into an ArrayList
		for(int x=0;x<randWord.length();x++)
		{
			eachLetter = randWord.substring(x, x + 1);
			imgFilePath = imageFilePath(eachLetter);
			correctLetterList.add(imgFilePath);
		}
		imageList.addAll(correctLetterList);
		
		// For each letter not in the word, put the image into wrong letter ArrayList
		for(int x=0;x<allImageList.size();x++)
		{
			imgFilePath = allImageList.get(x);
			if(correctLetterList.contains(imgFilePath)==false)
			{
				wrongLetterList.add(imgFilePath);
			}
			else
			{
				continue;
			}
			
		}
		// create instance of Random class
		int randIndex;
		
		// Random pick any 5 letters from wrong letter list to confuse player
		for(int x=0;x<5;x++)
		{
			String selectedLetter;
			randIndex=rand.nextInt(wrongLetterList.size());
			selectedLetter = wrongLetterList.get(randIndex);
			imageList.add(selectedLetter);
		}
		return imageList;
	}
}
