//Do not edit this class, just add JavaDocs
//You may edit the main method for testing things if you want
//but do not change the actual class structure.

/**
 * The implementation of a Node.
 * @author Professors
 * @param <T> the type of the value of Node.
 */
class Node<T> {
	private T value;
	private Node<T> next;
	private Node<T> prev;
	
	/**
	 * Create a new node with the specified value.
	 * @param value the specified value.
	 */
	public Node(T value) {
		this.value = value;
	}
	
	/**
	 * Return the value stored this node.
	 * @return the value stored this node.
	 */
	public T getValue() {
		return value;
	}
	
	/**
	 * Set the value stored in this node.
	 * @param value the (new) value of this node.
	 */
	public void setValue(T value) {
		this.value = value;
	}
	
	/**
	 * Return the next node after this node.
	 * @return the node after this node.
	 */
	public Node<T> getNext() {
		return this.next;
	}
	
	/**
	 * Set the next node after this node.
	 * @param next the (new) next node after this node.
	 */
	public void setNext(Node<T> next) {
		this.next = next;
	}
	
	/**
	 * Return the previous node before this node.
	 * @return the node before this node.
	 */
	public Node<T> getPrev() {
		return this.prev;
	}
	
	/**
	 * Set the previous node before this node.
	 * @param previous the previous node before this node.
	 */
	public void setPrev(Node<T> prev) {
		this.prev = prev;
	}
	
	/**
	 * Return a string representation consists of the list of nodes.
	 * @param head the head node of the list.
	 * @return the string representation.
	 */
	public static String listToString(Node<?> head) {
		StringBuilder ret = new StringBuilder();
		Node<?> current = head;
		while(current != null) {
			ret.append(current.value); //add values to the string
			ret.append(" "); //add space " "
			current = current.getNext(); //move to the next node
		}
		return ret.toString().trim(); 
	}
	
	/**
	 * Return a string representation consists of the list of nodes.
	 * It prints out the reverse of the list.
	 * @param head the head node of the list.
	 * @return the string representation in reverse order.
	 */
	public static String listToStringBackward(Node<?> head) {
		Node<?> current = head;
		while(current.getNext() != null) {
			current = current.getNext(); //move to the next node
		}
		
		StringBuilder ret = new StringBuilder();
		while(current != null) {
			ret.append(current.value); //add values to the string
			ret.append(" "); //add space " "
			current = current.getPrev(); //move to the previous node
		}
		return ret.toString().trim();
	}
	
	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		
		//make nodes
//		Node<String> n1 = new Node<>("A");
//		Node<String> n2 = new Node<>("B");
//		Node<String> n3 = new Node<>("C");
//		
//		//connect forward references
//		n1.setNext(n2);
//		n2.setNext(n3);
//		
//		//connect backward references
//		n3.setPrev(n2);
//		n2.setPrev(n1);
//		
//		//print forward and backward
//		System.out.println(Node.listToString(n1));
//		System.out.println(Node.listToStringBackward(n1));
		
		Node<Integer> n1 = new Node<>(10);
		Node<Integer> n2 = new Node<>(20);
		Node<Integer> n3 = new Node<>(30);
		Node<Integer> n4 = new Node<>(40);
		
		n1.setNext(n2);
		n2.setNext(n3);
		n3.setNext(n4);
		
		//System.out.println(Node.listToString(n1));
		method(n1);

		
	}
	
	public static void method(Node<?> n){
		if (n == null) return;
		method(n.getNext());
		System.out.print(n.getValue() + " ");
	}
}