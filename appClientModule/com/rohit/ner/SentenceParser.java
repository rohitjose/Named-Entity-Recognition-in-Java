/** #### MAIN CLASS ####
 * SentenceParser class contains the methods to parse the input sentence,
 * identify the entities in the sentence, build the lookup table
 * identify the possible combination of occurrences of the entities
 * and display the same
 */
package com.rohit.ner;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * @author Rohit
 * 
 */
public class SentenceParser {
	static String inputString = new String();// variable to store the input string
	static HashMap<String, String> checkWords = new HashMap<String, String>();//in memory lookup table for the name entity relations
	static HashSet<String> outputData = new HashSet<String>();//set for storing the final output
	//dictionary to store the possible entity occurences for the final output
	static HashMap<String, ArrayList<String>> keyCombinationList = new HashMap<String, ArrayList<String>>();
	static Bnode wordTreeRoot;//reference to the root node of the generated word tree for the sentence

	public static ArrayList<String> getLookupWords(String inputString) {
		//gets the possibles entities from the sentence
		WordTree wordTree = new WordTree();

		wordTree.setPivots();//sets the pivot words
		wordTreeRoot = wordTree.getTree(inputString);//sets the root of the word tree
		wordTree.setLeafNodes(wordTreeRoot);//sets the leaf nodes

		return wordTree.getLeafNodes();//returns the leaf nodes

	}

	public void setKeyCombinations(ArrayList<String> keys) {
		//sets the different key combinations available
		boolean check = true;//switch that selects the entity key list as a possible combination
		
		//loop to identify the overlapped entities a separated the combination
		for (String phrase : keys) {
			//checks the retrieved entity with the rest in the list
			for (String otherPhrase : keys) {
				if (!otherPhrase.equals(phrase) && otherPhrase.contains(phrase)) {
					check = false;//the current entity key combination, keys is not selected
					ArrayList<String> kRemovePhrase = new ArrayList<String>(
							keys);//list for the entity list without the overlapped entities
					kRemovePhrase.remove(phrase);
					setKeyCombinations(kRemovePhrase);//recursive call to continue the selection
					ArrayList<String> kRemoveOtherPhrase = new ArrayList<String>(
							keys);//list for the entity list without the overlapped entities
					kRemoveOtherPhrase.remove(otherPhrase);
					setKeyCombinations(kRemoveOtherPhrase);//recursive call to continue the selection
					break;
				}
			}
		}

		if (check) {
			keyCombinationList.put(Integer.toString(keyCombinationList.size()),
					keys);//if no overlapped entity is identified it selects the sent key combination as a possible entity combination
		}
	}

	public static void setPossibleCombinations(ArrayList<int[]> indexList,
			ArrayList<String> keyList) {
		/* Replaces the string with the passed entity key set does a lookup for the 
		   identified entity in the lookup table and replaces the combination in the 
		   input string 
		   The combination is identified from the list of integer arrays that is passed
		   and the lookup is done by indexing the entity key combination as specified
		   in the integer list 
		   */
		
		for (int[] i : indexList) {//retrieves each index list
			String tempString = inputString;//Initializes the string to be replaced
			for (int index : i) {//retrieves each index in the index list
				// replaces the entity with the entity relation combination
				tempString = tempString.replace(
						keyList.get(index),
						"{" + keyList.get(index) + ","
								+ checkWords.get(keyList.get(index)) + "}");
			}
			
			outputData.add(tempString);// adds the replaced string to the list of output strings
		}

	}

	public static void listPossibleCombinations() {
		// look to retrieve each entity key combination list from memory
		for (int i = 0; i < keyCombinationList.size(); i++) {
			ArrayList<String> Keylist = new ArrayList<String>(
					keyCombinationList.get(Integer.toString(i)));//retrieves the entity key list
			Combination combineList = new Combination();//creates a combination class

			ArrayList<int[]> integerList = combineList.setCombinations(Keylist
					.size());//gets the combination of the indexes as a list of integer arrays
			setPossibleCombinations(integerList, Keylist);//call to the setPossibleCombinations method
		}
	}

	public static void setInputString() {
		// Retrieves the user input from the console
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		System.out.print("Input String: ");
		try {
			inputString = br.readLine();
		} catch (IOException e) {
			// catch block for an IO exception
			e.printStackTrace();
		}

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// ###### MAIN CALL ######
		setInputString();//gets the input string for parsing from the user
		SentenceParser parser = new SentenceParser();// initializes the parser object

		ArrayList<String> lookupStrings = getLookupWords(inputString);//gets the words that have been selected for a lookup
	
		//loop for setting the name - entity lookup table based on the selected words
		for (String phrase : lookupStrings) {
			HashMap<String, String> lookupMap = new HashMap<String, String>(LookUp.getLookup(phrase));
			checkWords.putAll(lookupMap);//does the lookup for a combination of entities
			if((wordTreeRoot.getLeftNode()==null && wordTreeRoot.getRightNode()==null)||lookupMap.isEmpty()){
			//check to see if a further lookup is required
			for (String word : phrase.split(" ")) {
				checkWords.putAll(LookUp.getLookup(word));//does the lookup for a each word in the combination
			}
		  }
		}

		ArrayList<String> keyList = new ArrayList<String>(checkWords.keySet());//assigns the list of the identified entities
		parser.setKeyCombinations(keyList);//sets the combination of occurrence of all the keys

		listPossibleCombinations();//displays the entity combination

		//loop to display the final output
		for (String data : outputData) {
			System.out.println(data);
		}
		
		// ###### MAIN CALL END ######
	}
}
