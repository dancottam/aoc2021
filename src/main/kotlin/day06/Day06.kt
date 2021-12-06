package day06

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val school = School(input[0])
        school.ageByDays(80)
        return school.populationCount()
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934)
//    check(part2(testInput) == 1)

    val input = readInput("Day06")
    println(part1(input))
//    println(part2(input))
}

class School(input: String) {

    private val lanternFishPopulation = initialiseLanternFish(input)

    private fun initialiseLanternFish(input: String): MutableList<LanternFish> {
        val lanternFish = mutableListOf<LanternFish>()
        for (initialSpawnRate in input.split(",")) {
            lanternFish.add(LanternFish(initialSpawnRate.toInt()))
        }
        return lanternFish
    }

    fun populationCount(): Int {
        return lanternFishPopulation.size
    }

    fun ageByDays(days: Int) {
        for (i in 1..days) {
            ageByOneDay()
        }
    }

    private fun ageByOneDay() {
        val newGeneration = mutableListOf<LanternFish>()
        for (lanternFish in lanternFishPopulation) {
            val newLanternFish = lanternFish.ageByOneDayAndCheckSpawn()
            if (newLanternFish != null) {
                newGeneration.add(newLanternFish)
            }
        }
        lanternFishPopulation.addAll(newGeneration)
    }
}

class LanternFish(initialDaysUntilSpawn: Int = DEFAULT_SPAWN_RATE  + 2) {

    private var daysUntilSpawn = initialDaysUntilSpawn
    val spawnRate = DEFAULT_SPAWN_RATE

    fun daysUntilSpawn(): Int {
        return daysUntilSpawn
    }

    fun ageByOneDayAndCheckSpawn(): LanternFish? {
        if (daysUntilSpawn == 0) {
            daysUntilSpawn = spawnRate
            return LanternFish()
        } else {
            daysUntilSpawn = daysUntilSpawn.dec()
        }
        return null
    }

    companion object {
        const val DEFAULT_SPAWN_RATE = 6
    }
}