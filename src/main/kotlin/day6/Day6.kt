package day6

import day1.inputLines

private const val TARGET_SIZE = 14

fun main() {
	inputLines()
		.forEach { signal ->
			val index = signal.windowedSequence(TARGET_SIZE, 1)
				.withIndex()
				.first { (_, it) -> it.asSequence().distinct().count() == TARGET_SIZE }
				.index + TARGET_SIZE

			println("$signal -> $index")
		}
}