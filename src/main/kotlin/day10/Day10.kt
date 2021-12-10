package day10

import day10.Status.*
import readInput
import java.util.*
import kotlin.math.roundToInt

fun main() {
    fun part1(input: List<String>): Int {
        val lines = Lines(input)
        return lines.syntaxErrorScore
    }

    fun part2(input: List<String>): Long {
        val lines = Lines(input)
        return lines.middleCompletionScore()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day10_test")
    check(part1(testInput) == 26397)
    check(part2(testInput) == 288957L)

    val input = readInput("Day10")
    println(part1(input))
    println(part2(input))
}

class Lines(input: List<String>) {

    private val lines = parseLines(input)
    val syntaxErrorScore = calculateSyntaxErrorScore(lines)

    private fun parseLines(input: List<String>): List<Line> {
        val lines = mutableListOf<Line>()
        for(lineInput in input) {
            lines.add(Line(lineInput))
        }
        return lines.toList()
    }

    private fun calculateSyntaxErrorScore(lines: List<Line>): Int {
        val corruptedLines = lines.filter { it.status == CORRUPTED }
        return corruptedLines.sumOf { getIllegalCharacterScore(it.firstIllegalCharacter) }
    }

    private fun getIllegalCharacterScore(illegalCharacter: Char?): Int {
        return when (illegalCharacter) {
            ')' -> 3
            ']' -> 57
            '}' -> 1197
            '>' -> 25137
            else -> 0
        }
    }

    fun size(): Int {
        return lines.size
    }

    fun middleCompletionScore(): Long {
        val sortedScores = lines
            .filter { it.status == INCOMPLETE }
            .map { it.completionScore() }
            .sorted()
        return sortedScores[(sortedScores.size / 2).toDouble().roundToInt()]
    }
}

class Line(input: String) {

    val status: Status
    val firstIllegalCharacter: Char?
    val missingCloseChars: String

    init {
        val state = Stack<Char>()
        var firstIllegalCharacter: Char? = null
        for (char in input) {
            if (isChunkOpenChar(char)) {
                state.push(char)
            } else if (isMatchingOpenClose(state.peek(), char)) {
                state.pop()
            } else {
                firstIllegalCharacter = char
                break
            }
        }
        this.firstIllegalCharacter = firstIllegalCharacter
        if (firstIllegalCharacter != null) {
            status = CORRUPTED
            missingCloseChars = ""
        } else if (state.isNotEmpty()) {
            status = INCOMPLETE
            missingCloseChars = state.reversed().map {
                getCloseCharacterFor(it)
            }.joinToString("")
        } else {
            status = OK
            missingCloseChars = ""
        }
    }

    private fun getCloseCharacterFor(openChar: Char?): Char? {
        return when (openChar) {
            '(' -> ')'
            '[' -> ']'
            '{' -> '}'
            '<' -> '>'
            else -> null
        }
    }

    private fun isMatchingOpenClose(openChar: Char, closeChar: Char): Boolean {
        return (openChar == '(' && closeChar == ')')
                || (openChar == '[' && closeChar == ']')
                || (openChar == '{' && closeChar == '}')
                || (openChar == '<' && closeChar == '>')
    }

    private fun isChunkOpenChar(char: Char): Boolean {
        return OPEN_CHARS.contains(char)
    }

    fun completionScore(): Long {
        var total = 0L
        for (char in missingCloseChars) {
            total *= 5L
            total += charCompletionScore(char).toLong()
        }
        return total
    }

    private fun charCompletionScore(char: Char): Int {
        return when (char) {
            ')' -> 1
            ']' -> 2
            '}' -> 3
            '>' -> 4
            else -> 0
        }
    }

    companion object {
        val OPEN_CHARS = listOf('(', '[', '{', '<')
    }
}

enum class Status {
    OK, INCOMPLETE, CORRUPTED
}
