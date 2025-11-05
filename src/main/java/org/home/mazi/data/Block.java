package org.home.mazi.data;

import java.util.List;
import java.util.stream.DoubleStream;

public class Block {
	public static Double MAX_ENERGY = 200.0;
	private final List<Container> list;

	public Block(List<Container> list) {

		if (list == null || list.isEmpty()) {
			throw new IllegalArgumentException("List can't be null or empty");
		}

		this.list = list;
	}

	public List<Container> getList() {
		return list;
	}

	public Double getBlockChakra() {
		return list.stream().flatMapToDouble(c -> DoubleStream.of(c.getCurrent())).sum();
	}

	public Double getAvailableLeft() {
		return MAX_ENERGY - getBlockChakra();
	}
}
