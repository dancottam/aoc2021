package day11

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val grid = Grid(input)
        grid.stepTo(100)
        return grid.flashCount
    }

    fun part2(input: List<String>): Int {
        val grid = Grid(input)
        return grid.getNumStepsToSynchronizedFlash()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day11_test")
    check(part1(testInput) == 1656)
    check(part2(testInput) == 195)

    val input = readInput("Day11")
    println(part1(input))
    println(part2(input))
}

class Grid(input: List<String>) {

    var octopuses: MutableMap<Coordinate, Int> = parseInput(input)
    var flashCount = 0
    var numFlashesSinceLastStep = 0

    private fun parseInput(rows: List<String>): MutableMap<Coordinate, Int> {
        val octopuses = mutableMapOf<Coordinate, Int>()

        for (y in rows.indices) {
            val cols = rows[y].toCharArray()
            for (x in cols.indices) {
                octopuses[Coordinate(x, y)] = Character.getNumericValue(cols[x])
            }
        }

        return octopuses
    }

    fun octopusMap(): Map<Coordinate, Int> {
        return octopuses.toMap()
    }

    fun drawOctopusMap(): List<String> {
        val rows = mutableListOf<String>()
        val sortedMap = octopusMap().toSortedMap(compareBy(
            { it.y },
            { it.x }
        ))
        for ((coordinate, energy) in sortedMap) {
            if (rows.size < coordinate.y + 1) {
                rows.add(energy.toString())
            } else {
                rows[coordinate.y] = rows[coordinate.y] + energy
            }
        }
        return rows
    }

    fun step() {
        numFlashesSinceLastStep = 0
        octopuses.replaceAll { _, energy -> energy + 1 }
        checkFlashes()
        flashCount += numFlashesSinceLastStep
    }

    private fun checkFlashes() {
        val coords = octopuses.keys
        val readyToFlash = mutableListOf<Coordinate>()
        for (coord in coords) {
            val energy = octopuses[coord]
            if (energy != null) {
                if (energy > 9) {
                    readyToFlash.add(coord)
                }
            }
        }

        if (readyToFlash.isNotEmpty()) {
            for (coord in readyToFlash) {
                numFlashesSinceLastStep += 1
                octopuses[coord] = 0
                stepNeighbours(coord)
            }
            checkFlashes()
        }
    }

    private fun stepNeighbours(coord: Coordinate) {
        val neighbours = findNeighbours(coord)
        for (neighbour in neighbours) {
            val energy = octopuses[neighbour]
            if (energy != null && energy > 0) {
                octopuses[neighbour] = energy + 1
            }
        }
    }

    private fun findNeighbours(coord: Coordinate): List<Coordinate> {
        return listOf(
            Coordinate(coord.x-1, coord.y-1),
            Coordinate(coord.x-1, coord.y),
            Coordinate(coord.x-1, coord.y+1),
            Coordinate(coord.x, coord.y-1),
            Coordinate(coord.x, coord.y+1),
            Coordinate(coord.x+1, coord.y-1),
            Coordinate(coord.x+1, coord.y),
            Coordinate(coord.x+1, coord.y+1),
        )
    }

    fun stepTo(steps: Int) {
        for (i in 1..steps) {
            step()
        }
    }

    fun getNumStepsToSynchronizedFlash(): Int {
        var stepCount = 0
        while (numFlashesSinceLastStep < octopuses.size) {
            step()
            stepCount++
        }
        return stepCount
    }

}

data class Coordinate (
    val x: Int,
    val y: Int
)
