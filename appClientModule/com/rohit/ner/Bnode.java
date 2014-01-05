/**
 * Bnode is a class that emulates the basic structure of a node in a binary tree
 */
package com.rohit.ner;

/**
 * @author Rohit
 *
 */
public class Bnode {
	private String word; // contains the data of the node
	private Bnode left;  // reference to the left node in the tree
	private Bnode right; // reference to the right node in the tree
	
	public Bnode(String word){
		/* Constructor to initialize the variables
		   Assigns the word as the passed data vale
		   Initializes the left and right reference as null */
		this.word = word;
		this.left=null;
		this.right=null;
	}
	
	public void setLeftNode(Bnode left){
		/* Assigns the left reference of the current node to the
		   passed value */
		this.left = left;
	}
	
	public void setRightNode(Bnode right){
		/* Assigns the right reference of the current node to the
		   passed value */
		this.right = right;
	}
	
	public Bnode getLeftNode(){
		// Returns the left node reference
		return this.left;
	}
	
	public Bnode getRightNode(){
		// Returns the right reference
		return this.right;
	}
	
	public String getWord(){
		// Returns the data value of the node
		return this.word;
	}
	

}
