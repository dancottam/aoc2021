package day03

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val report = DiagnosticReport(input)
        return report.powerConsumption
    }

    fun part2(input: List<String>): Int {
        val report = DiagnosticReport(input)
        return report.lifeSupportRating
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
    check(part2(testInput) == 230)

    val input = readInput("Day03")
    println(part1(input))
    println(part2(input))
}


class DiagnosticReport(input: List<String>) {
    val gammaRate = calculateGammaRate(input)
    val epsilonRate = calculateEpsilonRate(gammaRate)
    val powerConsumption = calculatePowerConsumption(gammaRate, epsilonRate)
    val oxygenGeneratorRating = calculateOxygenGeneratorRating(input)
    val co2ScrubberRating = calculateCO2ScrubberRating(input)
    val lifeSupportRating = calculateLifeSupportRating(oxygenGeneratorRating, co2ScrubberRating)


    companion object {
        fun calculateGammaRate(input: List<String>): String {
            return mostCommonBitAtEachPosition(input)
        }

        private fun mostCommonBitAtEachPosition(input: List<String>): String {
            val reports = mutableMapOf<Int, BitReport>()

            for (bits in input) {
                for (i in bits.indices) {
                    reports.putIfAbsent(i, BitReport()).also {
                        if (it != null) {
                            val bit = bits[i]
                            if (bit == '1') {
                                it.ones++
                            } else {
                                it.zeroes++
                            }
                        }
                    }
                }
            }
            val mostCommonBits = reports.values.map { it.mostCommonBit() }
            return mostCommonBits.joinToString("")
        }

        fun calculateEpsilonRate(gammaRate: String): String {
            return invertBitsAtEachPosition(gammaRate)
        }

        private fun invertBitsAtEachPosition(bits: String): String {
            val oppositeBits = bits.toCharArray().map { if (it == '0') '1' else '0' }
            return oppositeBits.joinToString("")
        }

        fun calculatePowerConsumption(gammaRate: String, epsilonRate: String): Int {
            val gammaRateAsInt = convertBinaryToInt(gammaRate)
            val epsilonRateAsInt = convertBinaryToInt(epsilonRate)
            return gammaRateAsInt * epsilonRateAsInt
        }

        fun calculateOxygenGeneratorRating(input: List<String>): String {
            var filteredInput = input

            var position = 0
            while (filteredInput.size > 1) {
                val mostCommonBit = mostCommonBitAtPosition(filteredInput, position)
                filteredInput = filterInputByBitAtPosition(filteredInput, position, mostCommonBit)
                position++
            }
            return filteredInput[0]
        }

        fun calculateCO2ScrubberRating(input: List<String>): String {
            var filteredInput = input

            var position = 0
            while (filteredInput.size > 1) {
                val leastCommonBit = leastCommonBitAtPosition(filteredInput, position)
                filteredInput = filterInputByBitAtPosition(filteredInput, position, leastCommonBit)
                position++
            }
            return filteredInput[0]
        }

        private fun mostCommonBitAtPosition(input: List<String>, position: Int): Char {
            val report = createBitReportForPosition(input, position)
            return report.mostCommonBit() ?: '1'
        }

        private fun leastCommonBitAtPosition(input: List<String>, position: Int): Char {
            val report = createBitReportForPosition(input, position)
            return report.leastCommonBit() ?: '0'
        }

        private fun createBitReportForPosition(input: List<String>, position: Int): BitReport {
            val report = BitReport()
            for (bits in input) {
                if (position < bits.length) {
                    val bit = bits[position]
                    if (bit == '1') {
                        report.ones++
                    } else {
                        report.zeroes++
                    }
                }
            }
            return report
        }

        private fun filterInputByBitAtPosition(input: List<String>, position: Int, bit: Char): List<String> {
            return input.filter { it[position] == bit }
        }

        fun calculateLifeSupportRating(oxygenGeneratorRating: String, co2ScrubberRating: String): Int {
            val oxygenGeneratorRatingAsInt = convertBinaryToInt(oxygenGeneratorRating)
            val co2ScrubberRatingAsInt = convertBinaryToInt(co2ScrubberRating)
            return oxygenGeneratorRatingAsInt * co2ScrubberRatingAsInt
        }

        private fun convertBinaryToInt(binary: String): Int {
            return Integer.parseInt(binary, 2)
        }
    }

    private data class BitReport(
        var zeroes: Int = 0,
        var ones: Int = 0
    ) {
        fun mostCommonBit(): Char? {
            return if (zeroes == ones) {
                null
            } else if (zeroes > ones) {
                '0'
            } else {
                '1'
            }
        }

        fun leastCommonBit(): Char? {
            return if (zeroes == ones) {
                null
            } else if (zeroes < ones) {
                '0'
            } else {
                '1'
            }
        }
    }
}