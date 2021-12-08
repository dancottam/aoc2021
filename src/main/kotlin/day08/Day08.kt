package day08

import readInput
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        val notes = Notes(input)
        return notes.countNumbersInAllOutputs(listOf(1, 4, 7, 8))
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

class Notes(input: List<String>) {

    private val entries = createEntries(input)

    private fun createEntries(input: List<String>): List<Entry> {
        return input.map { Entry(it) }
    }

    fun countNumbersInAllOutputs(numbers: List<Int>): Int {
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

        val zeroPattern = determineZeroPattern(sevenPattern, fourPattern)
        numberPatterns[0] = zeroPattern

        val ninePattern = determineNinePattern(fourPattern, eightPattern)
        numberPatterns[9] = ninePattern

        val threePattern = determineThreePattern(onePattern, ninePattern)
        numberPatterns[3] = threePattern

        val fivePattern = determineFivePattern(onePattern, ninePattern)
        numberPatterns[5] = fivePattern

        val sixPattern = determineSixPattern(fivePattern, onePattern)
        numberPatterns[6] = sixPattern

        val twoPattern = determineTwoPattern(threePattern, fivePattern)
        numberPatterns[2] = twoPattern

        return numberPatterns.toSortedMap()
    }

    private fun determineZeroPattern(sevenPattern: SortedSet<Char>, fourPattern: SortedSet<Char>): SortedSet<Char> {
        return signalPatterns.first { segments ->
            segments.size == 6
                    && segments.containsAll(sevenPattern)
                    && !segments.containsAll(fourPattern)
        }
    }

    private fun determineNinePattern(fourPattern: SortedSet<Char>, eightPattern: SortedSet<Char>): SortedSet<Char> {
        return signalPatterns.first { segments ->
            segments.size == 6
                    && segments.containsAll(fourPattern)
                    && !segments.containsAll(eightPattern)
        }
    }

    private fun determineThreePattern(onePattern: SortedSet<Char>, ninePattern: SortedSet<Char>): SortedSet<Char> {
        return signalPatterns.first { segments ->
            segments.size == 5
                    && segments.containsAll(onePattern)
                    && ninePattern.containsAll(segments)
        }
    }

    private fun determineFivePattern(onePattern: SortedSet<Char>, ninePattern: SortedSet<Char>): SortedSet<Char> {
        return signalPatterns.first { segments ->
            segments.size == 5
                    && !segments.containsAll(onePattern)
                    && ninePattern.containsAll(segments)
        }
    }

    private fun determineSixPattern(fivePattern: SortedSet<Char>, onePattern: SortedSet<Char>): SortedSet<Char> {
        return signalPatterns.first { segments ->
            segments.size == 6
                    && segments.containsAll(fivePattern)
                    && !segments.containsAll(onePattern)
        }
    }

    private fun determineTwoPattern(threePattern: SortedSet<Char>, fivePattern: SortedSet<Char>): SortedSet<Char> {
        return signalPatterns.first { segments ->
            segments.size == 5
                    && segments != threePattern
                    && segments != fivePattern
        }
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
