import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Day02Tests {

    private val startLocation = Location(
        horizontalPosition = 0,
        depth = 10
    )

    @Test
    fun `Up instruction decreases depth and does not change horizontal position`() {
        val newLocation = Up(5).apply(startLocation)
        assertEquals(5, newLocation.depth)
        assertEquals(startLocation.horizontalPosition, newLocation.horizontalPosition)
    }

    @Test
    fun `Down instruction increases depth and does not change horizontal position`() {
        val newLocation = Down(5).apply(startLocation)
        assertEquals(15, newLocation.depth)
        assertEquals(startLocation.horizontalPosition, newLocation.horizontalPosition)
    }

    @Test
    fun `Forward instruction increases horizontalPosition and does not change depth`() {
        val newLocation = Forward(10).apply(startLocation)
        assertEquals(startLocation.depth, newLocation.depth)
        assertEquals(10, newLocation.horizontalPosition)
    }
}