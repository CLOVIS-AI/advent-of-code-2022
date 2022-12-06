package day6

import day1.inputLines

fun main() {
	inputLines()
		.forEach { signal ->
			val index = signal.windowedSequence(4, 1)
				.withIndex()
				.first { (_, it) -> it.asSequence().distinct().count() == 4 }
				.index + 4

			println("$signal -> $index")
		}
}