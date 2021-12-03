package day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Tests {

    private val input = listOf(
        "00100",
        "11110",
        "10110",
        "10111",
        "10101",
        "01111",
        "00111",
        "11100",
        "10000",
        "11001",
        "00010",
        "01010"
    )
    private val report = DiagnosticReport(input)

    @Test
    fun `Gamma rate is most common bit at each position`() {
        assertEquals("10110", report.gammaRate)
    }

    @Test
    fun `Epsilon rate is least common bit at each position`() {
        assertEquals("01001", report.epsilonRate)
    }

    @Test
    fun `Power consumption is decimal(gamma) multiplied by decimal(epsilon)`() {
        assertEquals(198, report.powerConsumption)
    }

    @Test
    fun `Oxygen generator rating is value that matches the 'most common' bit criteria`() {
        assertEquals("10111", report.oxygenGeneratorRating)
    }

    @Test
    fun `CO2 scrubber rating is the value that matches the 'least common' bit criteria`() {
        assertEquals("01010", report.co2ScrubberRating)
    }

    @Test
    fun `Life support rating is decimal(oxygen generator rating) multiplied by decimal(CO2 scrubber rating)`() {
        assertEquals(230, report.lifeSupportRating)
    }

}