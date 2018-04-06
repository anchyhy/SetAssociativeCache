package test.com.TheTradeDesk.cache;

import java.util.Stack;

import main.com.TheTradeDesk.cache.Cache;
import main.com.TheTradeDesk.cache.CacheImpl;
import main.com.TheTradeDesk.cache.ReplacementMethodNames;

import org.junit.Test;

import static org.junit.Assert.*;

public class CacheImplTest {
	
	@Test
	public void testCreateCacheWithIntegerType() {
		CacheImpl<Integer, Integer> cache = new CacheImpl<>(2, 3, ReplacementMethodNames.LRU);
		cache.put(2, 3);
		assertEquals(new Integer(3), cache.get(2).getValue());
	}
	
	@Test
	public void testCreateCacheWithStringType() {
		CacheImpl<String, String> cache = new CacheImpl<>(2, 3, ReplacementMethodNames.LRU);
		cache.put("key", "value");
		assertEquals("value", cache.get("key").getValue());
	}
	
	@Test
	public void testCreateCacheWithClassType() {
		CacheImpl<Class, Class> cache = new CacheImpl<>(2, 3, ReplacementMethodNames.LRU);
		cache.put(Integer.class, String.class);
		assertEquals(String.class, cache.get(Integer.class).getValue());
	}
	
	@Test
	public void testPutWithoutReplacement() {
		Cache<String, String> cache = new CacheImpl<>(2, 3, ReplacementMethodNames.LRU);
		cache.put("testKey", "testValue");
		assertEquals(cache.get("testKey").getValue(), "testValue");
	}
	
	@Test
	public void testPutWithLRUReplacement() {
		Cache<String, String> cache = new CacheImpl<>(1, 2, ReplacementMethodNames.LRU);
		cache.put("testKey1", "testValue1");
		cache.put("testKey2", "testValue2");
		cache.put("testKey3", "testValue3");
		assertEquals(cache.get("testKey1"), null);
	}
	
	@Test
	public void testPutWithMRUReplacement() throws InterruptedException {
		Cache<String, String> cache = new CacheImpl<>(1, 2, ReplacementMethodNames.MRU);
		cache.put("testKey1", "testValue1");
		cache.put("testKey2", "testValue2");
		cache.put("testKey3", "testValue3");
		assertEquals(cache.get("testKey2"), null);
	}
	
	@Test
	public void testPutWithSameKey() {
		Cache<String, String> cache = new CacheImpl<>(1, 2, ReplacementMethodNames.MRU);
		cache.put("testKey", "testValue1");
		cache.put("testKey", "testValue2");
		assertEquals(cache.get("testKey").getValue(), "testValue2");
	}
	
	@Test
	public void testGet() {
		Cache<String, String> cache = new CacheImpl<>(1, 2, ReplacementMethodNames.MRU);
		cache.put("testKey", "testValue");
		assertEquals(cache.get("testKey").getValue(), "testValue");
	}
}
