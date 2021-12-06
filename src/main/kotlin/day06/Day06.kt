package day06

import readInput

fun main() {
    fun part1(input: List<String>): Long {
        val school = School(input[0])
        school.ageByDays(80)
        return school.populationCount()
    }

    fun part2(input: List<String>): Long {
        val school = School(input[0])
        school.ageByDays(256)
        return school.populationCount()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day06_test")
    check(part1(testInput) == 5934L)
    check(part2(testInput) == 26984457539L)

    val input = readInput("Day06")
    println(part1(input))
    println(part2(input))
}

class School(input: String) {

    private var numFishAtEachSpawnRate = initializeSpawnRateMap(input)

    private fun initializeSpawnRateMap(input: String): MutableMap<Int, Long> {
        val spawnRateMap = mutableMapOf<Int, Long>()
        for (rate in input.split(",")) {
            spawnRateMap.merge(rate.toInt(), 1) { oldValue: Long, newValue: Long ->
                oldValue + newValue
            }
        }
        return spawnRateMap
    }

    fun numFishPerSpawnRate(): Map<Int, Long> {
        return numFishAtEachSpawnRate.toSortedMap()
    }

    fun populationCount(): Long {
        return numFishAtEachSpawnRate.values.sum()
    }

    fun ageByDays(days: Int) {
        for (i in 1..days) {
            ageByOneDay()
        }
    }

    private fun ageByOneDay() {

        val newSpawnRateCounts = mutableMapOf<Int, Long>()

        for ((rate, numFishAtCurrentRate) in numFishAtEachSpawnRate) {
            val newRate = calculateNewSpawnRate(rate)

            newSpawnRateCounts.merge(newRate, numFishAtCurrentRate) { existingValue: Long, newValue: Long ->
                existingValue + newValue
            }

            if (rate == 0) {
                newSpawnRateCounts[INITIAL_SPAWN_RATE] = numFishAtCurrentRate
            }
        }

        numFishAtEachSpawnRate = newSpawnRateCounts
    }

    private fun calculateNewSpawnRate(rate: Int): Int {
        return if (rate > 0) rate - 1 else DEFAULT_SPAWN_RATE
    }


    companion object {
        const val INITIAL_SPAWN_RATE = 8
        const val DEFAULT_SPAWN_RATE = 6
    }
}