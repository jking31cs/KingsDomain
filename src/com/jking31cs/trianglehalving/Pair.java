package com.jking31cs.trianglehalving;

public class Pair<T> {
	public final T o1;
	public final T o2;
	
	public Pair(T o1, T o2) {
		this.o1 = o1;
		this.o2 = o2;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((o1 == null) ? 0 : o1.hashCode()) + ((o2 == null) ? 0 : o2.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Pair)) return false;
		Pair<?> that = (Pair<?>) obj;
		return (that.o1.equals(o1) && that.o2.equals(o2))
				|| (that.o1.equals(o2) && that.o2.equals(o1));
	}
	
	
}
