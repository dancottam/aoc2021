package day14

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day14Tests {

    private val input = """
        NNCB

        CH -> B
        HH -> N
        CB -> H
        NH -> C
        HB -> C
        HC -> B
        HN -> C
        NN -> C
        BH -> H
        NC -> B
        NB -> B
        BN -> B
        BB -> N
        BC -> B
        CC -> N
        CN -> C
    """.trimIndent().split("\n")

    @Test
    fun `Read input`() {
        val polymer = Polymer.fromInput(input)
        assertEquals(
            sortedMapOf<String, Long>(
                "CB" to 1,
                "NN" to 1,
                "NC" to 1,
        ), polymer.pairCounts)
        assertEquals(16, polymer.pairInsertions.size)
    }

    @Test
    fun `Step`() {
        val polymer = Polymer.fromInput(input)
        val newPolymer = polymer.step()
        assertEquals(
            sortedMapOf<String, Long>(
                "CH" to 1,
                "HB" to 1,
                "NC" to 1,
                "NB" to 1,
                "BC" to 1,
                "CN" to 1,
        ), newPolymer.pairCounts)
        assertEquals(sortedMapOf<Char, Long>(
            'C' to 2,
            'H' to 1,
            'B' to 2,
            'N' to 2
        ), newPolymer.charCounts)
    }

    @Test
    fun `Step for`() {
        val polymer = Polymer.fromInput(input)
        val newPolymer = polymer.stepFor(2)
        assertEquals(
            sortedMapOf<String, Long>(
                "BB" to 2,
                "BC" to 2,
                "BH" to 1,
                "CB" to 2,
                "CC" to 1,
                "CN" to 1,
                "HC" to 1,
                "NB" to 2
        ), newPolymer.pairCounts)
        assertEquals(sortedMapOf<Char, Long>(
            'C' to 4,
            'H' to 1,
            'B' to 6,
            'N' to 2
        ), newPolymer.charCounts)
    }

    @Test
    fun `Polymer length`() {
        val polymer = Polymer.fromInput(input)
        assertEquals(4, polymer.length())

        val polymerAfterStep1 = polymer.step()
        assertEquals(7, polymerAfterStep1.length())

        val polymerAfterStep2 = polymerAfterStep1.step()
        assertEquals(13, polymerAfterStep2.length())

        val polymerAfterStep3 = polymerAfterStep2.step()
        assertEquals(25, polymerAfterStep3.length())
    }

    @Test
    fun `Count elements`() {
        val polymer = Polymer.fromInput(input)
        val newPolymer = polymer.stepFor(10)
        assertEquals(1749, newPolymer.countElements('B'))
        assertEquals(298, newPolymer.countElements('C'))
        assertEquals(161, newPolymer.countElements('H'))
        assertEquals(865, newPolymer.countElements('N'))
    }

    @Test
    fun `Most common element count minus least common element count`() {
        val polymer = Polymer.fromInput(input)
        val newPolymer = polymer.stepFor(10)
        assertEquals(1588, newPolymer.mostCommonCountMinusLeastCommonCount())
    }
}
