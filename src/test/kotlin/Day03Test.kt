import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day03Test {

    @Test
     fun example() {

         val result = Day03.part1("day03_example.txt")

         assertEquals(161, result)
     }

    @Test
    fun example_part2() {

        var result = Day03.part2("day03_example2.txt")

        assertEquals(48, result)
    }

    @Test
    fun part1() {

        val result = Day03.part1("day03_part1.txt")

        println(result)
    }

    @Test
    fun part2() {

        val result = Day03.part2("day03_part1.txt")
        println(result)
    }
 }