package day07

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day07Tests {

    private val input = "16,1,2,0,4,2,7,1,2,14"

    private val crabSubs = CrabSubs(input)

    @Test
    fun `Calculate fuel spend for position`() {
        assertEquals(41, crabSubs.fuelSpendForPosition(1))
        assertEquals(39, crabSubs.fuelSpendForPosition(3))
        assertEquals(71, crabSubs.fuelSpendForPosition(10))
    }

    @Test
    fun `Calculate cheapest fuel spend`() {
        assertEquals(37, crabSubs.cheapestFuelSpend())
    }

}