package day08

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import readInput

class Day08Tests {

    private val input = "acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf"

    @Test
    fun `Read input`() {
        val entry = Entry(input)
        assertEquals(listOf(
            "acedgfb", "cdfbe", "gcdfa", "fbcad", "dab", "cefabd", "cdfgeb", "eafb", "cagedb", "ab"
        ), entry.signalPatterns)
        assertEquals(listOf(
            "cdfeb", "fcadb", "cdfeb", "cdbaf"
        ), entry.outputPatterns)
    }

    @Test
    fun `Find patterns for easy numbers`() {
        val entry = Entry(input)
        assertEquals("ab", entry.getPatternForNumber(1))
        assertEquals("abef", entry.getPatternForNumber(4))
        assertEquals("abd", entry.getPatternForNumber(7))
        assertEquals("abcdefg", entry.getPatternForNumber(8))
    }

    @Test
    fun `Count instances of number in output pattern`() {
        val entry = Entry("be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe")
        assertEquals(0, entry.countInstancesOfNumberInOutput(1))
        assertEquals(1, entry.countInstancesOfNumberInOutput(4))
        assertEquals(0, entry.countInstancesOfNumberInOutput(7))
        assertEquals(1, entry.countInstancesOfNumberInOutput(8))
    }

    @Test
    fun `Count numbers in output patterns`() {
        val notes = Notes(readInput("Day08_test"))
        assertEquals(26, notes.countInstancesOfNumbersInOutput(listOf(1, 4, 7, 8)))
    }

}
