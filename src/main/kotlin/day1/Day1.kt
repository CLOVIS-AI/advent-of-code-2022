package day1

private fun inputLines() = sequence {
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
		.max()

	println(result)
}