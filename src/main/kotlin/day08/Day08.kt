package day08

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val notes = Notes(input)
        return notes.countInstancesOfNumbersInOutput(listOf(1, 4, 7, 8))
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
//    check(part2(testInput) == 1)

    val input = readInput("Day08")
    println(part1(input))
//    println(part2(input))
}

class Notes (input: List<String>) {

    private val entries = createEntries(input)

    private fun createEntries(input: List<String>): List<Entry> {
        return input.map { Entry(it) }
    }

    fun countInstancesOfNumbersInOutput(numbers: List<Int>): Int {
        var total = 0
        for (entry in entries) {
            for (number in numbers) {
                total += entry.countInstancesOfNumberInOutput(number)
            }
        }
        return total
    }
}

class Entry(input: String) {

    val signalPatterns = parseSignalPatterns(input)
    val outputPatterns = parseOutputPatterns(input)

    private fun parseSignalPatterns(input: String): List<String> {
        return input.split("|")[0].trim().split(" ").toList()
    }

    private fun parseOutputPatterns(input: String): List<String> {
        return input.split("|")[1].trim().split(" ").toList()
    }

    fun getPatternForNumber(number: Int): String {
        if (number == 1) {
            return signalPatterns.first { it.length == 2 }.toSortedSet().joinToString("")
        } else if (number == 4) {
            return signalPatterns.first { it.length == 4 }.toSortedSet().joinToString("")
        } else if (number == 7) {
            return signalPatterns.first { it.length == 3 }.toSortedSet().joinToString("")
        } else if (number == 8) {
            return signalPatterns.first { it.length == 7 }.toSortedSet().joinToString("")
        }
        TODO("Not yet implemented")
    }

    fun countInstancesOfNumberInOutput(number: Int): Int {
        val pattern = getPatternForNumber(number)
        return outputPatterns.count { it.toSortedSet().joinToString("") == pattern }
    }
}
