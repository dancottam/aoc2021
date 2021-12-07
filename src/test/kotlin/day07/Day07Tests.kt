package day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day07Tests {

    private val input = "16,1,2,0,4,2,7,1,2,14"

    @Test
    fun `Part 1 calculate fuel spend for position`() {
        val part1CrabSubs = CrabSubs.Part1(input)
        assertEquals(41, part1CrabSubs.fuelSpendForPosition(1))
        assertEquals(39, part1CrabSubs.fuelSpendForPosition(3))
        assertEquals(71, part1CrabSubs.fuelSpendForPosition(10))
    }

    @Test
    fun `Part 1 cheapest position`() {
        assertEquals(2, CrabSubs.Part1(input).cheapestPosition())
    }

    @Test
    fun `Part 1 calculate cheapest fuel spend`() {
        assertEquals(37, CrabSubs.Part1(input).cheapestFuelSpend())
    }

    @Test
    fun `Part 2 calculate fuel spend for position`() {
        assertEquals(66, CrabSubs.Part2("16").fuelSpendForPosition(5))
        assertEquals(10, CrabSubs.Part2("1").fuelSpendForPosition(5))
        assertEquals(6, CrabSubs.Part2("2").fuelSpendForPosition(5))
        assertEquals(15, CrabSubs.Part2("0").fuelSpendForPosition(5))
        assertEquals(1, CrabSubs.Part2("4").fuelSpendForPosition(5))
        assertEquals(6, CrabSubs.Part2("2").fuelSpendForPosition(5))
        assertEquals(3, CrabSubs.Part2("7").fuelSpendForPosition(5))
        assertEquals(10, CrabSubs.Part2("1").fuelSpendForPosition(5))
        assertEquals(6, CrabSubs.Part2("2").fuelSpendForPosition(5))
        assertEquals(45, CrabSubs.Part2("14").fuelSpendForPosition(5))
    }

    @Test
    fun `Part 2 cheapest position`() {
        assertEquals(5, CrabSubs.Part2(input).cheapestPosition())
    }

    @Test
    fun `Part 2 calculate cheapest fuel spend`() {
        assertEquals(168, CrabSubs.Part2(input).cheapestFuelSpend())
    }

}