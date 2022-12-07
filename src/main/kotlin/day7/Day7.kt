package day7

import arrow.optics.optics
import day1.inputLines

@optics
sealed class File {
	abstract val size: Int

	abstract fun <T> fold(
		initial: T,
		onRegular: (acc: T, it: Regular) -> T,
		onDirectory: (acc: T, it: Directory) -> T,
	): T

	fun <T> fold(
		initial: T,
		transform: (acc: T, it: File) -> T,
	): T = fold(
		initial,
		onRegular = transform,
		onDirectory = transform,
	)

	fun filter(predicate: (File) -> Boolean): File = fold(
		this,
		onRegular = { acc, it -> it },
		onDirectory = { acc, it -> if (predicate(it)) it else acc }
	)

	abstract fun toString(indent: Int = 0): String

	@optics
	data class Regular(override val size: Int) : File() {

		override fun <T> fold(
			initial: T,
			onRegular: (acc: T, it: Regular) -> T,
			onDirectory: (acc: T, it: Directory) -> T,
		): T = onRegular(initial, this)

		override fun toString(indent: Int) = "$size"
		override fun toString() = toString(indent = 1)

		companion object
	}

	@optics
	data class Directory(val children: Map<String, File> = emptyMap()) : File() {

		override val size by lazy { children.values.sumOf { it.size } }

		fun plus(path: List<String>, file: File): Directory = when {
			path.isEmpty() -> error("Replacing the root is forbidden")
			path.size == 1 -> copy(children = children + (path.first() to file))
			else -> {
				(children.getOrDefault(path.first(), Directory()) as Directory)
					.plus(path.drop(1), file)
			}
		}

		override fun <T> fold(
			initial: T,
			onRegular: (acc: T, it: Regular) -> T,
			onDirectory: (acc: T, it: Directory) -> T
		): T {
			val folded = children
				.values
				.fold(initial) { acc, it ->
					it.fold(acc, onRegular, onDirectory)
				}

			return onDirectory(folded, this)
		}

		override fun toString(indent: Int) = buildString {
			appendLine()

			val spacing = "  ".repeat(indent)
			for ((name, child) in children) {
				append(spacing)
				append(name)
				append(": ")
				appendLine(child.toString(indent + 1))
			}
		}

		override fun toString() = toString(indent = 1)

		companion object
	}

	companion object
}

private fun Sequence<String>.repl(): File {
	val path = ArrayList<String>()
	var root = File.Directory()

	forEach { line ->
		println("TRACE: Current path: ${path.joinToString(separator = "/", prefix = "/")}")
		if (line.startsWith('$')) {
			val args = line.removePrefix("$ ").split(" ")
			when (args[0]) {
				"cd" -> when (val dir = args[1]) {
					"/" -> path.clear()
					".." -> path.removeLast()
					else -> path += dir
				}
			}
		} else {
			val words = line.split(" ")
			val header = words[0]

			run {
				val size = header.toIntOrNull() ?: return@run
				val name = words[1]
				println("TRACE: Creating file $name with size $size")
				root = root.plus(path + name, File.Regular(size))
			}
		}

		println("TRACE: File: $root")
	}

	return root
}

fun main() {
	val result = inputLines()
		.onEach { println("INFO:  $it") }
		.repl()
		.also { println("Result: $it") }
		.filter { it.size <= 100_000 }

	println("Total size: $result")
}