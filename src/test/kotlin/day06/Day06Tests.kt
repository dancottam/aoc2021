package day06

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day06Tests {

    val input = "3,4,3,1,2"

    @Test
    fun `New LanternFish has 8 days until spawn`() {
        val lanternFish = LanternFish()
        assertEquals(8, lanternFish.daysUntilSpawn())
    }

    @Test
    fun `Days until spawn is decremented when LanternFish ages`() {
        val lanternFish = LanternFish()
        lanternFish.ageByOneDayAndCheckSpawn()
        assertEquals(7, lanternFish.daysUntilSpawn())
    }

    @Test
    fun `Days until spawn resets to spawn rate when it reaches zero`() {
        val lanternFish = LanternFish(0)
        lanternFish.ageByOneDayAndCheckSpawn()

        assertEquals(lanternFish.spawnRate, lanternFish.daysUntilSpawn())
    }

    @Test
    fun `Aging by one day results in new LanternFish only if daysUntilSpawn is 1`() {
        val lanternFish = LanternFish(8)
        for (i in 8 downTo 1) {
            assertNull(lanternFish.ageByOneDayAndCheckSpawn())
        }
        assertNotNull(lanternFish.ageByOneDayAndCheckSpawn())
    }

    @Test
    fun `School correctly initialised from input`() {
        val school = School(input)
        assertEquals(5, school.populationCount())
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
