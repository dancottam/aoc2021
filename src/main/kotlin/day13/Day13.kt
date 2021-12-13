package day13

import readInput

fun main() {
    fun part1(input: List<String>): Int {
        val page = Page.fromInput(input)
        val foldedPage = page.fold()
        return foldedPage.dots.size
    }

    fun part2(input: List<String>): String {
        val page = Page.fromInput(input)
        val foldedPage = page.foldAll()
        return foldedPage.plotDots()
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day13_test")
    check(part1(testInput) == 17)
    check(part2(testInput) == """
        #####
        #...#
        #...#
        #...#
        #####
        .....
        .....
    """.trimIndent())

    val input = readInput("Day13")
    println(part1(input))
    println(part2(input))
}

data class Page(
    val dots: List<Position>,
    val maxX: Int = findMaxX(dots),
    val maxY: Int = findMaxY(dots),
    val remainingFolds: List<String>,
) {

    fun fold(): Page {
        val fold = remainingFolds.first()
        return applyFold(fold)
    }

    private fun applyFold(fold: String): Page {
        val foldSplit = fold.split("=")
        val axis = foldSplit[0]
        val foldPos = foldSplit[1].toInt()

        val newDots = if (axis == "y") performHorizontalFold(foldPos) else performVerticalFold(foldPos)
        return Page(
            dots = newDots,
            maxX = if (axis == "x") foldPos - 1 else maxX,
            maxY = if (axis == "y") foldPos - 1 else maxY,
            remainingFolds = remainingFolds.subList(1, remainingFolds.size)
        )
    }

    private fun performHorizontalFold(foldPosition: Int): List<Position> {
        val belowFold = dots.filter { it.y > foldPosition }
        val aboveFold = dots.filter { it.y < foldPosition }.toMutableList()

        for (dot in belowFold) {
            val newY = foldPosition - (dot.y - foldPosition)
            val newPos = Position(dot.x, newY)
            aboveFold.add(newPos)
        }

        return aboveFold.toSet().toList()
    }

    private fun performVerticalFold(foldPosition: Int): List<Position> {
        val rightOfFold = dots.filter { it.x > foldPosition }
        val leftOfFold = dots.filter { it.x < foldPosition }.toMutableList()

        for (dot in rightOfFold) {
            val newX = foldPosition - (dot.x - foldPosition)
            val newPos = Position(newX, dot.y)
            leftOfFold.add(newPos)
        }

        return leftOfFold.toSet().toList()
    }

    fun plotDots(): String {
        var plots = ""
        for (y in 0..maxY) {
            for (x in 0..maxX) {
                plots += (if (dots.contains(Position(x, y))) "#" else ".")
            }
            plots += "\n"
        }
        return plots.trim()
    }

    fun foldAll(): Page {
        var page = this
        var remainingFolds = this.remainingFolds

        while (remainingFolds.isNotEmpty()) {
            page = page.fold()
            remainingFolds = page.remainingFolds
        }
        return page
    }

    companion object {

        private const val FOLD_LINE_PREFIX = "fold along "

        fun fromInput(input: List<String>): Page {
            val dots = mutableListOf<Position>()
            val folds = mutableListOf<String>()

            for (line in input) {
                if (line.isBlank()) {
                    continue
                }
                if (line.startsWith(FOLD_LINE_PREFIX)) {
                    val fold = line.substringAfter(FOLD_LINE_PREFIX)
                    folds.add(fold)
                } else {
                    val coords = line.split(",")
                    dots.add(Position(coords[0].toInt(), coords[1].toInt()))
                }
            }
            return Page(
                dots = dots.toList(),
                maxX = findMaxX(dots),
                maxY = findMaxY(dots),
                remainingFolds = folds.toList())
        }

        private fun findMaxX(dots: List<Position>): Int {
            return dots.maxOf { it.x }
        }

        private fun findMaxY(dots: List<Position>): Int {
            return dots.maxOf { it.y }
        }
    }
}

data class Position(
    val x: Int,
    val y: Int
)