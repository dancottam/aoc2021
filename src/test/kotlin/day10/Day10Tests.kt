package day10

import day10.Status.CORRUPTED
import day10.Status.OK
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day10Tests {

    private val input = """
        [({(<(())[]>[[{[]{<()<>>
        [(()[<>])]({[<{<<[]>>(
        {([(<{}[<>[]}>{[]{[(<()>
        (((({<>}<{<{<>}{[]{[]{}
        [[<[([]))<([[{}[[()]]]
        [{[{({}]{}}([{[{{{}}([]
        {<[[]]>}<{[{[{[]{()[[[]
        [<(<(<(<{}))><([]([]()
        <{([([[(<>()){}]>(<<{{
        <{([{{}}[<[[[<>{}]]]>[]]
        """.trimIndent().split("\n")

    @Test
    fun `Read lines from input`() {
        val lines = Lines(input)
        assertEquals(10, lines.size())
    }

    @Test
    fun `Valid chunks`() {
        assertEquals(OK, Line("[]").status)
        assertEquals(OK, Line("([])").status)
        assertEquals(OK, Line("{()()()}").status)
        assertEquals(OK, Line("<([{}])>").status)
        assertEquals(OK, Line("[<>({}){}[([])<>]]").status)
        assertEquals(OK, Line("(((((((((())))))))))").status)
    }

    @Test
    fun `Corrupt chunks`() {
        assertEquals(CORRUPTED, Line("(]").status)
        assertEquals(CORRUPTED, Line("{()()()>").status)
        assertEquals(CORRUPTED, Line("(((()))}").status)
        assertEquals(CORRUPTED, Line("<([]){()}[{}])").status)
    }

    @Test
    fun `Get first illegal character in line`() {
        assertEquals('}', Line("{([(<{}[<>[]}>{[]{[(<()>").firstIllegalCharacter)
        assertEquals(')', Line("[[<[([]))<([[{}[[()]]]").firstIllegalCharacter)
        assertEquals(']', Line("[{[{({}]{}}([{[{{{}}([]").firstIllegalCharacter)
        assertEquals(')', Line("[<(<(<(<{}))><([]([]()").firstIllegalCharacter)
        assertEquals('>', Line("<{([([[(<>()){}]>(<<{{").firstIllegalCharacter)
    }

    @Test
    fun `Get syntax error score`() {
        val lines = Lines(input)
        assertEquals(26397, lines.syntaxErrorScore)
    }

    @Test
    fun `Get missing close characters`() {
        assertEquals("}}]])})]", Line("[({(<(())[]>[[{[]{<()<>>").missingCloseChars)
        assertEquals(")}>]})", Line("[(()[<>])]({[<{<<[]>>(").missingCloseChars)
        assertEquals("}}>}>))))", Line("(((({<>}<{<{<>}{[]{[]{}").missingCloseChars)
        assertEquals("]]}}]}]}>", Line("{<[[]]>}<{[{[{[]{()[[[]").missingCloseChars)
        assertEquals("])}>", Line("<{([{{}}[<[[[<>{}]]]>[]]").missingCloseChars)
    }

    @Test
    fun `Get line completion score`() {
        assertEquals(288957, Line("[({(<(())[]>[[{[]{<()<>>").completionScore())
        assertEquals(5566, Line("[(()[<>])]({[<{<<[]>>(").completionScore())
        assertEquals(1480781, Line("(((({<>}<{<{<>}{[]{[]{}").completionScore())
        assertEquals(995444, Line("{<[[]]>}<{[{[{[]{()[[[]").completionScore())
        assertEquals(294, Line("<{([{{}}[<[[[<>{}]]]>[]]").completionScore())
    }

    @Test
    fun `Get middle completion score`() {
        assertEquals(288957, Lines(input).middleCompletionScore())
    }
}
