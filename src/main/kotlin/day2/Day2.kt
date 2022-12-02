package day2

import day1.inputLines
import day2.Outcome.*

private data class Play(
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

	companion object {
		fun fromExpected(player1: Int, expected: Outcome) = listOf(0, 1, 2)
			.map { Play(player1, it) }
			.first { it.wins == expected }
	}
}

private enum class Outcome {
	Win,
	Loss,
	Draw,
}

private fun convertPlay(text: String) = when (text) {
	"A", "X" -> 0
	"B", "Y" -> 1
	"C", "Z" -> 2
	else -> error("Unknown letter: $text")
}

private fun convertOutcome(text: String) = when (text) {
	"X" -> Loss
	"Y" -> Draw
	"Z" -> Win
	else -> error("Unknown letter: $text")
}

fun main() {
	val result = inputLines()
		.map {
			val (first, second) = it.split(" ")
			Play.fromExpected(convertPlay(first), convertOutcome(second))
		}
		.onEach { println("$it, result: ${it.wins}, score: ${it.score}") }
		.map { it.score }
		.sum()

	println("Total score: $result")
}