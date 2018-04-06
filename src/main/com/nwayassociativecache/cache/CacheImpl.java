package main.com.TheTradeDesk.cache;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Logger;

import main.com.TheTradeDesk.replacementalgorithm.LRU;
import main.com.TheTradeDesk.replacementalgorithm.MRU;

public class CacheImpl<T, K> implements Cache<T, K> {
	public static final Logger LOGGER = Logger.getLogger("ErroLogging");
	public final CacheSet<T, K>[] cacheSetArray;
	public final int setCount;
	public final int blockCount;

	@SuppressWarnings("unchecked")
	public CacheImpl(int setCount, int blockCount,
			ReplacementMethodNames replacementMethodNames) {
		this.setCount = setCount;
		this.blockCount = blockCount;
		cacheSetArray = (CacheSet<T, K>[]) Array.newInstance(CacheSet.class,
				setCount);
		switch(replacementMethodNames) {
			case LRU:
				for (int idx = 0; idx < setCount; idx++) {
					cacheSetArray[idx] = new CacheSet<>(blockCount, new LRU<T, K>());
				}
				break;
			case MRU:
				for (int idx = 0; idx < setCount; idx++) {
					cacheSetArray[idx] = new CacheSet<>(blockCount, new MRU<T, K>());
				}
				break;
			default:
				break;
		}
	}

	@Override
	public synchronized void put(T key, K value) {
		int setIndex = getSetIndex(key);
		cacheSetArray[setIndex].put(key, value);
	}

	@Override
	public synchronized Block<T, K> get(T key) {
		int setIndex = getSetIndex(key);
		return cacheSetArray[setIndex].get(key);
	}

	private int toHashCode(T key) {
		byte[] bytesOfKey = null;
		try {
			bytesOfKey = key.toString().getBytes("UTF-8");
		} catch (UnsupportedEncodingException ex) {
			LOGGER.info("UnsupportedEncodingException: Cannot get the bytes of key!");

		}
		MessageDigest messageDigest = null;
		try {
			messageDigest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException ex) {
			LOGGER.info("The given MessageDigest algorithm is wrong!");
		}
		byte[] hashBytes = messageDigest.digest(bytesOfKey);
		return Math.abs(ByteBuffer.wrap(hashBytes)
				.order(ByteOrder.LITTLE_ENDIAN).getInt());
	}

	private int getSetIndex(T key) {
		return toHashCode(key) % setCount;
	}

	@Override
	public void print() {
		for (int idx = 0; idx < cacheSetArray.length; idx++) {
			Block<T, K> head = cacheSetArray[idx].getReplacementMethod().getHead();
			Block<T, K> tail = cacheSetArray[idx].getReplacementMethod().getTail();
			Block<T, K> curBlock = head.getNext();
			System.out.println("--------------------SET " + idx + "--------------------");
			while (curBlock != tail) {
				System.out.println("[key: " + curBlock.getKey() + ", value: "
						+ curBlock.getValue() + "]");
				curBlock = curBlock.getNext();
			}
		}
		System.out.println("-----------------------------------------------");
	}
}
