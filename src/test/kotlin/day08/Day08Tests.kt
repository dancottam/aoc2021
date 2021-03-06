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
            "acedgfb".toSortedSet(),
            "cdfbe".toSortedSet(),
            "gcdfa".toSortedSet(),
            "fbcad".toSortedSet(),
            "dab".toSortedSet(),
            "cefabd".toSortedSet(),
            "cdfgeb".toSortedSet(),
            "eafb".toSortedSet(),
            "cagedb".toSortedSet(),
            "ab".toSortedSet()
        ), entry.signalPatterns)
        assertEquals(listOf(
            "cdfeb".toSortedSet(),
            "fcadb".toSortedSet(),
            "cdfeb".toSortedSet(),
            "cdbaf".toSortedSet()
        ), entry.outputPatterns)
    }

    @Test
    fun `Find patterns for numbers`() {
        val entry = Entry(input)
        assertEquals("ab", entry.patternForNumber(1))
        assertEquals("abef", entry.patternForNumber(4))
        assertEquals("abd", entry.patternForNumber(7))
        assertEquals("abcdefg", entry.patternForNumber(8))

        assertEquals("abcdeg", entry.patternForNumber(0))
        assertEquals("abcdef", entry.patternForNumber(9))
        assertEquals("abcdf", entry.patternForNumber(3))
        assertEquals("bcdef", entry.patternForNumber(5))
        assertEquals("bcdefg", entry.patternForNumber(6))
        assertEquals("acdfg", entry.patternForNumber(2))
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
        assertEquals(26, notes.countNumbersInAllOutputs(listOf(1, 4, 7, 8)))
    }

    @Test
    fun `Determine output value`() {
        val entry = Entry(input)
        assertEquals(5353, entry.outputValue)
    }

    @Test
    fun `Sum all output values`() {
        val notes = Notes(readInput("Day08_test"))
        assertEquals(61229, notes.sumAllOutputValues())
    }

}
