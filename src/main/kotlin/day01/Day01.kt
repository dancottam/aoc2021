package day01

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var prevDepth: Int? = null
        var numIncreased = 0
        for (line in input) {
            val currentDepth = line.toInt()
            if (prevDepth != null && prevDepth < currentDepth) {
                numIncreased++
            }
            prevDepth = currentDepth
        }
        return numIncreased
    }

    fun part2(input: List<String>): Int {
        var prevSum: Int? = null
        val last3Measurements = ArrayDeque<Int>(3)
        var numIncreased = 0
        for (line in input) {
            val nextDepth = line.toInt()
            if (last3Measurements.size < 3) {
                last3Measurements.add(nextDepth)
                if (last3Measurements.size < 3) {
                    continue
                }
            } else {
                last3Measurements.removeFirst()
                last3Measurements.addLast(nextDepth)
            }
            val newSum = last3Measurements.sum()
            if (prevSum != null && prevSum < newSum) {
                numIncreased++
            }
            prevSum = newSum
        }
        return numIncreased
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)
    check(part2(testInput) == 5)

    val input = readInput("Day01")
    println(part1(input))
    println(part2(input))
}
