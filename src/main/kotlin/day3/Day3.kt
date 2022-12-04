package day3

import day1.inputLines

fun main() {
	val result = inputLines()
		.windowed(3, 3)
		.map { (first, second, third) -> first.first { it in second && it in third } }
		.map {
			if (it in 'a'..'z') it.code - 'a'.code
			else it.code - 'A'.code + 26
		}
		.map { it + 1 }
		.sum()

	println(result)
}