package day2

import day1.inputLines
import day2.Outcome.*

private data class PLay(
	val player1: Int,
	val player2: Int,
) {
	val wins get() = when (player1) {
		(player2 - 1).mod(3) -> Win
		(player2 + 1).mod(3) -> Loss
		player2 -> Draw
		else -> error("Don't know what do do with $player1 $player2. Would win if player 2 played ${(player2 - 1).rem(3)}")
	}

	val score get() = player2 + 1 + when (wins) {
		Win -> 6
		Loss -> 0
		Draw -> 3
	}

	override fun toString() = "($player1, $player2)"
}

private enum class Outcome {
	Win,
	Loss,
	Draw,
}

private fun convert(text: String) = when (text) {
	"A", "X" -> 0
	"B", "Y" -> 1
	"C", "Z" -> 2
	else -> error("Unknown letter: $text")
}

fun main() {
	val result = inputLines()
		.map {
			val (first, second) = it.split(" ")
			PLay(convert(first), convert(second))
		}
		.onEach { println("$it, result: ${it.wins}, score: ${it.score}") }
		.map { it.score }
		.sum()

	println("TOtal score: $result")
}