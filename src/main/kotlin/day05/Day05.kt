package day05

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val lineDiagram = LineDiagram(input)
        return lineDiagram.countPointsWithOverlappingHorizontalOrVerticalLines()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
//    check(part2(testInput) == 1)

    val input = readInput("Day05")
    println(part1(input))
//    println(part2(input))
}

data class Point (val x: Int, val y: Int)

class Line (
    input: String
) {
    private val inputSplit = input.split(" -> ")
    val start = parsePoint(inputSplit[0])
    val end = parsePoint(inputSplit[1])
    val coordinates = expandPoints(start, end)

    private fun parsePoint(input: String): Point {
        val inputSplit = input.split(",")
        return Point(inputSplit[0].toInt(), inputSplit[1].toInt())
    }

    private fun expandPoints(start: Point, end: Point): List<Point> {
        val xRange = calculateRange(start.x, end.x)
        val yRange = calculateRange(start.y, end.y)

        val expandedPoints = mutableListOf<Point>()
        for (x in xRange) {
            for (y in yRange) {
                expandedPoints.add(Point(x, y))
            }
        }

        return expandedPoints.toList()
    }

    private fun calculateRange(start: Int, end: Int): IntProgression {
        if (start > end) {
            return start.downTo(end)
        }
        return start.rangeTo(end)
    }

    fun isHorizontal(): Boolean {
        return start.x == end.x
    }

    fun isVertical(): Boolean {
        return start.y == end.y
    }
}

class LineDiagram (input: List<String>) {
    private val lines = populateLines(input)

    private fun populateLines(input: List<String>): List<Line> {
        val lines = mutableListOf<Line>()
        for (lineSegment in input) {
            lines.add(Line(lineSegment))
        }
        return lines.toList()
    }

    fun allLines(): List<Line> {
        return lines
    }

    fun horizontalOrVerticalLines(): List<Line> {
        return lines.filter { it.isHorizontal() || it.isVertical() }
    }

    fun horizontalOrVerticalLinesPerPoint(): Map<Point, Int> {
        val numLinesPerPoint = mutableMapOf<Point, Int>()

        for (line in horizontalOrVerticalLines()) {
            for (coordinate in line.coordinates) {
                if (numLinesPerPoint.containsKey(coordinate)) {
                    numLinesPerPoint[coordinate] = numLinesPerPoint[coordinate]!! + 1
                } else {
                    numLinesPerPoint[coordinate] = 1
                }
            }
        }
        return numLinesPerPoint.toMap()
    }

    fun countPointsWithOverlappingHorizontalOrVerticalLines(): Int {
        return horizontalOrVerticalLinesPerPoint().filterValues{ it > 1 }.size
    }
}
