/**
 * SymbolTable implementation with linear-probing hash table.
 * @author Quang Vo.
 * @param <T> the type of the value of SymbolTable.
 */
class SymbolTable<T> {
	private TableEntry<String,T>[] storage; //the underlying symbol table
	private int numOfElements; //number of key-value pairs in the symbol table
	private TableEntry<String, T> tombStone; //the inactive tombStone
	
	/**
	 * Initialize an empty symbol table with the specified initial capacity.
	 * @param size the specified initial capacity.
	 */
	@SuppressWarnings("unchecked")
	public SymbolTable(int size) {
		//Create a hash table where the initial storage
		//is size and string keys can be mapped to T values.
		//You may assume size is >= 2
		
		storage = new TableEntry[size];
		numOfElements = 0;
		tombStone = new TableEntry<String, T>(null, null); 
		
	}
	
	/**
	 * Return the capacity of the symbol table.
	 * @return the capacity.
	 */
	public int getCapacity() {
		//return how big the storage is
		//O(1)
		
		return storage.length;
	}
	
	/**
	 * Return the number of key-value pairs in the symbol table.
	 * @return the number of key-value pairs.
	 */
	public int size() {
		//return the number of elements in the table
		//O(1)
		
		return numOfElements;
	}
	
	/**
	 * Insert the key-value pair into the symbol table.
	 * Use linear probing if that location of key is in use.
	 * Replace the old value with the new value if the key already exists in the table.
	 * Double the capacity and rehash if the table is >= 80% full.
	 * @param k the key.
	 * @param v the value.
	 */
	public void put(String k, T v) {
		//Place value v at the location of key k.
		//Use linear probing if that location is in use.
		//You may assume both k and v will not be null.
		
		//Hint: Make a TableEntry to store in storage
		//and use the absolute value of k.hashCode() for
		//the probe start.
		
		//If the key already exists in the table
		//replace the current value with v.
		
		//If the key isn't in the table and the table
		//is >= 80% full, expand the table to twice
		//the size and rehash
		
		//Worst case: O(n), Average case: O(1)

		int pos = Math.abs(k.hashCode()) % storage.length; //get the pos index
		
		while(storage[pos] != null && storage[pos] != tombStone){ //check if the index is null or a tombstone
			
			if(storage[pos].getKey().equals(k)){ //if same key, replace the value
				storage[pos] = new TableEntry<String, T>(k, v);
				return;
			}
			pos = (pos + 1) % storage.length; //move to the next index

		}
			storage[pos] = new TableEntry<String, T>(k, v); //place value v at the location of key k
			numOfElements++;
		
		//double the capacity the table if the table is >=80% full, then rehash.
		if(this.size() >= ((double)(storage.length) * 4/5)){ 
			rehash(storage.length * 2);
		}
	}
	
	/**
     * Remove the specified key and its associated value from this symbol table.
     * Leave a tombstone at the removed position to not break the chain.
	 * @param k the key.
	 * @return the removed value.
	 * @return null if the value is not in the table.
	 */
	public T remove(String k) {
		//Remove the given key (and associated value)
		//from the table. Return the value removed.		
		//If the value is not in the table, return null.
		
		//Hint 1: Remember to leave a tombstone!
		//Hint 2: Does it matter what a tombstone is?
		//   Yes and no... You need to be able to tell
		//   the difference between an empty spot and
		//   a tombstone and you also need to be able
		//   to tell the difference between a "real"
		//   element and a tombstone.
		
		//Worst case: O(n), Average case: O(1)
		
		int pos = Math.abs(k.hashCode()) % storage.length; //get the pos index
		
		while(storage[pos] != null && !storage[pos].getKey().equals(k)){ //check if the index is null or the key
			pos = (pos + 1) % storage.length; //move to the next index
		}
		
		if(storage[pos] == null){ //the key is not in the table
			return null;
		}
		else{
			T oldValue = storage[pos].getValue(); 
			storage[pos] = tombStone; //set the pos index to be a tombstone
			numOfElements--;
			return oldValue; //return the old value
		}	
	}
	
	/**
     * Returns the value associated with the specified key.
	 * @param k the key.
	 * @return the value associated with the specified key.
	 * @return null if the value is not in the table.
	 */
	public T get(String k) {
		//Given a key, return the value from the table.
		
		//If the value is not in the table, return null.
		
		//Worst case: O(n), Average case: O(1)
		

		int pos = Math.abs(k.hashCode()) % storage.length; //get the pos index
		
		//check if the item is null or a tombstone or the key
		while(storage[pos] != null && (storage[pos] == tombStone || !storage[pos].getKey().equals(k))){
			pos = (pos + 1) % storage.length; //move to the next index
		}
		if(storage[pos] == null){ //the key is not in the table
			return null;
		}
		else{
			return storage[pos].getValue(); //the key is found 
		}
		
	}
	
	/**
	 * Check if there is a tombstone at the given index.
	 * @param index the given index.
	 * @return true if there is a tombstone, false otherwise.
	 */
	public boolean isTombstone(int index) {
		//this is a helper method needed for printing
		
		//return whether or not there is a tombstone at the
		//given index
		
		//O(1)
		
		if(storage[index] != tombStone){  
			return false; //if the index is not a tombstone.
		}
		return true;
	}
	
	/**
	 * Resize the symbol table to the given capacity.
	 * Rehash all values.
	 * @param size the given capacity.
	 * @return true if the new size fits all the elements, false otherwise.
	 */
	@SuppressWarnings("unchecked")
	public boolean rehash(int size) {
		//Increase or decrease the size of the storage,
		//rehashing all values.
		
		//If the new size won't fit all the elements,
		//return false and do not rehash. Return true
		//if you were able to rehash.
		
		
		if(size < this.size()){ //check to see if the new size fits all the elements
			return false; 
		}
		
		SymbolTable<T> temp = new SymbolTable<T>(size); //create a temporary symbol table with the new capacity
		
		for(int i = 0; i < getCapacity(); i++){
			if(storage[i] != null && storage[i] != tombStone){ //check if the index is null or a tombstone
				temp.put(storage[i].getKey(), storage[i].getValue()); //rehash the elements
			}
		}
		
		this.storage = temp.storage; //update references
		return true;		
	}
	
	/**
	 *  A main method to test/demo
	 *  @param args not used
	 */
	public static void main(String[] args) {
		//main method for testing, edit as much as you want
		SymbolTable<String> st1 = new SymbolTable<>(10);
		SymbolTable<Integer> st2 = new SymbolTable<>(5);
		
		if(st1.getCapacity() == 10 && st2.getCapacity() == 5 && st1.size() == 0 && st2.size() == 0) {
			System.out.println("Yay 1");
		}
		
		st1.put("a","apple");
		st1.put("b","banana");
		st1.put("banana","b");
		st1.put("b","butter");
		
		//System.out.println(st1.toStringDebug());
		
		if(st1.toString().equals("a:apple\nb:butter\nbanana:b") && st1.toStringDebug().equals("[0]: null\n[1]: null\n[2]: null\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:apple\n[8]: b:butter\n[9]: banana:b")) {
			System.out.println("Yay 2");
		}
		
		//System.out.println(st1.size());
		
		if(st1.getCapacity() == 10 && st1.size() == 3 && st1.get("a").equals("apple") && st1.get("b").equals("butter") && st1.get("banana").equals("b")) {
			System.out.println("Yay 3");
		}
		
		st2.put("a",1);
		st2.put("b",2);
		st2.put("e",3);
		st2.put("y",4);
		
		//System.out.println(st2.toStringDebug());
		//System.out.println(st2.getCapacity());

		if(st2.toString().equals("e:3\ny:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: e:3\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
			System.out.println("Yay 4");
		}
		
		if(st2.getCapacity() == 10 && st2.size() == 4 && st2.get("a").equals(1) && st2.get("b").equals(2) && st2.get("e").equals(3) && st2.get("y").equals(4)) {
			System.out.println("Yay 5");
		}
		
		//System.out.println(st2.remove("e"));
		//System.out.println(st2.get("y"));
		
		if(st2.remove("e").equals(3) && st2.getCapacity() == 10 && st2.size() == 3 && st2.get("e") == null && st2.get("y").equals(4)) {
			System.out.println("Yay 6");
		}
		//System.out.println(st2.toStringDebug());

		
		if(st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: tombstone\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
			System.out.println("Yay 7");
		}
		
//		st2.put("e", 100);
//		
//		if(st2.toString().equals("e:100\ny:4\na:1\nb:2") && st2.get("e").equals(100) && st2.toStringDebug().equals("[0]: null\n[1]: e:100\n[2]: y:4\n[3]: null\n[4]: null\n[5]: null\n[6]: null\n[7]: a:1\n[8]: b:2\n[9]: null")) {
//			System.out.println("Yay 7.5");
//		}
		

		if(st2.rehash(2) == false && st2.size() == 3 && st2.getCapacity() == 10) {
			System.out.println("Yay 8");
		}
		
//		st2.rehash(4);
//		System.out.println(st2.toStringDebug());
		
		if(st2.rehash(4) == true && st2.size() == 3 && st2.getCapacity() == 4) {
			System.out.println("Yay 9");
		}
		
		//System.out.println(st2.toStringDebug());

		
		if(st2.toString().equals("y:4\na:1\nb:2") && st2.toStringDebug().equals("[0]: null\n[1]: y:4\n[2]: a:1\n[3]: b:2")) {
			System.out.println("Yay 10");
		}
		
		SymbolTable<String> st3 = new SymbolTable<>(2);
		st3.put("a","a");
		st3.remove("a");
		
		//System.out.println(st3.toStringDebug());

		
		if(st3.toString().equals("") && st3.toStringDebug().equals("[0]: null\n[1]: tombstone")) {
			st3.put("a","a");
			if(st3.toString().equals("a:a") && st3.toStringDebug().equals("[0]: null\n[1]: a:a") && st3.toStringDebug().equals("[0]: null\n[1]: a:a")) {
				System.out.println("Yay 11");
			}
		}
		
		st3.put("b","b");
		if(st3.toString().equals("a:a\nb:b") && st3.toStringDebug().equals("[0]: null\n[1]: a:a\n[2]: b:b\n[3]: null") && st3.getCapacity() == 4) {
			System.out.println("Yay 12");
		}
		
	}
	
	//--------------Provided methods below this line--------------
	//Add JavaDocs, but do not change the methods.
	
	/**
	 * Create a string representation of the symbol table.
	 * Without empty indices and tombstones.
	 * @return the string representation.
	 */
	public String toString() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(storage[i] != null && !isTombstone(i)) {
				s.append(storage[i] + "\n");
			}
		}
		return s.toString().trim();
	}
	
	/**
	 * Create a string representation of the symbol table.
	 * This shows all the elements including empty indices and tombstones. 
	 * @return the string representation.
	 */
	public String toStringDebug() {
		//THIS METHOD IS PROVIDED, DO NOT CHANGE IT
		StringBuilder s = new StringBuilder();
		for(int i = 0; i < storage.length; i++) {
			if(!isTombstone(i)) {
				s.append("[" + i + "]: " + storage[i] + "\n");
			}
			else {
				s.append("[" + i + "]: tombstone\n");
			}
			
		}
		return s.toString().trim();
	}
}