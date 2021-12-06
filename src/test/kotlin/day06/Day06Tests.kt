package day06

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day06Tests {

    private val input = "3,4,3,1,2"

    @Test
    fun `School correctly initialised from input`() {
        val school = School(input)
        assertEquals(5, school.populationCount())
        assertEquals(
            sortedMapOf<Int, Long>(
            1 to 1,
            2 to 1,
            3 to 2,
            4 to 1
        ), school.numFishPerSpawnRate())
    }

    @Test
    fun `Population increases correctly when aging`() {
        val school = School(input)
        assertEquals(5, school.populationCount())
        school.ageByDays(18)
        assertEquals(26, school.populationCount())
        school.ageByDays(62)
        assertEquals(5934, school.populationCount())
    }
}
