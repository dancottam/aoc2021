package day07

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val crabSubs = CrabSubs(input[0])
        return crabSubs.cheapestFuelSpend()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day07_test")
    check(part1(testInput) == 37)
//    check(part2(testInput) == 1)

    val input = readInput("Day07")
    println(part1(input))
//    println(part2(input))
}

class CrabSubs(
    input: String
) {
    private val positions = input.split(",").map { it.toInt() }.toList()

    fun fuelSpendForPosition(target: Int): Int {
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

    fun cheapestFuelSpend(): Int {
        val spendPerPosition = mutableMapOf<Int, Int>()
        for (position in positions) {
            spendPerPosition[position] = fuelSpendForPosition(position)
        }
        return spendPerPosition.toList().minByOrNull { (_, value) -> value }!!.second
    }
}
