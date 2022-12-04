package day4

import day1.inputLines

private operator fun IntRange.contains(other: IntRange): Boolean =
	first >= other.first && last <= other.last

fun main() {
	val result = inputLines()
		.map {
			val (range1, range2) = it.split(',')
			val (start1, end1) = range1.split('-')
			val (start2, end2) = range2.split('-')
			(start1.toInt()..end1.toInt()) to (start2.toInt()..end2.toInt())
		}
		.filter { (range1, range2) ->
			range1 in range2 || range2 in range1
		}
		.count()

	println(result)
}