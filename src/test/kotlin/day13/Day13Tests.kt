package day13

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class Day13Tests {

    private val input ="""
        6,10
        0,14
        9,10
        0,3
        10,4
        4,11
        6,0
        6,12
        4,1
        0,13
        10,12
        3,4
        3,0
        8,4
        1,10
        2,14
        8,10
        9,0

        fold along y=7
        fold along x=5
    """.trimIndent().split("\n")

    @Test
    fun `Read input`() {
        val page = Page.fromInput(input)
        assertEquals(18, page.dots.size)
        assertEquals(10, page.maxX)
        assertEquals(14, page.maxY)
        assertEquals(listOf(
            "y=7",
            "x=5"
        ), page.remainingFolds)
    }

    @Test
    fun `Perform fold`() {
        val page = Page.fromInput(input)
        val pageAfterFirstFold = page.fold()

        assertEquals("""
            #.##..#..#.
            #...#......
            ......#...#
            #...#......
            .#.#..#.###
            ...........
            ...........
        """.trimIndent(), pageAfterFirstFold.plotDots())
        assertEquals(listOf(
            "x=5"
        ), pageAfterFirstFold.remainingFolds)
    }

    @Test
    fun `Fold all`() {
        val page = Page.fromInput(input)
        val foldedPage = page.foldAll()

        assertEquals("""
            #####
            #...#
            #...#
            #...#
            #####
            .....
            .....
        """.trimIndent(), foldedPage.plotDots())
        assertTrue(foldedPage.remainingFolds.isEmpty())
    }
}