package day12

import readInput
import java.util.*

fun main() {
    fun part1(input: List<String>): Int {
        val caveMap = CaveMap(input)
        return caveMap.plotDistinctPathsVisitingSmallCavesOnlyOnce().size
    }

    fun part2(input: List<String>): Int {
        val caveMap = CaveMap(input)
        return caveMap.plotDistinctPathsAllowingTwoVisitsToOneSmallCave().size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day12_test")
    check(part1(testInput) == 226)
    check(part2(testInput) == 3509)

    val input = readInput("Day12")
    println(part1(input))
    println(part2(input))
}

class CaveMap (input: List<String>) {

    val connections: List<CaveConnection> = parseConnections(input)

    private fun parseConnections(input: List<String>): List<CaveConnection> {
        val connections = mutableListOf<CaveConnection>()

        for (line in input) {
            val caveNames = line.split("-")
            connections.add(CaveConnection(caveNames[0], caveNames[1]))
            connections.add(CaveConnection(caveNames[1], caveNames[0]))
        }

        return connections.toList()
    }

    fun plotDistinctPathsVisitingSmallCavesOnlyOnce(): SortedSet<String> {
        return plotDistinctPaths { connection, pathSoFar ->
            leadsToLargeCave(connection) || notYetVisited(connection.toCave, pathSoFar)
        }
    }

    fun plotDistinctPathsAllowingTwoVisitsToOneSmallCave(): SortedSet<String> {
        return plotDistinctPaths { connection, pathSoFar ->
            leadsToLargeCave(connection) || notYetVisited(connection.toCave, pathSoFar) ||
                    (notStartOrEnd(connection.toCave) && noSmallCavesHaveBeenVisitedTwice(pathSoFar))
        }
    }

    private fun leadsToLargeCave(connection: CaveConnection): Boolean {
        return connection.toCave == connection.toCave.uppercase()
    }

    private fun notYetVisited(cave: String, pathSoFar: List<CaveConnection>): Boolean {
        return pathSoFar.all {
            it.fromCave != cave && it.toCave != cave
        }
    }

    private fun notStartOrEnd(cave: String): Boolean {
        return cave != "start" && cave != "end"
    }

    private fun noSmallCavesHaveBeenVisitedTwice(path: List<CaveConnection>): Boolean {
        val toCaves = path.map { it.toCave }.filter { it.lowercase() == it }
        return toCaves.distinct().all { Collections.frequency(toCaves, it) == 1 }
    }

    private fun plotDistinctPaths(usableConnectionPredicate: (connection: CaveConnection, pathSoFar: List<CaveConnection>) -> Boolean): SortedSet<String> {
        val distinctPaths = findDistinctPathsBetween("start", "end", usableConnectionPredicate)
        return distinctPaths.map {
            val path = mutableListOf(it.first().fromCave)
            for (caveConnection in it) {
                path.add(caveConnection.toCave)
            }
            path.joinToString(",")
        }.toSortedSet()
    }

    private fun findDistinctPathsBetween(
        startCave: String,
        endCave: String,
        usableConnectionPredicate: (connection: CaveConnection, pathSoFar: List<CaveConnection>) -> Boolean
    ): Set<List<CaveConnection>> {

        val paths = mutableListOf<List<CaveConnection>>()

        val candidateConnections = connections.filter { it.fromCave == startCave }

        for (connection in candidateConnections) {
            val path = listOf(connection)
            val possiblePaths = findPathsTo(endCave, path, usableConnectionPredicate)
            paths.addAll(possiblePaths)
        }

        return paths.toSet()
    }

    private fun findPathsTo(
        endCave: String,
        pathSoFar: List<CaveConnection>,
        usableConnectionPredicate: (connection: CaveConnection, pathSoFar: List<CaveConnection>) -> Boolean
    ): List<List<CaveConnection>> {
        val currentCave = pathSoFar.last().toCave

        val candidateConnections = connections.filter { it.fromCave == currentCave }

        val paths = mutableListOf<List<CaveConnection>>()

        for (connection in candidateConnections) {
            if (usableConnectionPredicate.invoke(connection, pathSoFar)) {
                val mutablePathSoFar = pathSoFar.toMutableList()
                mutablePathSoFar.add(connection)

                if (connection.toCave == endCave) {
                    paths.add(mutablePathSoFar.toList())
                } else {
                    val possiblePaths = findPathsTo(endCave, mutablePathSoFar.toList(), usableConnectionPredicate)
                    paths.addAll(possiblePaths)
                }
            }
        }
        return paths.toList()
    }

}

data class CaveConnection (
    val fromCave: String,
    val toCave: String
)