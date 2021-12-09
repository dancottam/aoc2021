package day09

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val heightMap = HeightMap(input)
        return heightMap.sumRiskLevelsForAllLowPoints()
    }

    fun part2(input: List<String>): Int {
        val heightMap = HeightMap(input)
        return heightMap.threeLargestBasinsMultipliedTogether()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day09_test")
    check(part1(testInput) == 15)
    check(part2(testInput) == 1134)

    val input = readInput("Day09")
    println(part1(input))
    println(part2(input))
}

class HeightMap(input: List<String>) {

    val coordinateHeights = readHeights(input)
    val lowPoints: List<Coordinate> = findLowPoints()

    private fun readHeights(rows: List<String>): Map<Coordinate, Int> {

        val heightMap = mutableMapOf<Coordinate, Int>()

        for (y in rows.indices) {
            val cols = rows[y].toCharArray()
            for (x in cols.indices) {
                heightMap[Coordinate(x, y)] = Character.getNumericValue(cols[x])
            }
        }

        return heightMap.toMap()
    }

    private fun findLowPoints(): List<Coordinate> {
        val lowPoints = mutableListOf<Coordinate>()

        for ((coordinate, height) in coordinateHeights) {
            if (isLowerThanAdjacentCoordinates(coordinate, height)) {
                lowPoints.add(coordinate)
            }
        }

        return lowPoints
    }

    private fun isLowerThanAdjacentCoordinates(coordinate: Coordinate, height: Int): Boolean {
        val adjacentHeights = findAdjacentCoordinateHeights(coordinate)
        return adjacentHeights.none { it <= height }
    }

    private fun findAdjacentCoordinateHeights(coordinate: Coordinate): List<Int> {
        return findAdjacentCoordinates(coordinate).mapNotNull { coordinateHeights[it] }
    }

    private fun findAdjacentCoordinates(coordinate: Coordinate): List<Coordinate> {
        return listOfNotNull(
            upCoordinate(coordinate),
            downCoordinate(coordinate),
            leftCoordinate(coordinate),
            rightCoordinate(coordinate)
        )
    }

    private fun upCoordinate(coordinate: Coordinate): Coordinate {
        return Coordinate(coordinate.x, coordinate.y-1)
    }

    private fun downCoordinate(coordinate: Coordinate): Coordinate {
        return Coordinate(coordinate.x, coordinate.y+1)
    }

    private fun leftCoordinate(coordinate: Coordinate): Coordinate {
        return Coordinate(coordinate.x-1, coordinate.y)
    }

    private fun rightCoordinate(coordinate: Coordinate): Coordinate {
        return Coordinate(coordinate.x+1, coordinate.y)
    }

    fun riskLevel(coordinate: Coordinate): Int? {
        return coordinateHeights[coordinate]?.plus(1)
    }

    fun sumRiskLevelsForAllLowPoints(): Int {
        return lowPoints.mapNotNull { riskLevel(it) }.sum()
    }

    fun findBasinSizeForLowPoint(coordinate: Coordinate): Int? {
        if (!lowPoints.contains(coordinate)) {
            return null
        }
        val basinCoordinates = traceBasin(coordinate)
        return basinCoordinates.size
    }

    private fun traceBasin(coordinate: Coordinate, basinCoordinates: MutableList<Coordinate> = mutableListOf()): List<Coordinate> {
        val height = coordinateHeights[coordinate]

        if (height == null || height == 9 || basinCoordinates.contains(coordinate)) {
            return basinCoordinates
        }

        basinCoordinates.add(coordinate)

        val adjacentCoordinates = findAdjacentCoordinates(coordinate)

        for (adjacentCoordinate in adjacentCoordinates) {
            if (coordinateIsHigher(adjacentCoordinate, height)) {
                traceBasin(adjacentCoordinate, basinCoordinates)
            }
        }
        return basinCoordinates
    }

    private fun coordinateIsHigher(coordinate: Coordinate, height: Int): Boolean {
        val coordinateHeight = coordinateHeights[coordinate]
        if (coordinateHeight != null) {
            return coordinateHeight > height
        }
        return false
    }

    fun threeLargestBasinsMultipliedTogether(): Int {
        val uniqueBasins = findBasins()
        val sortedBasinSizes = uniqueBasins.map { it.size }.sortedDescending()
        return sortedBasinSizes.take(3).reduce { acc, i -> acc * i }
    }

    private fun findBasins(): List<List<Coordinate>> {
        val basins = mutableListOf<List<Coordinate>>()
        for (lowPoint in lowPoints) {
            val lowPointBasin = traceBasin(lowPoint)
            basins.add(lowPointBasin)
        }
        return basins.toList()
    }

}

data class Coordinate(
    val x: Int,
    val y: Int
)