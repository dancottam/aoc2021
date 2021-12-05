package day05

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val lineDiagram = LineDiagram(input)
        return lineDiagram.countPointsWithOverlappingHorizontalOrVerticalLines()
    }

    fun part2(input: List<String>): Int {
        val lineDiagram = LineDiagram(input)
        return lineDiagram.countPointsWithAnyOverlappingLines()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day05_test")
    check(part1(testInput) == 5)
    check(part2(testInput) == 12)

    val input = readInput("Day05")
    println(part1(input))
    println(part2(input))
}

data class Point (val x: Int, val y: Int)

class Line (
    input: String
) {
    private val inputSplit = input.split(" -> ")
    val start = parsePoint(inputSplit[0])
    val end = parsePoint(inputSplit[1])
    val points = expandPoints(start, end)

    private fun parsePoint(input: String): Point {
        val inputSplit = input.split(",")
        return Point(inputSplit[0].toInt(), inputSplit[1].toInt())
    }

    private fun expandPoints(start: Point, end: Point): List<Point> {
        val expandedPoints = mutableListOf<Point>()

        if (isHorizontal()) {
            for (x in calculateRange(start.x, end.x)) {
                expandedPoints.add(Point(x, start.y))
            }
        } else if (isVertical()) {
            for (y in calculateRange(start.y, end.y)) {
                expandedPoints.add(Point(start.x, y))
            }
        } else {
            val xRange = calculateRange(start.x, end.x)
            val yRange = calculateRange(start.y, end.y)

            for (i in xRange.indices) {
                expandedPoints.add(Point(xRange[i], yRange[i]))
            }
        }

        return expandedPoints.toList()
    }

    private fun calculateRange(start: Int, end: Int): List<Int> {
        if (start > end) {
            return start.downTo(end).toList()
        }
        return start.rangeTo(end).toList()
    }

    fun isHorizontal(): Boolean {
        return start.y == end.y
    }

    fun isVertical(): Boolean {
        return start.x == end.x
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

    fun countPointsWithOverlappingHorizontalOrVerticalLines(): Int {
        return horizontalOrVerticalLinesPerPoint().filterValues{ it > 1 }.size
    }

    private fun horizontalOrVerticalLinesPerPoint(): Map<Point, Int> {
        return countLinesPerPoint(horizontalOrVerticalLines())
    }

    fun countPointsWithAnyOverlappingLines(): Int {
        return linesPerPoint(lines).filterValues { it > 1 }.size
    }

    private fun linesPerPoint(lines: List<Line>): Map<Point, Int> {
        return countLinesPerPoint(lines)
    }

    private fun countLinesPerPoint(lines: List<Line>): Map<Point, Int> {
        val numLinesPerPoint = mutableMapOf<Point, Int>()
        for (line in lines) {
            for (coordinate in line.points) {
                if (numLinesPerPoint.containsKey(coordinate)) {
                    numLinesPerPoint[coordinate] = numLinesPerPoint[coordinate]!!.inc()
                } else {
                    numLinesPerPoint[coordinate] = 1
                }
            }
        }
        return numLinesPerPoint.toMap()
    }
}
