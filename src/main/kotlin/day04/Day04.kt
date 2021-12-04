package day04

import day04.BingoGame.Board.*
import readInput
import kotlin.streams.toList

fun main() {
    fun part1(input: List<String>): Int {
        val game = BingoGame(input)
        val result = game.play()
        return result.firstWinnerScore()
    }

    fun part2(input: List<String>): Int {
        val game = BingoGame(input)
        val result = game.play()
        return result.lastWinnerScore()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day04_test")
    check(part1(testInput) == 4512)
    check(part2(testInput) == 1924)

    val input = readInput("Day04")
    println(part1(input))
    println(part2(input))
}


class BingoGame(input: List<String>) {

    val drawNumbers = getDrawNumbers(input)
    val boards: List<Board> = createBoards(input)
    private val remainingBoards = boards.toMutableList()

    fun callNumber(calledNumber: Int) {
        for (board in remainingBoards) {
            board.markNumber(calledNumber)
        }
    }

    fun play(): Result {
        val result = Result()

        for (number in drawNumbers) {
            callNumber(number)
            val winningBoards = findWinningBoards(remainingBoards)
            result.addWinningBoards(number, winningBoards)
            remainingBoards.removeAll(winningBoards)
        }
        return result
    }

    private fun findWinningBoards(boards: MutableList<Board>): List<Board> {
        val winningBoards = mutableListOf<Board>()
        for (board in boards) {
            if (board.checkWin()) {
                winningBoards.add(board)
            }
        }
        return winningBoards.toList()
    }

    class Board(
        input: List<String>
    ) {
        val cells = createCells(input)

        fun markNumber(numberToMark: Int) {
            for (row in cells) {
                for (cell in row) {
                    if (cell.number == numberToMark) {
                        cell.mark()
                    }
                }
            }
        }

        fun checkWin(): Boolean {
            val hasWinningRow = checkForWinningRow()
            if (hasWinningRow) {
                return true
            }
            return checkForWinningColumn()
        }

        fun checkForWinningRow(): Boolean {
            for (row in cells) {
                if (row.all { it.isMarked() }) {
                    return true
                }
            }
            return false
        }

        fun checkForWinningColumn(): Boolean {
            val columnMap = mutableMapOf<Int, MutableList<Boolean>>()
            for (row in cells) {
                for (i in row.indices) {
                    columnMap.putIfAbsent(i, mutableListOf())
                    columnMap[i]!!.add(row[i].isMarked())
                }
            }
            return columnMap.values.any { cells -> cells.all { cellIsMarked -> cellIsMarked } }
        }

        fun calculateScore(winningNumber: Int): Int {
            val hasWon = checkWin()
            if (hasWon) {
                return winningNumber * sumUnmarkedNumbers()
            }
            return 0
        }

        private fun sumUnmarkedNumbers(): Int {
            return cells.sumOf { rows ->
                rows.filter { cell -> cell.isNotMarked() }
                    .sumOf { cell -> cell.number }
            }
        }

        class Cell(
            val number: Int,
            private var marked: Boolean = false
        ) {

            fun mark() {
                marked = true
            }

            fun isMarked(): Boolean {
                return marked
            }

            fun isNotMarked(): Boolean {
                return !marked
            }

            override fun equals(other: Any?): Boolean {
                if (this === other) return true
                if (other !is Cell) return false

                if (number != other.number) return false
                if (marked != other.marked) return false

                return true
            }

            override fun hashCode(): Int {
                var result = number
                result = 31 * result + marked.hashCode()
                return result
            }

        }

        class Result {
            private val scores = mutableListOf<BoardScore>()

            fun addWinningBoards(winningNumber: Int, winningBoards: List<Board>) {
                val winningBoardScores = mutableListOf<BoardScore>()
                for (board in winningBoards) {
                    winningBoardScores.add(BoardScore(board, board.calculateScore(winningNumber)))
                }
                winningBoardScores.sortByDescending { it.score }
                scores.addAll(winningBoardScores)
            }

            fun firstWinnerScore(): Int {
                return scores.first().score
            }

            fun lastWinnerScore(): Int {
                return scores.last().score
            }

            data class BoardScore(
                val board: Board,
                val score: Int
            )

        }
    }

    companion object {
        fun getDrawNumbers(input: List<String>): List<Int> {
            return input[0].split(",").map { it.toInt() }
        }

        fun createBoards(input: List<String>): List<Board> {
            if (input.size < 3) {
                return emptyList()
            }
            val boards = mutableListOf<Board>()
            var rowBuffer = mutableListOf<String>()
            for (i in 2..input.lastIndex) {
                val line = input[i]
                if (line.isBlank()) {
                    boards.add(createBoard(rowBuffer))
                    rowBuffer = mutableListOf()
                } else {
                    rowBuffer.add(line)
                }
            }
            if (rowBuffer.isNotEmpty()) {
                boards.add(createBoard(rowBuffer))
            }
            return boards.toList()
        }

        private fun createBoard(input: List<String>): Board {
            return Board(input)
        }

        private fun createCells(input: List<String>): List<List<Cell>> {
            val rows = mutableListOf<List<Cell>>()
            for (line in input) {
                if (line.isBlank()) {
                    break
                }
                val row = line.split(" ").stream().filter(String::isNotBlank).map { Cell(it.toInt()) }.toList()
                rows.add(row)
            }
            return rows.toList()
        }
    }
}