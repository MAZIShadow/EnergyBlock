package org.home.mazi.data;

public class Container {
	public static Double MAX_ENERGY = 41.0;
	public static Double MIN_ENERGY = 21.0;

	private Double current = 0.0;

	public Double getCurrent() {
		return current;
	}

	public void setCurrent(Double current) {
		if (current > Container.MAX_ENERGY) {
			throw new IllegalArgumentException("You have destroyed Container");
		}

		this.current = current;
	}
}
