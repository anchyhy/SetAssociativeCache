package main.com.TheTradeDesk.replacementalgorithm;

import main.com.TheTradeDesk.cache.Block;

public class MRU<T, K> extends ReplacementMethod<T, K> {

	@Override
	public void placeBlock(Block<T, K> block) {
		block.setPre(this.getTail().getPre());
		block.setNext(this.getTail());
		block.getPre().setNext(block);
		this.getTail().setPre(block);
	}

	@Override
	public void whenSetIsFull() {
		removeBlockFromSet(this.getTail().getPre());
	}

	@Override
	public void whenBlockAlreadyExists(Block<T, K> block) {
		// TODO Auto-generated method stub
		removeBlockFromSet(block);
	}
}
