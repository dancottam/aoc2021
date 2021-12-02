fun main() {
    fun part1(input: List<String>): Int {
        var submarineLocation = Location(
            horizontalPosition = 0,
            depth = 0
        )

        for (line in input) {
            val instruction = createInstruction(line)
            submarineLocation = instruction.apply(submarineLocation)
        }

        return submarineLocation.horizontalPosition * submarineLocation.depth
    }

    fun part2(input: List<String>): Int {
        return input.size
    }

    // test if implementation meets criteria from the description, like:
    val testInput = readInput("Day02_test")
    check(part1(testInput) == 150)
//    check(part2(testInput) == 1)

    val input = readInput("Day02")
    println(part1(input))
//    println(part2(input))
}

fun createInstruction(instruction: String): Instruction {
    val parts = instruction.trim().split(" ")
    if (parts.size != 2) {
        throw IllegalArgumentException("Provided instruction is not correctly formatted [$instruction]")
    }
    val command = parts[0].lowercase()
    val amount = parts[1].toInt()

    return when(command) {
        "up" -> Up(amount)
        "down" -> Down(amount)
        "forward" -> Forward(amount)
        else -> throw IllegalArgumentException("Unknown instruction [$instruction]")
    }
}

data class Location (
    val horizontalPosition: Int,
    val depth:Int
)

abstract class Instruction (
    protected val amount: Int
) {
    abstract fun apply(currentLocation: Location): Location
}

class Up(amount: Int): Instruction(amount) {
    override fun apply(currentLocation: Location): Location {
        return Location(
            horizontalPosition = currentLocation.horizontalPosition,
            depth = currentLocation.depth - amount
        )
    }
}

class Down(amount: Int): Instruction(amount) {
    override fun apply(currentLocation: Location): Location {
        return Location(
            horizontalPosition = currentLocation.horizontalPosition,
            depth = currentLocation.depth + amount
        )
    }
}

class Forward(amount: Int): Instruction(amount) {
    override fun apply(currentLocation: Location): Location {
        return Location(
            horizontalPosition = currentLocation.horizontalPosition + amount,
            depth = currentLocation.depth
        )
    }
}