package day03

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val report = DiagnosticReport(input)
        return report.powerConsumption
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day03_test")
    check(part1(testInput) == 198)
//    check(part2(testInput) == 1)

    val input = readInput("Day03")
    println(part1(input))
//    println(part2(input))
}

class DiagnosticReport(input: List<String>) {
    val gammaRate = calculateGammaRate(input)
    val epsilonRate = calculateEpsilonRate(gammaRate)
    val powerConsumption = calculatePowerConsumption(gammaRate, epsilonRate)

    companion object {
        private fun calculateGammaRate(input: List<String>): String {
            return mostCommonBitAtEachPosition(input)
        }

        private fun calculateEpsilonRate(gammaRate: String): String {
            val oppositeBits = gammaRate.toCharArray().map { if (it == '0') '1' else '0' }
            return oppositeBits.joinToString("")
        }

        private fun calculatePowerConsumption(gammaRate: String, epsilonRate: String): Int {
            val gammaRateAsInt = convertBinaryToInt(gammaRate)
            val epsilonRateAsInt = convertBinaryToInt(epsilonRate)
            return gammaRateAsInt * epsilonRateAsInt
        }

        private fun convertBinaryToInt(binary: String): Int {
            return Integer.parseInt(binary, 2)
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
            val mostCommonBits = reports.values.map { mostCommonBit(it) }
            return mostCommonBits.joinToString("")
        }

        private fun mostCommonBit(it: BitReport): Char {
            return if (it.zeroes > it.ones) '0' else '1'
        }
    }

    private data class BitReport (
        var zeroes: Int = 0,
        var ones: Int = 0
    )
}