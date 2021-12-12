package day12

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day12Tests {

    private val smallInput = """
        start-A
        start-b
        A-c
        A-b
        b-d
        A-end
        b-end
    """.trimIndent().split("\n")

    private val largerInput = """
            dc-end
            HN-start
            start-kj
            dc-start
            dc-HN
            LN-dc
            HN-end
            kj-sa
            kj-HN
            kj-dc
        """.trimIndent().split("\n")

    private val evenLargerInput = """
            fs-end
            he-DX
            fs-he
            start-DX
            pj-DX
            end-zg
            zg-sl
            zg-pj
            pj-he
            RW-he
            fs-DX
            pj-RW
            zg-RW
            start-pj
            he-WI
            zg-he
            pj-fs
            start-RW
        """.trimIndent().split("\n")

    @Test
    fun `Read input`() {
        val caveMap = CaveMap(smallInput)
        assertEquals(14, caveMap.connections.size)
    }

    @Test
    fun `Get distinct paths visiting small caves only once (small map)`() {
        val caveMap = CaveMap(smallInput)
        assertEquals("""
                start,A,b,A,c,A,end
                start,A,b,A,end
                start,A,b,end
                start,A,c,A,b,A,end
                start,A,c,A,b,end
                start,A,c,A,end
                start,A,end
                start,b,A,c,A,end
                start,b,A,end
                start,b,end
            """.trimIndent().split("\n").toSortedSet(), caveMap.plotDistinctPathsVisitingSmallCavesOnlyOnce())
    }

    @Test
    fun `Get distinct paths visiting small caves only once (larger map)`() {
        val caveMap = CaveMap(largerInput)

        assertEquals("""
            start,HN,dc,HN,end
            start,HN,dc,HN,kj,HN,end
            start,HN,dc,end
            start,HN,dc,kj,HN,end
            start,HN,end
            start,HN,kj,HN,dc,HN,end
            start,HN,kj,HN,dc,end
            start,HN,kj,HN,end
            start,HN,kj,dc,HN,end
            start,HN,kj,dc,end
            start,dc,HN,end
            start,dc,HN,kj,HN,end
            start,dc,end
            start,dc,kj,HN,end
            start,kj,HN,dc,HN,end
            start,kj,HN,dc,end
            start,kj,HN,end
            start,kj,dc,HN,end
            start,kj,dc,end
        """.trimIndent().split("\n").toSortedSet(), caveMap.plotDistinctPathsVisitingSmallCavesOnlyOnce())
    }

    @Test
    fun `Get distinct paths visiting small caves only once (even larger map)`() {
        val caveMap = CaveMap(evenLargerInput)
        assertEquals(226, caveMap.plotDistinctPathsVisitingSmallCavesOnlyOnce().size)
    }

    @Test
    fun `Get distinct paths visiting one small cave twice (small map)`() {
        val caveMap = CaveMap(smallInput)
        assertEquals("""
            start,A,b,A,b,A,c,A,end
            start,A,b,A,b,A,end
            start,A,b,A,b,end
            start,A,b,A,c,A,b,A,end
            start,A,b,A,c,A,b,end
            start,A,b,A,c,A,c,A,end
            start,A,b,A,c,A,end
            start,A,b,A,end
            start,A,b,d,b,A,c,A,end
            start,A,b,d,b,A,end
            start,A,b,d,b,end
            start,A,b,end
            start,A,c,A,b,A,b,A,end
            start,A,c,A,b,A,b,end
            start,A,c,A,b,A,c,A,end
            start,A,c,A,b,A,end
            start,A,c,A,b,d,b,A,end
            start,A,c,A,b,d,b,end
            start,A,c,A,b,end
            start,A,c,A,c,A,b,A,end
            start,A,c,A,c,A,b,end
            start,A,c,A,c,A,end
            start,A,c,A,end
            start,A,end
            start,b,A,b,A,c,A,end
            start,b,A,b,A,end
            start,b,A,b,end
            start,b,A,c,A,b,A,end
            start,b,A,c,A,b,end
            start,b,A,c,A,c,A,end
            start,b,A,c,A,end
            start,b,A,end
            start,b,d,b,A,c,A,end
            start,b,d,b,A,end
            start,b,d,b,end
            start,b,end
        """.trimIndent().split("\n").toSortedSet().joinToString("\n"), caveMap.plotDistinctPathsAllowingTwoVisitsToOneSmallCave().joinToString("\n"))
    }

    @Test
    fun `Get distinct paths visiting one small cave twice (slightly larger map)`() {
        val caveMap = CaveMap(largerInput)
        assertEquals(103, caveMap.plotDistinctPathsAllowingTwoVisitsToOneSmallCave().size)
    }

    @Test
    fun `Get distinct paths visiting one small cave twice (even larger map)`() {
        val caveMap = CaveMap(evenLargerInput)
        assertEquals(3509, caveMap.plotDistinctPathsAllowingTwoVisitsToOneSmallCave().size)
    }
}
