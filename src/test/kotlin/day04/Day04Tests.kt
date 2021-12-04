package day04

import day04.BingoGame.Board
import day04.BingoGame.Board.Cell
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import readInput

class Day04Tests {

    private val input = readInput("Day04_test")
    private val game = BingoGame(input)

    @Test
    fun `New bingo game has correct draw numbers`() {
        assertEquals(
            listOf(7, 4, 9, 5, 11, 17, 23, 2, 0, 14, 21, 24, 10, 16, 13, 6, 15, 25, 12, 22, 18, 20, 8, 19, 3, 26, 1),
            game.drawNumbers
        )
    }

    @Test
    fun `Create board from input`() {
        val board = Board(
            listOf(
                "22 13 17 11  0",
                " 8  2 23  4 24",
                "21  9 14 16  7",
                " 6 10  3 18  5",
                " 1 12 20 15 19"
            )
        )
        assertEquals(
            listOf(
                listOf(Cell(22), Cell(13), Cell(17), Cell(11), Cell(0)),
                listOf(Cell(8), Cell(2), Cell(23), Cell(4), Cell(24)),
                listOf(Cell(21), Cell(9), Cell(14), Cell(16), Cell(7)),
                listOf(Cell(6), Cell(10), Cell(3), Cell(18), Cell(5)),
                listOf(Cell(1), Cell(12), Cell(20), Cell(15), Cell(19))
            ), board.cells
        )
    }

    @Test
    fun `New bingo game has correct number of 5x5 boards`() {
        assertEquals(3, game.boards.size)
        for (i in 0..2) {
            val board = game.boards[i]
            val numRows = board.cells.size

            assertEquals(5, numRows)

            for (j in board.cells.indices) {
                val numCols = board.cells[j].size
                assertEquals(5, numCols)
            }
        }
    }

    @Test
    fun `Numbers are correctly marked for first 5 drawn numbers`() {
        val firstFiveDraws = game.drawNumbers.subList(0, 5)
        for (drawnNumber in firstFiveDraws) {
            game.callNumber(drawnNumber)
        }

        assertTrue(game.boards[0].cells[0][3].isMarked())
        assertTrue(game.boards[0].cells[1][3].isMarked())
        assertTrue(game.boards[0].cells[2][1].isMarked())
        assertTrue(game.boards[0].cells[2][4].isMarked())
        assertTrue(game.boards[0].cells[3][4].isMarked())

        assertTrue(game.boards[1].cells[1][0].isMarked())
        assertTrue(game.boards[1].cells[1][4].isMarked())
        assertTrue(game.boards[1].cells[2][2].isMarked())
        assertTrue(game.boards[1].cells[3][1].isMarked())
        assertTrue(game.boards[1].cells[3][4].isMarked())

        assertTrue(game.boards[2].cells[0][4].isMarked())
        assertTrue(game.boards[2].cells[1][3].isMarked())
        assertTrue(game.boards[2].cells[3][1].isMarked())
        assertTrue(game.boards[2].cells[3][4].isMarked())
        assertTrue(game.boards[2].cells[4][4].isMarked())
    }

    @Test
    fun `Board has winning row when all numbers in a row are marked`() {
        val board = Board(
            listOf(
                "22 13 17 11  0",
                " 8  2 23  4 24",
                "21  9 14 16  7",
                " 6 10  3 18  5",
                " 1 12 20 15 19"
            )
        )
        board.markNumber(8)
        board.markNumber(2)
        board.markNumber(23)
        board.markNumber(4)
        board.markNumber(24)

        assertTrue(board.checkForWinningRow())
    }

    @Test
    fun `Board has winning column when all numbers in a column are marked`() {
        val board = Board(
            listOf(
                "22 13 17 11  0",
                " 8  2 23  4 24",
                "21  9 14 16  7",
                " 6 10  3 18  5",
                " 1 12 20 15 19"
            )
        )
        board.markNumber(13)
        board.markNumber(2)
        board.markNumber(9)
        board.markNumber(10)
        board.markNumber(12)

        assertTrue(board.checkForWinningColumn())
    }

    @Test
    fun `Third board wins after 12 draws`() {
        val firstTwelveDraws = game.drawNumbers.subList(0, 12)
        for (drawnNumber in firstTwelveDraws) {
            game.callNumber(drawnNumber)
        }
        assertFalse(game.boards[0].checkWin())
        assertFalse(game.boards[1].checkWin())
        assertTrue(game.boards[2].checkWin())
    }

    @Test
    fun `Score for losing board is 0`() {
        assertEquals(0, game.boards[0].calculateScore(24))
        assertEquals(0, game.boards[1].calculateScore(24))
        assertEquals(0, game.boards[2].calculateScore(24))
    }

    @Test
    fun `Score for the winning board is sum of all unmarked numbers multiplied by the last number called`() {
        val result = game.play()
        assertEquals(4512, result.firstWinnerScore())
    }

    @Test
    fun `Score for the last board to win`() {
        val result = game.play()
        assertEquals(1924, result.lastWinnerScore())
    }
}