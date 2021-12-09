package day09

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day09Tests {

    private val input = listOf(
        "2199943210",
        "3987894921",
        "9856789892",
        "8767896789",
        "9899965678"
    )

    private val heightMap = HeightMap(input)

    @Test
    fun `Load height map`() {
        assertEquals(50, heightMap.coordinateHeights.size)
    }

    @Test
    fun `Find low points`() {
        assertEquals(listOf(
            Coordinate(1, 0),
            Coordinate(9, 0),
            Coordinate(2, 2),
            Coordinate(6, 4)
        ), heightMap.lowPoints)
    }

    @Test
    fun `Risk level for coordinate`() {
        assertEquals(2, heightMap.riskLevel(Coordinate(1, 0)))
        assertEquals(1, heightMap.riskLevel(Coordinate(9, 0)))
        assertEquals(6, heightMap.riskLevel(Coordinate(2, 2)))
        assertEquals(6, heightMap.riskLevel(Coordinate(6, 4)))
    }

    @Test
    fun `Sum low point risk level`() {
        assertEquals(15, heightMap.sumRiskLevelsForAllLowPoints())
    }

    @Test
    fun `Get basin size for low point  coordinate`() {
        assertEquals(3, heightMap.findBasinSizeForLowPoint(Coordinate(1, 0)))
        assertEquals(9, heightMap.findBasinSizeForLowPoint(Coordinate(9, 0)))
        assertEquals(14, heightMap.findBasinSizeForLowPoint(Coordinate(2, 2)))
        assertEquals(9, heightMap.findBasinSizeForLowPoint(Coordinate(6, 4)))
    }

    @Test
    fun `Three largest basins multiplied together`() {
        assertEquals(1134, heightMap.threeLargestBasinsMultipliedTogether())
    }

}