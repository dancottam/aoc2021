fun main() {
    fun part1(input: List<String>): Int {
        var prevDepth: Int? = null
        var count = 0
        for (line in input) {
            val currentDepth = line.toInt()
            if (prevDepth != null && prevDepth < currentDepth) {
                count++
            }
            prevDepth = currentDepth
        }
        return count
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 7)

    val input = readInput("Day01")
    println(part1(input))
//    println(part2(input))
}
