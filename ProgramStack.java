import java.util.Iterator;

/**
 * Linked list implementation of the stack.
 * @author Quang Vo
 * @param <T> the type of the value in the ProgramStack.
 */
class ProgramStack<T> implements Iterable<T> {
	// You'll want some instance variables here
	private Node<T> top;
	private int size;	
	
	/**
	 * Construct an empty stack.
	 */
	public ProgramStack() {
		//setup what you need	
		top = null;
		size = 0;
	}
	
	/**
	 * Insert a new item onto the stack.
	 * @param item the item to insert.
	 */
	public void push(T item) {
		//push an item onto the stack
		//you may assume the item is not null
		//O(1)
		
		Node<T> newNode = new Node<T>(item); //create a new node with the item
		
		//link the nodes
		newNode.setNext(top);
		if(size() != 0){
			top.setPrev(newNode);
		}
		
		top = newNode; //set the newNode to be the top
		size++;
	}
	
	/**
	 * Remove the most recently inserted item from the stack.
	 * @return the removed item.
	 * @return null if the stack is empty.
	 */
	public T pop() {
		//pop an item off the stack
		//if there are no items on the stack, return null
		//O(1)
		
		//replace this dummy return statement
		
		if(isEmpty()){ //check if the stack is empty
			return null;
		}
		else{
			T toReturn = top.getValue(); 
			top = top.getNext(); //set top to be the next item
			size--;
			if(size != 0){
				top.setPrev(null);
			}
			return toReturn; //return the old value
		}
		
	}
	
	/**
	 * Get the most recently inserted item in the stack.
	 * Do not remove the item from the stack.
	 * @return the most recently inserted item in the stack.
	 * @return null if the stack is empty.
	 */
	public T peek() {
		//return the top of the stack (but don't remove it)
		//if there are no items on the stack, return null
		//O(1)
		
		//replace this dummy return statement
		
		if(isEmpty()){ //check if the stack is empty
			return null;
		}
		else{
			return top.getValue();
		}
	}

	/**
	 * Create a string representation of the stack, items are separated by a space.
	 * The top is shown to the right, the bottom to the left.
	 * @return the string representation of the stack.
	 */
	public String toString() {
		//Create a string of the stack where each item
		//is separated by a space. The top of the stack
		//should be shown to the right and the bottom of
		//the stack on the left.
		
		//Hint: Reuse the provided code from another class
		//instead of writing this yourself!
		
		//O(n)
		
		//replace this dummy return statement
		
		if(size != 0){
			return Node.listToStringBackward(top); //reuse the code from Node.java
		}
		else{
			return Node.listToString(top);
		}
	}
	
	/**
	 * Remove everything from the stack.
	 */
	public void clear() {
		//remove everything from the stack
		//O(1)
		
		top = null;
		size = 0;
	}
	
	/**
	 * Return the number of items in the stack.
	 * @return the size of the stack.
	 */
	public int size() {
		//return the number of items on the stack
		//O(1)
		
		//replace this dummy return statement
		
		return size;
	}
	
	/**
	 * Check if the stack is logically empty.
	 * @return true if empty, false otherwise.
	 */
	public boolean isEmpty() {
		//return whether or not the stack is empty
		//O(1)
		
		//replace this dummy return statement
		
		if(size() == 0){ //if the stack is empty
			return true;
		}
		else{
			return false;
		}
	}
	
	/**
	 * Create an array representation of the stack.
	 * The top of the stack is the element 0.
	 * @return the array representation.
	 */
	@SuppressWarnings("unchecked")
	public Object[] toArray() {
		//Return an array representation of the stack.
		//The top of the stack should be element 0
		//in the array.
		
		//O(n)
		
		//replace this dummy return statement
		
		T[] stackArray = (T[]) new Object[size]; //create an array of objects
		
		Node<T> current = top; 
		
		for(int i = 0; i < size(); i++){
			stackArray[i] = current.getValue(); //add values to the array
			current = current.getNext(); //move to the next node 
		}
		
		return stackArray;
	}
	
	/**
	 * Obtain an Iterator object used to traverse from the top to the bottom of the stack.
	 * @return an iterator positioned prior to the top element. 
	 */
	public Iterator<T> iterator() {
		return new StackIterator();
	}
		//Return an iterator that traverses from
		//the top of the stack to the bottom of
		//the stack.
		
		//The iterator's hasNext() and next() methods
		//must both be O(1)
		
		//next() should throw a NullPointerException
		//if you try to use next when there are no
		//more items
		
		//replace this dummy return statement
		
	//	return new Iterator<T>(){
	/**
	 * This is the implementation of the StackIterator.
	 */
	private class StackIterator implements Iterator<T>{
			Node<T> current = top;
			
			/**
			 * Test if there are more items in the stack.
			 * @return true if there are more items in the stack.
			 */
			public boolean hasNext(){
				return current!=null;
			}
			
			/**
			 * Obtain the next item in the stack.
			 * @return the next item in the stack.
			 * @throws NullPointerException if there are no more items.
			 */
			public T next(){
				if(!hasNext()){ //check if there are more items in the stack
					throw new NullPointerException("No more items");
				}
				T toReturn = current.getValue();
				current = current.getNext(); //move to the next node
				return toReturn; //return the item
			}
		}
		
		
	
	
	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */
	public static void main(String[] args) {
		ProgramStack<String> s1 = new ProgramStack<>();
		s1.push("a");
		s1.push("b");
				
		ProgramStack<Integer> s2 = new ProgramStack<>();
		s2.push(1);
		s2.push(2);
		s2.push(3);
		
		//System.out.println(s1.toString());
		//System.out.println(s1.toArray().length);
		
		if(s1.toString().equals("a b") && s1.toArray()[0].equals("b") && s1.toArray()[1].equals("a") && s1.toArray().length == 2) {
			System.out.println("Yay 1");
		}
		
		if(s1.peek().equals("b") && s2.peek().equals(3) && s1.size() == 2 && s2.size() == 3) {
			System.out.println("Yay 2");
		}
		
		if(s1.pop().equals("b") && s2.pop().equals(3) && s1.size() == 1 && s2.size() == 2) {
			System.out.println("Yay 3");
		}
		

		if(s1.toString().equals("a") && s1.peek().equals("a") && s2.peek().equals(2) && s1.pop().equals("a") && s2.pop().equals(2) && s1.size() == 0 && s2.size() == 1) {
			System.out.println("Yay 4");
		}
		
		//System.out.println(s1.toString());

		
		if(s1.toString().equals("") && s1.peek() == null && s2.peek().equals(1) && s1.pop() == null && s2.pop().equals(1) && s1.size() == 0 && s2.size() == 0) {
			System.out.println("Yay 5");
		}
		
		s2.push(10);
		s2.push(20);
		s2.push(30);
		
		//System.out.println(!s2.isEmpty());
		
		if(s1.isEmpty() && s1.toArray().length == 0 && !s2.isEmpty()) {
			s2.clear();
			if(s2.isEmpty()) {
				System.out.println("Yay 6");
			}
		}
		
		ProgramStack<Integer> s3 = new ProgramStack<>();
		s3.push(3);
		s3.push(2);
		s3.push(1);
		
		int i = 1;
		for(Integer item : s3) {
			if(i == item) System.out.println("Yay " + (6+i));
			else
				System.out.println(item);
			i++;
		}
		s3.push(4);
		s3.pop();
	
		
		if(s3.toString().equals("3 2 1") && s3.toArray()[0].equals(1) && s3.toArray()[1].equals(2) && s3.toArray().length == 3) {
			System.out.println("Yay 10");
		}
		
		
	}
}