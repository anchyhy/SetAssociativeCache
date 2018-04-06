package main.com.TheTradeDesk.replacementalgorithm;

import main.com.TheTradeDesk.cache.Block;

public class LRU<T, K> extends ReplacementMethod<T, K> {
	
	@Override
	public void placeBlock(Block<T, K> block) {
		block.setPre(this.getHead());
		block.setNext(this.getHead().getNext());
		block.getNext().setPre(block);
		this.getHead().setNext(block);
	}

	@Override
	public void whenSetIsFull() {
		removeBlockFromSet(this.getTail().getPre());
	}

	@Override
	public void whenBlockAlreadyExists(Block<T, K> block) {
		removeBlockFromSet(block);
	}
}
