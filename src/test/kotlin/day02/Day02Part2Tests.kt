package day02

import day02.Part2.*
import day02.Part2.Companion.createInstruction
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Part2Tests {

    private val startState = State(
        horizontalPosition = 5,
        depth = 10,
        aim = 5
    )

    @Test
    fun `Down X increases aim by X units`() {
        val newState = Down(5).apply(startState)
        assertEquals(10, newState.aim)
        assertEquals(startState.horizontalPosition, newState.horizontalPosition)
        assertEquals(startState.depth, newState.depth)
    }

    @Test
    fun `Up X decreases aim by X units`() {
        val newState = Up(5).apply(startState)
        assertEquals(0, newState.aim)
        assertEquals(startState.horizontalPosition, newState.horizontalPosition)
        assertEquals(startState.depth, newState.depth)
    }

    @Test
    fun `Forward X increases horizontal position by X units AND increases depth by aim multiplied by X`() {
        val newState = Forward(5).apply(startState)
        assertEquals(startState.aim, newState.aim)
        assertEquals(10, newState.horizontalPosition)
        assertEquals(35, newState.depth)
    }

    @Test
    fun `Example input yields expected result`() {
        val instructions = listOf(
            createInstruction("forward 5"),
            createInstruction("down 5"),
            createInstruction("forward 8"),
            createInstruction("up 3"),
            createInstruction("down 8"),
            createInstruction("forward 2")
        )
        var state = State(
            horizontalPosition = 0,
            depth = 0,
            aim = 0
        )

        for (instruction in instructions) {
            state = instruction.apply(state)
        }

        assertEquals(15, state.horizontalPosition)
        assertEquals(60, state.depth)
    }
}