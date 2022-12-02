package day1

fun inputLines() = sequence {
	while (true) {
		yield(readlnOrNull() ?: break)
	}
}

private fun Sequence<String>.accumulateElves() = sequence {
	val acc = ArrayList<String>()
	for (value in this@accumulateElves) {
		if (value.isNotBlank()) {
			acc += value
		} else {
			yield(acc)
			acc.clear()
		}
	}
}

fun main() {
	val result = inputLines()
		.accumulateElves()
		.map { it.sumOf { it.toInt() } }
		.sortedDescending()
		.take(3)
		.sum()

	println(result)
}