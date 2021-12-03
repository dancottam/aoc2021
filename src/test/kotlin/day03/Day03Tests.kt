package day03

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day03Tests {

    private val input = listOf("00100", "11110", "10110", "10111", "10101", "01111", "00111", "11100", "10000", "11001", "00010", "01010")
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
    fun `Power consumption is gamma multiplied by epsilon`() {
        assertEquals(198, report.powerConsumption)
    }

}