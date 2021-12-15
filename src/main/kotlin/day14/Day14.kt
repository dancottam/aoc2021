package day14

import readInput
import java.util.*

fun main() {
    fun part1(input: List<String>): Long {
        val polymer = Polymer.fromInput(input)
        val newPolymer = polymer.stepFor(10)
        return newPolymer.mostCommonCountMinusLeastCommonCount()
    }

    fun part2(input: List<String>): Long {
        val polymer = Polymer.fromInput(input)
        val newPolymer = polymer.stepFor(40)
        return newPolymer.mostCommonCountMinusLeastCommonCount()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day14_test")
    check(part1(testInput) == 1588L)
    check(part2(testInput) == 2188189693529L)

    val input = readInput("Day14")
    println(part1(input))
    println(part2(input))
}

class Polymer(
    val pairCounts: SortedMap<String, Long>,
    val charCounts: SortedMap<Char, Long>,
    val pairInsertions: List<PairInsertion>
) {

    fun stepFor(stepCount: Int): Polymer {
        var newPolymer = this
        for (i in 1..stepCount) {
            newPolymer = newPolymer.step()
        }
        return newPolymer
    }

    fun step(): Polymer {
        val newPairCounts = mutableMapOf<String, Long>()
        val newCharCounts = charCounts.toMutableMap()

        for ((pair, charToInsert, newPairs) in pairInsertions) {
            val pairCount = pairCounts[pair]
            if (pairCount != null && pairCount > 0) {

                newCharCounts.merge(charToInsert, pairCount, Long::plus)

                for (newPair in newPairs) {
                    newPairCounts.merge(newPair, pairCount, Long::plus)
                }
            }
        }

        return Polymer(newPairCounts.toSortedMap(), newCharCounts.toSortedMap(), pairInsertions)
    }

    fun countElements(element: Char): Long {
        return charCounts[element] ?: 0
    }

    fun mostCommonCountMinusLeastCommonCount(): Long {
        val min = charCounts.minByOrNull { it.value }?.value
        val max = charCounts.maxByOrNull { it.value }?.value

        if (min != null && max != null) {
            return max.minus(min)
        }
        return 0
    }

    fun length(): Long {
        return charCounts.values.sum()
    }

    companion object {

        fun fromInput(input: List<String>): Polymer {
            val template = input.first()
            val instructions = mutableListOf<PairInsertion>()

            for (i in 1 until input.size) {
                val line = input[i]
                if (line.isBlank()) {
                    continue
                }
                val pair = line.substringBefore(" -> ")
                val charToInsert = line.last()
                instructions.add(PairInsertion(pair, charToInsert))
            }

            val charCounts = mutableMapOf<Char, Long>()
            val pairCounts = mutableMapOf<String, Long>()

            var prevChar:Char? = null
            for (char in template) {
                charCounts.merge(char, 1, Long::plus)

                if (prevChar == null) {
                    prevChar = char
                    continue
                }

                val pair = String(charArrayOf(prevChar, char))

                pairCounts.merge(pair, 1, Long::plus)

                prevChar = char
            }

            return Polymer(pairCounts.toSortedMap(), charCounts.toSortedMap(), instructions)
        }


    }
}

data class PairInsertion(
    val pair: String,
    val charToInsert: Char,
    val newPairs: List<String> = createNewPairs(pair, charToInsert)
) {
    companion object {
        private fun createNewPairs(pair: String, charToInsert: Char): List<String> {
            val newPairs = mutableListOf<String>()

            newPairs.add(String(charArrayOf(pair.first(), charToInsert)))
            newPairs.add(String(charArrayOf(charToInsert, pair.last())))

            return newPairs.toList()
        }
    }
}