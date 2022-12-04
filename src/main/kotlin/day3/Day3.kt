package day3

import day1.inputLines

fun main() {
	val result = inputLines()
		.map { it.chunked(it.length / 2) }
		.map { (first, second) -> first.first { it in second } }
		.map {
			if (it in 'a'..'z') it.code - 'a'.code
			else it.code - 'A'.code + 26
		}
		.map { it + 1 }
		.sum()

	println(result)
}