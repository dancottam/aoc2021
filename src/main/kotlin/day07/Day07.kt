package day07

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val crabSubs = CrabSubs.Part1(input[0])
        return crabSubs.cheapestFuelSpend() ?: 0
    }

    fun part2(input: List<String>): Int {
        val crabSubs = CrabSubs.Part2(input[0])
        return crabSubs.cheapestFuelSpend() ?: 0
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
    check(part2(testInput) == 168)

    val input = readInput("Day07")
    println(part1(input))
    println(part2(input))
}

abstract class CrabSubs(
    input: String
) {
    val positions = input.split(",").map { it.toInt() }.toList().sorted()

    abstract fun fuelSpendForPosition(target: Int): Int

    fun cheapestPosition(): Int? {
        return cheapestPositionSpend()?.position
    }

    fun cheapestFuelSpend(): Int? {
        return cheapestPositionSpend()?.spend
    }

    private fun cheapestPositionSpend(): PositionSpend? {
        var cheapestPositionSpend: PositionSpend? = null
        for (position in 0..positions.last()) {
            val positionSpend = PositionSpend(position, fuelSpendForPosition(position))
            if (cheapestPositionSpend == null || cheapestPositionSpend.spend > positionSpend.spend) {
                cheapestPositionSpend = positionSpend
            }
        }
        return cheapestPositionSpend
    }

    private data class PositionSpend(
        val position: Int,
        val spend: Int
    )

    class Part1(input: String) : CrabSubs(input = input) {

        override fun fuelSpendForPosition(target: Int): Int {
            var fuelSpend = 0
            for (position in positions) {
                if (position < target) {
                    fuelSpend += target - position
                } else if (position > target) {
                    fuelSpend += position - target
                }
            }
            return fuelSpend
        }

    }

    class Part2(input: String) : CrabSubs(input = input) {

        override fun fuelSpendForPosition(target: Int): Int {
            var fuelSpend = 0
            for (position in positions) {
                var distance = 0
                if (position < target) {
                    distance = target - position
                } else if (position > target) {
                    distance = position - target
                }
                if (distance > 0) {
                    var stepCost = 1
                    for (i in 1..distance) {
                        fuelSpend += stepCost
                        stepCost++
                    }
                }
            }
            return fuelSpend
        }
    }
}
