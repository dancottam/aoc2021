package day02

class Part1 {

    data class Location(
        val horizontalPosition: Int,
        val depth: Int
    )

    abstract class Instruction(
        protected val amount: Int
    ) {
        abstract fun apply(currentLocation: Location): Location
    }

    class Up(amount: Int) : Instruction(amount) {
        override fun apply(currentLocation: Location): Location {
            return Location(
                horizontalPosition = currentLocation.horizontalPosition,
                depth = currentLocation.depth - amount
            )
        }
    }

    class Down(amount: Int) : Instruction(amount) {
        override fun apply(currentLocation: Location): Location {
            return Location(
                horizontalPosition = currentLocation.horizontalPosition,
                depth = currentLocation.depth + amount
            )
        }
    }

    class Forward(amount: Int) : Instruction(amount) {
        override fun apply(currentLocation: Location): Location {
            return Location(
                horizontalPosition = currentLocation.horizontalPosition + amount,
                depth = currentLocation.depth
            )
        }
    }

    companion object {
        fun createInstruction(instruction: String): Instruction {
            val parts = instruction.trim().split(" ")
            if (parts.size != 2) {
                throw IllegalArgumentException("Provided instruction is not correctly formatted [$instruction]")
            }
            val command = parts[0].lowercase()
            val amount = parts[1].toInt()

            return when (command) {
                "up" -> Up(amount)
                "down" -> Down(amount)
                "forward" -> Forward(amount)
                else -> throw IllegalArgumentException("Unknown instruction [$instruction]")
            }
        }
    }
}