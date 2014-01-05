/**
 * WordTree builds the binary tree using the Bnode class objects.
 * It contains the methods to build the binary tree for the sentence 
 * passed. It has the method to retrieve the leafnodes of a tree.
 */
package com.rohit.ner;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * @author rohit
 * 
 */
public class WordTree {
	private ArrayList<String> pivots; // contains the pivot elements that governs the generation of the word tree
	private ArrayList<String> leafNodes = new ArrayList<String>(); //stores the leaf nodes of the tree

	/**
	 * @param args
	 */
	public void setPivots() {
		/* Retrieves the pivot words from the path as specified
		 * in the properties file
		 */
		String Path = PropertyLoader.loadPivotFilePath();// sets the path of the properties file
		File file = new File(Path); // sets the file object
		this.pivots = new ArrayList<String>();// initializes the pivot elements list
		BufferedReader buffer = null;// sets the buffer reader

		try {
			buffer = new BufferedReader(new FileReader(file));
			String[] pivotData = buffer.readLine().split(",");//retrieves each comma seperated word
			Collections.addAll(this.pivots, pivotData);// copies it to the value
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				buffer.close();// closes the file
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setPivots(ArrayList<String> keys) {
		this.pivots = keys;
	}

	public Bnode getTree(String sentence) {
		/*
		 * Builds the tree from the passed sentence
		 * it uses the pivot elements to split the sentence
		 * and build it to the word tree
		 */
		Bnode root = null;

		for (String checker : pivots) {//loops over the pivot elements
			int partition;
			//check for the pivot elements in the sentence
			if (((partition = sentence.indexOf(" " + checker + " ")) > -1)
					|| (partition = sentence.indexOf(checker + " ")) == 0) {

				root = new Bnode(checker);// sets the tree node as the pivot element
				root.setLeftNode(getTree(sentence.substring(0, partition)));//sets the left reference
				//contains a recursive call that carries on the word tree generation to the left subtree
				root.setRightNode(getTree(sentence.substring(partition
						+ checker.length() + 1, sentence.length())));// sets the right reference
				//contains a recursive call that carries on the word tree generation to the right subtree
				return root;//returns the root
			}
		}
		if (sentence != null && sentence.trim() != "" && !sentence.isEmpty())
			return new Bnode(sentence.trim());// sets the leaf node
		else
			return null;//sets the child reference to null

	}

	public void setLeafNodes(Bnode root) {
		/*
		 * Retrieves the leaf nodes of the tree
		 */
		Bnode left, right;

		if ((left = root.getLeftNode()) == null
				&& (right = root.getRightNode()) == null) {
			leafNodes.add(root.getWord());// adds the detected leaf node to the variable
		}
		if ((left = root.getLeftNode()) != null) {
			setLeafNodes(left);// Propagates the search to the left subtree
		}
		if ((right = root.getRightNode()) != null) {
			setLeafNodes(right);// Propagates the search to the right subtree
		}

	}

	public ArrayList<String> getLeafNodes() {
		/*
		 * Returns the leaf nodes of the generated tree to the calling
		 * function
		 */
		return leafNodes;
	}

}
