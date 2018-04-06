package main.com.TheTradeDesk.replacementalgorithm;

import main.com.TheTradeDesk.cache.Block;

public abstract class ReplacementMethod<T, K> {
	
	private Block<T, K> head;
	private Block<T, K> tail;
	
	public ReplacementMethod() {
		this.head = new Block<>(null, null);
		this.tail = new Block<>(null, null);
		this.head.setPre(null);
		this.head.setNext(this.tail);
		this.tail.setPre(head);
		this.tail.setNext(null);
	}
	
	public Block<T, K> getHead() {
		return this.head;
	}
	
	public Block<T, K> getTail() {
		return this.tail;
	}

	public abstract void placeBlock(Block<T, K> block);
	public abstract void whenSetIsFull();
	public abstract void whenBlockAlreadyExists(Block<T, K> block);
	
	public void removeBlockFromSet(Block<T, K> block) {
		block.getPre().setNext(block.getNext());
		block.getNext().setPre(block.getPre());
	}
}
