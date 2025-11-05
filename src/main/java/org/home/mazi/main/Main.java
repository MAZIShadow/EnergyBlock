package org.home.mazi.main;

import org.home.mazi.data.Block;
import org.home.mazi.data.Container;

import java.util.*;

public class Main {

	public static Double fixCalculation(double value) {
		return Math.round(value * 10.0) / 10.0;
	}

	enum Option {
		OPTIMAL_BLOCKS,
		OPTIMAL_ENERGY
	}

	public static void main(String[] args) {
		double requestedChakra = 208.0;
		final int MAX_BLOCKS = 2;
		final int MAX_CONTAINER_PER_BLOCK = 10;
		final int ALL_CONTAINER = MAX_BLOCKS * MAX_CONTAINER_PER_BLOCK;
		Block.MAX_ENERGY = 200.0;
		Container.MIN_ENERGY = 21.0;
		Container.MAX_ENERGY = 55.6;
		Option selected = Option.OPTIMAL_ENERGY;
		System.out.printf("Selected option: [%s]%n", selected);
		int expectedNumberOfContainers;

		switch (selected) {
			case OPTIMAL_BLOCKS:
				expectedNumberOfContainers = (int) Math.ceil(requestedChakra / Container.MAX_ENERGY);
				break;
			default:
				expectedNumberOfContainers = (int) Math.floor(requestedChakra / Container.MIN_ENERGY);
				break;
		}

		System.out.printf("Expected containers: %d%n", expectedNumberOfContainers);

		if (requestedChakra > MAX_BLOCKS * Block.MAX_ENERGY) {
			System.out.printf("Not enough blocks only [%d]%n", MAX_BLOCKS);
			return;
		}

		if (expectedNumberOfContainers > ALL_CONTAINER) {
			System.out.printf("Not enough containers only [%d]%n", ALL_CONTAINER);
			return;
		}

		double optimalValue = fixCalculation(requestedChakra / expectedNumberOfContainers);
		double specificValue = optimalValue + fixCalculation(requestedChakra - optimalValue * expectedNumberOfContainers);
		System.out.printf("OptionalValue: %.1f%nSpecificValue: %.1f%n%n", optimalValue, specificValue);

		if (optimalValue > Container.MAX_ENERGY || optimalValue < Container.MIN_ENERGY || specificValue > Container.MAX_ENERGY || specificValue < Container.MIN_ENERGY) {
			System.out.println("IMPOSSIBLE!!!!");
			return;
		}

		List<Block> listBlocks = new ArrayList<>();

		for (int i = 0; i < MAX_BLOCKS; i++) {
			List<Container> list = new ArrayList<>();
			for (int j = 0; j < MAX_CONTAINER_PER_BLOCK; j++) {
				list.add(new Container());
			}
			listBlocks.add(new Block(list));
		}

		Queue<Double> queue = new LinkedList<>();
		queue.add(specificValue);
		for (int i = 1; i < expectedNumberOfContainers; i++) {
			queue.add(optimalValue);
		}

		// SET VALUES
		all_break:
		for (Block b : listBlocks) {
			for (Container c : b.getList()) {
				Double value = queue.peek();

				if (value == null) {
					break all_break;
				}

				if (b.getAvailableLeft() < value) {
					break;
				}

				c.setCurrent(queue.poll());
			}
		}

		// PRINT RESULT
		int i = 1;
		for (Block b : listBlocks) {
			System.out.printf("Block #%d [%.1f/%.1f]%n", i++, b.getBlockChakra(), Block.MAX_ENERGY);
			System.out.print("[|");
			for (Container c : b.getList()) {
				System.out.printf("%.1f|", c.getCurrent());
			}
			System.out.println("]");
			System.out.println();
		}
	}
}
