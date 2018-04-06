package main.com.TheTradeDesk.cache;

public interface Cache<T, K>  {
	public void put(T key, K value);
	public Block<T, K> get(T key);
	public void print();
}
