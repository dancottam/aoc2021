package day11

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day11Tests {

    private val input = """
        5483143223
        2745854711
        5264556173
        6141336146
        6357385478
        4167524645
        2176841721
        6882881134
        4846848554
        5283751526
        """.trimIndent().split("\n")

    @Test
    fun `Read input`() {
        val grid = Grid(input)
        assertEquals(100, grid.octopusMap().size)
        assertEquals(input, grid.drawOctopusMap())
    }

    @Test
    fun `Step grid`() {
        val beforeAnySteps = """
            11111
            19991
            19191
            19991
            11111
            """.trimIndent()
        val afterStep1 = """
            34543
            40004
            50005
            40004
            34543
        """.trimIndent()
        val afterStep2 = """
            45654
            51115
            61116
            51115
            45654
        """.trimIndent()

        val grid = Grid(beforeAnySteps.split("\n"))
        grid.step()
        assertEquals(afterStep1.split("\n"), grid.drawOctopusMap())

        grid.step()
        assertEquals(afterStep2.split("\n"), grid.drawOctopusMap())
    }

    @Test
    fun `Step to`() {
        val afterStep10 = """
            0481112976
            0031112009
            0041112504
            0081111406
            0099111306
            0093511233
            0442361130
            5532252350
            0532250600
            0032240000
        """.trimIndent()

        val grid = Grid(input)
        grid.stepTo(10)
        assertEquals(afterStep10.split("\n"), grid.drawOctopusMap())
    }

    @Test
    fun `Flash count`() {
        val grid = Grid(input)
        grid.stepTo(100)
        assertEquals(1656, grid.flashCount)
    }

    @Test
    fun `Get num steps for first synchronized flash`() {
        val grid = Grid(input)
        assertEquals(195, grid.getNumStepsToSynchronizedFlash())
    }
}
