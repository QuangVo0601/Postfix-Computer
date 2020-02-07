//Do not edit this class, just add JavaDocs

/**
 * The implementation of a table entry.
 * @author Professors
 * @param <K> the type of the key.
 * @param <V> the type of the value.
 */
class TableEntry<K,V> {
	private K key;
	private V value;
	
	/**
	 * Create a new table entry with a given key and its associated value.
	 * @param key the given key.
	 * @param value the associated value.
	 */
	public TableEntry(K key, V value) {
		this.key = key;
		this.value = value;
	}
	
	/**
	 * Return the key corresponding to this table entry.
	 * @return the key corresponding to this table entry.
	 */
	public K getKey() {
		return key;
	}
	
	/**
	 * Return the value corresponding to this table entry.
	 * @return the value corresponding to this table entry.
	 */
	public V getValue() {
		return value;
	}
	
	/**
	 * Create a string representation of this table entry with the key and the value.
	 * @return the string representation.
	 */
	public String toString() {
		return key.toString()+":"+value.toString();
	}
}