package main.com.TheTradeDesk.cache;

public class Block<T, K> {
	private T key;
	private K value;
	private Block<T, K> pre;
	private Block<T, K> next;
	
	public Block() {
		this.key = null;
		this.value = null;
		this.pre = null;
		this.next = null;
	}
	public Block(T key, K value) {
		this.key = key;
		this.value = value;
	}
	public T getKey() {
		return key;
	}
	public void setKey(T key) {
		this.key = key;
	}
	public K getValue() {
		return value;
	}
	public void setValue(K value) {
		this.value = value;
	}
	public Block<T, K> getPre() {
		return pre;
	}
	public void setPre(Block<T, K> pre) {
		this.pre = pre;
	}
	public Block<T, K> getNext() {
		return next;
	}
	public void setNext(Block<T, K> next) {
		this.next = next;
	}
	
	@Override
	public String toString() {
			return "[key: " + this.getKey() + ", value: "
					+ this.getValue() + "]";
	}
}
