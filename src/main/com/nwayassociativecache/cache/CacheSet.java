package main.com.TheTradeDesk.cache;

import main.com.TheTradeDesk.replacementalgorithm.ReplacementMethod;

public class CacheSet<T, K> {
	private int curBlocksCount;
	private int setCapacity;
	private ReplacementMethod<T, K> replacementMethod;

	public CacheSet(int setCapacity, ReplacementMethod<T, K> replacementMethod) {
		this.curBlocksCount = 0;
		this.setCapacity = setCapacity;
		this.replacementMethod = replacementMethod;
	}

	public ReplacementMethod<T, K> getReplacementMethod() {
		return this.replacementMethod;
	}

	public void put(T key, K value) {
		Block<T, K> block = new Block<>(key, value);
		Block<T, K> curBlock = replacementMethod.getHead().getNext();
		boolean isFound = false;
		while (curBlock != replacementMethod.getTail()) {
			if (key instanceof String && key.equals(curBlock.getKey())
					|| curBlock.getKey() == key) {
				// delete current block
				replacementMethod.whenBlockAlreadyExists(curBlock);
				isFound = true;
				break;
			}
			// move to the next block
			curBlock = curBlock.getNext();
		}
		if (!isFound) {
			if (curBlocksCount == setCapacity) {
				// delete block by replacementMethod
				replacementMethod.whenSetIsFull();
			} else {
				curBlocksCount++;
			}
		}
		// add to head or tail by algorithm
		replacementMethod.placeBlock(block);
	}
	
	public Block<T, K> get(T key) {
		Block<T, K> curBlock = replacementMethod.getHead().getNext();
		while (curBlock != replacementMethod.getTail()) {
			if (key instanceof String && key.equals(curBlock.getKey())
					|| curBlock.getKey() == key) {
				Block<T, K> block = new Block<>(curBlock.getKey(),
						curBlock.getValue());
				// delete current block
				replacementMethod.whenBlockAlreadyExists(curBlock);
				// add to head or tail by algorithm
				replacementMethod.placeBlock(block);
				return block;
			}
			curBlock = curBlock.getNext();
		}
		return null;
	}
}
