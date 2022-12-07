package day7

import arrow.optics.optics
import day1.inputLines

@optics
sealed class File {
	abstract val size: Int

	@optics
	data class Regular(override val size: Int) : File() {
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
	}

	return root
}

fun main() {
	val result = inputLines()
		.onEach { println("INFO:  $it") }
		.repl()

	println("Total size: ${result.size}")
}