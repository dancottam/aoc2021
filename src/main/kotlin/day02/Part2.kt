package day02

class Part2 {
    data class State (
        val horizontalPosition: Int,
        val depth: Int,
        val aim: Int
    )

    abstract class Instruction (
        protected val amount: Int
    ) {
        abstract fun apply(currentState: State): State
    }

    class Down(amount: Int): Instruction(amount) {
        override fun apply(currentState: State): State {
            return State(
                horizontalPosition = currentState.horizontalPosition,
                depth = currentState.depth,
                aim = currentState.aim + amount
            )
        }
    }

    class Up(amount: Int): Instruction(amount) {
        override fun apply(currentState: State): State {
            return State(
                horizontalPosition = currentState.horizontalPosition,
                depth = currentState.depth,
                aim = currentState.aim - amount
            )
        }
    }

    class Forward(amount: Int): Instruction(amount) {
        override fun apply(currentState: State): State {
            return State(
                horizontalPosition = currentState.horizontalPosition + amount,
                depth = currentState.depth + (currentState.aim * amount),
                aim = currentState.aim
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