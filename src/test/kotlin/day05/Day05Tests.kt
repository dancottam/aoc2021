package day05

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readInput

class Day05Tests {

    private val input = readInput("Day05_test")

    @Test
    fun `Read line input`() {
        val input = "0,9 -> 5,9"
        val line = Line(input)
        assertEquals(Point(0, 9), line.start)
        assertEquals(Point(5, 9), line.end)
    }

    @Test
    fun `Line points are expanded correctly`() {
        val input1 = "1,1 -> 1,3"
        val line1 = Line(input1)
        assertEquals(listOf(
            Point(1, 1),
            Point(1, 2),
            Point(1, 3)
        ), line1.points)

        val input2 = "9,7 -> 7,7"
        val line2 = Line(input2)
        assertEquals(listOf(
            Point(9, 7),
            Point(8, 7),
            Point(7, 7)
        ), line2.points)

        val input3 = "1,1 -> 3,3"
        val line3 = Line(input3)
        assertEquals(listOf(
            Point(1, 1),
            Point(2, 2),
            Point(3, 3)
        ), line3.points)
    }

    @Test
    fun `Only consider horizontal or vertical lines`() {
        val lineDiagram = LineDiagram(input)
        assertEquals(10, lineDiagram.allLines().size)
        assertEquals(6, lineDiagram.horizontalOrVerticalLines().size)
    }

    @Test
    fun `Count points with overlapping horizontal or vertical lines`() {
        val lineDiagram = LineDiagram(input)
        assertEquals(5, lineDiagram.countPointsWithOverlappingHorizontalOrVerticalLines())
    }

    @Test
    fun `Count points with any overlapping lines (including diagonals)`() {
        val lineDiagram = LineDiagram(input)
        assertEquals(12, lineDiagram.countPointsWithAnyOverlappingLines())
    }
}