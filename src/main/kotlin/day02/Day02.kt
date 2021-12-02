package day02

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        var submarineLocation = Part1.Location(
            horizontalPosition = 0,
            depth = 0
        )

        for (line in input) {
            val instruction = Part1.createInstruction(line)
            submarineLocation = instruction.apply(submarineLocation)
        }

        return submarineLocation.horizontalPosition * submarineLocation.depth
    }

    fun part2(input: List<String>): Int {
        var state = Part2.State(
            horizontalPosition = 0,
            depth = 0,
            aim = 0
        )

        for (line in input) {
            val instruction = Part2.createInstruction(line)
            state = instruction.apply(state)
        }

        return state.horizontalPosition * state.depth
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
    check(part2(testInput) == 900)

    val input = readInput("Day02")
    println(part1(input))
    println(part2(input))
}

