package day08

import readInput
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        val notes = Notes(input)
        return notes.countInstancesOfNumbersInOutput(listOf(1, 4, 7, 8))
    }

    fun part2(input: List<String>): Long {
        val notes = Notes(input)
        return notes.sumAllOutputValues()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day08_test")
    check(part1(testInput) == 26)
    check(part2(testInput) == 61229L)

    val input = readInput("Day08")
    println(part1(input))
    println(part2(input))
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

    fun sumAllOutputValues(): Long {
        var total = 0L
        for (entry in entries) {
            total = total.plus(entry.outputValue)
        }
        return total
    }
}

class Entry(input: String) {

    val signalPatterns = parseSignalPatterns(input)
    val outputPatterns = parseOutputPatterns(input)

    private val numberPatterns = createNumberPatternMap()
    val outputValue = calculateOutputValue()

    private fun parseSignalPatterns(input: String): List<SortedSet<Char>> {
        return input.split("|")[0].trim().split(" ").map {
            it.toSortedSet()
        }
    }

    private fun parseOutputPatterns(input: String): List<SortedSet<Char>> {
        return input.split("|")[1].trim().split(" ").map {
            it.toSortedSet()
        }
    }

    private fun createNumberPatternMap(): Map<Int, SortedSet<Char>> {
        val numberPatterns = mutableMapOf<Int, SortedSet<Char>>()

        val onePattern = signalPatterns.first { it.size == 2 }
        numberPatterns[1] = onePattern
        val fourPattern = signalPatterns.first { it.size == 4 }
        numberPatterns[4] = fourPattern
        val sevenPattern = signalPatterns.first { it.size == 3 }
        numberPatterns[7] = sevenPattern
        val eightPattern = signalPatterns.first { it.size == 7 }
        numberPatterns[8] = eightPattern

        val zeroPattern = signalPatterns.first { segments ->
            segments.size == 6
                    && segments.containsAll(sevenPattern)
                    && !segments.containsAll(fourPattern)
        }
        numberPatterns[0] = zeroPattern

        val ninePattern = signalPatterns.first { segments ->
            segments.size == 6
                    && segments.containsAll(fourPattern)
                    && !segments.containsAll(eightPattern)
        }
        numberPatterns[9] = ninePattern

        val threePattern = signalPatterns.first { segments ->
            segments.size == 5
                    && segments.containsAll(onePattern)
                    && ninePattern.containsAll(segments)
        }
        numberPatterns[3] = threePattern

        val fivePattern = signalPatterns.first { segments ->
            segments.size  == 5
                    && !segments.containsAll(onePattern)
                    && ninePattern.containsAll(segments)
        }
        numberPatterns[5] = fivePattern

        val sixPattern = signalPatterns.first { segments ->
            segments.size == 6
                    && segments.containsAll(fivePattern)
                    && !segments.containsAll(onePattern)
        }
        numberPatterns[6] = sixPattern

        val twoPattern = signalPatterns.first { segments ->
            segments.size == 5
                    && segments != threePattern
                    && segments != fivePattern
        }
        numberPatterns[2] = twoPattern

        return numberPatterns.toSortedMap()
    }

    fun patternForNumber(number: Int): String {
        return numberPatterns[number]?.joinToString("") ?: ""
    }

    fun countInstancesOfNumberInOutput(number: Int): Int {
        val pattern = patternForNumber(number)
        return outputPatterns.count { it.joinToString("") == pattern }
    }

    private fun calculateOutputValue(): Int {
        var outputValue = ""
        for (pattern in outputPatterns) {
            outputValue += numberForPattern(pattern)
        }
        return outputValue.toInt()
    }

    private fun numberForPattern(pattern: SortedSet<Char>): Int {
        return numberPatterns.filterValues { it == pattern }.keys.first()
    }
}
