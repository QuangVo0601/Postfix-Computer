//These are all the imports you are allowed, don't add any more!
import java.util.Arrays;
import java.util.Scanner;
import java.io.File;
import java.io.IOException;

/**
 * The implementation of a postfix computer.
 * @author Quang Vo.
 */
class Computer {
	
	private static Node<String> front;  	// first node
	private static Node<String> back;  	// last node
	private static int size = 0;		// number of nodes in the queue
	
	/**
	 * Read a file in a scanner and create a queue of nodes.
	 * The values in the nodes are the strings read from the file.
	 * @param filename the file to be opened.
	 * @return the front of the queue of nodes with values read from the file.
	 * @throws IOException when the file can't be read or found.
	 */
	public static Node<String> fileToNodeQueue(String filename) throws IOException {
		//given a file name, open that file in a scanner and create a queue of nodes
		//the head of the queue of nodes should be the start of the queue
		//the values in the nodes should be the strings read in each time you call
		//next() on the scanner
		
		File file = new File(filename); //open the file
		
		Scanner sc = new Scanner(file); //read the file
		
		String currentString = "";
				
		while(sc.hasNext()){ //check if there are more strings in the file 
			currentString = sc.next(); //get the string
			Node<String> newNode = new Node<>(currentString); //create a node with the string
			
			if(size == 0){
				front = back = newNode; //front and back point to the same newNode
			}
			else{
				back.setNext(newNode); //link the newNode to the back
				newNode.setPrev(back);
				back = newNode; //set the back to be the new node
			}
			size++;
		}		

		sc.close(); //close the file
		
		return front; 
	}
	
	/**
	 * Process the specified number of symbols from the input queue.
	 * Update the progStack and symbols variables appropriately.
	 * @param input the queue of nodes with symbols.
	 * @param numSymbols the number of specified symbols to be processed.
	 * @return the remaining queue items.
	 */
	public Node<String> process(Node<String> input, int numSymbols) {
		//Given an input queue of symbols process the number of symbols
		//specified (numSymbols) and update the progStack and symbols
		//variables appropriately to reflect the state of the "computer"
		//(see below the "do not edit" line for these variables).
		
		//Return the remaining queue items.
		
		//For example, if input is the head of a linked list 3 -> 2 -> +
		//and numSymbols=2, you would push 3 and push 2, then return the linked
		//list with just the + node remaining.
		
		for(int i = 0; i < numSymbols; i++){
			
			//check if the input is null, then return the input
			if(input == null){ 
				return input;
			}
			
			String item = input.getValue(); //get the first node from the queue
			input = input.getNext(); //move to the next node

			//check to see if the item is an operator
			if(!checkValue(INT_OPS, ASSIGN_OPS, item) && !item.equals("print")){ 
				progStack.push(item); //push the item onto the progStack if it is not an operator					
			}
			
			//check if the item is the "=" operator
			else if(item.equals("=")){
				int value = 0;
				
				if(isInteger((String)progStack.peek())){ //check if the top of the progStack is an integer or a string
					value = Integer.parseInt((String) progStack.pop()); //get the value
				}
				else{
					value = symbols.get((String) progStack.pop()); //get the value from symbols
				}
				
				String key = (String) progStack.pop(); //get the key
				symbols.put(key, value); //put a new table entry into the symbols
			}
			
			//check if the item is the "print" operator
			else if(item.equals("print")){
				String printItem = (String) progStack.pop(); //pop one item from the progStack
				
				if(!isInteger(printItem)){ //check if the item is an integer
					int value = symbols.get(printItem); //if not an integer, get the value from the symbols using the item as the key
					printItem = Integer.toString(value); //convert the value to a string
				}
				System.out.println(printItem.toString()); //print the item 

			}
			else{
				int item1 = 0; //first operand
				int item2 = 0; //second operand
				String key = ""; 
				String result = ""; 
				int value = 0; 
				
				if(isInteger((String) progStack.peek())){ //check if the top of the progStack is an integer or a string
					item1 = Integer.parseInt((String) progStack.pop()); //get the first operand
				}
				else{
					key = (String) progStack.pop(); //get the key 
					item1 = symbols.get(key); //the first operand will be the value associated with the key in symbols
				}
				
				if(isInteger((String) progStack.peek())){ //check if the top of the progStack is an integer or a string
					item2 = Integer.parseInt((String) progStack.pop());	//get the second operand
				}
				else{
					key = (String) progStack.pop(); //get the key
					item2 = symbols.get(key); //the second operand will be the value associated with the key in symbols
				}
				
				switch(item){
					case "+": 
						int sum = item2 + item1; //when the item is "+" operator
						result = Integer.toString(sum);
						progStack.push(result); //push the result onto the stack
						break;
					case "-":
						int diff = item2 - item1; //when the item is "-" operator
						result = Integer.toString(diff);
						progStack.push(result); //push the result onto the stack
						break;
					case "*":
						int product = item2 * item1; //when the item is "*" operator
						result = Integer.toString(product);
						progStack.push(result); //push the result onto the stack
						break;
					case "/":
						int quotient = item2 / item1; //when the item is "/" operator
						result = Integer.toString(quotient);
						progStack.push(result); //push the result onto the stack
						break;
					case "+=":
						value = item2 + item1; //when the item is "+=" operator
						symbols.put(key, value); //keep the key, update the value
						break;
					case "-=":
						value = item2 - item1; //when the item is "-=" operator
						symbols.put(key, value); //keep the key, update the value
						break;
					case "*=":
						value = item2 * item1; //when the item is "*=" operator
						symbols.put(key, value); //keep the key, update the value
						break;
					case "/=":
						value = item2 / item1; //when the item is "/=" operator
						symbols.put(key, value); //keep the key, update the value
						break;
				}
			}
		}
		return input;
	}
	
	/**
	 * Check if the item is a symbol in INT_OPS or ASSIGN_OPS.
	 * @param INT_OPS the symbols ["+","-","*","/"].
	 * @param ASSIGN_OPS the symbols ["=","+=","-=","*=","/="].
	 * @param item the item to be checked.
	 * @return true if the item is one of those symbols, false otherwise.
	 */
	private static boolean checkValue(String[] INT_OPS, String [] ASSIGN_OPS, String item) {
		
		for(String s: INT_OPS){ //check in INT_OPS
			if(s.equals(item))
				return true;
		}
		
		for(String s: ASSIGN_OPS){ //check in ASSIGN_OPS
			if(s.equals(item))
				return true;
		}
		return false;
	}
	
	/**
	 * Check if the item is the type Integer.
	 * @param item the item to be checked.
	 * @return true if the item is an integer, false otherwise.
	 */
	private static boolean isInteger(String item){
		try 
        { 
            Integer.parseInt(item); //check valid integer using parseInt() method 
            return true;
        }  
        catch (NumberFormatException e)  
        { 
        	return false;
        } 
	}
	
	/**
	 *  A main method to test/demo
	 */
	public static void testMain(){
		//edit this as much as you want, if you use main without any arguments,
		//this is the method that will be run instead of the program
		System.out.println("You need to put test code in testMain() to run Computer with no parameters.");
		
		try {
			Computer.fileToNodeQueue("sample2.txt");
			new Computer().process(front, 12);
						

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	//--------------------DON'T EDIT BELOW THIS LINE--------------------
	//----------------------EXCEPT TO ADD JAVADOCS----------------------
	
	//don't edit these...
	public static final String[] INT_OPS = {"+","-","*","/"};
	public static final String[] ASSIGN_OPS = {"=","+=","-=","*=","/="};
	
	//or these...
	public ProgramStack<Object> progStack = new ProgramStack<>();
	public SymbolTable<Integer> symbols = new SymbolTable<>(5);
	
	/**
	 * The program's entry point.
	 * @param args an array of command-line arguments for the program.
	 */
	public static void main(String[] args) {
		//this is not a testing main method, so don't edit this
		//edit testMain() instead!
		
		if(args.length == 0) { 
			testMain();
			return;
		}
		
		if(args.length != 2 || !(args[1].equals("false") || args[1].equals("true"))) {
			System.out.println("Usage: java Computer [filename] [true|false]");
			System.exit(0);
		}
		
		try {
			(new Computer()).runProgram(args[0], args[1].equals("true"));
		}
		catch(IOException e) {
			System.out.println(e.toString());
			e.printStackTrace();
		}
	}
	
	//provided, don't change this
	/**
	 * Run the computer with either normal mode or debug mode.
	 * Normal mode: all operations will be done and display the output of any print statements.
	 * Debug mode: the computer will perform one "step" at a time.
	 * @param filename the file to be opened.
	 * @param debug decide whether to use debug mode or not.
	 * @throws IOException if the file can't be read or found.
	 */
	public void runProgram(String filename, boolean debug) throws IOException {
		Node<String> input = fileToNodeQueue(filename);
		System.out.println("\nProgram: " + Node.listToString(input)); //print out the queue of nodes
		
		if(!debug) { //in normal mode
			while(input != null) {
				input = process(input, 10); //process 10 symbols at a time
			}
		}
		else { //in debug mode
			Scanner s = new Scanner(System.in);
			for(int i = 1; input != null; i++) { 
				System.out.println("\n######### Step " + i + " ###############\n");
				System.out.println("----------Step Output----------");
				input = process(input, 1); //process one symbol at a time
				System.out.println("----------Symbol Table---------");
				System.out.println(symbols); //print out all the elements in symbols
				System.out.println("----------Program Stack--------");
				System.out.println(progStack); //print out all the elements in progStack
				if(input != null) {
					System.out.println("----------Program Remaining----");
					System.out.println(Node.listToString(input)); //print out the remaining in the input queue
				}
				System.out.println("\nPress Enter to Continue");
				s.nextLine();
			}
		}
	}
}