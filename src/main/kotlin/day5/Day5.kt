package day5

import day1.inputLines

private typealias Cargo = Char
private typealias Pile = List<Cargo>

private class Dock(
	piles: List<List<Cargo>>,
) {

	private val _piles = ArrayList(piles.map { ArrayList(it) })
	val piles: List<Pile> get() = _piles

	fun move(n: Int, from: Int, to: Int) {
		println("TRACE: $n boxes $from -> $to")
		val moved = List(n) { _piles[from].removeLast() }
			.asReversed()
		_piles[to].addAll(moved)
	}

	fun topOfEachPile() = piles
		.map { it.lastOrNull() ?: " " }
		.joinToString(separator = "")

	override fun toString() = buildString {
		appendLine("Dock")
		for ((i, pile) in piles.withIndex()) {
			append(" • $i:")
			for (cargo in pile)
				append(" $cargo")
			appendLine()
		}
	}

	companion object {
		fun readFromInput(): Dock {
			val lines = ArrayList<String>()

			inputLines()
				.takeWhile { it.isNotBlank() }
				.forEach { lines += it }

			val size = (lines.last().length / 3)
				.also { println("Detected size: $it") }

			val output = List(size) { ArrayList<Cargo>() }
			for (line in lines.asReversed().drop(1)) {
				for (index in 0 until size) {
					val value = line.getOrNull(index * 4 + 1)
						?.takeUnless { it.isWhitespace() }
						?: continue

					output[index] += value
				}
			}

			return Dock(output)
		}
	}
}

fun main() {
	val dock = Dock.readFromInput()
	println("Initial situation: $dock")

	inputLines()
		.forEach {
			println("INFO: $it")
			val (n, from, to) = it.split(" ")
				.mapNotNull { it.toIntOrNull() }

			dock.move(n, from - 1, to - 1)
		}

	println("Top of each pile: ${dock.topOfEachPile()}")
}
