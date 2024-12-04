import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day04Test {

    @Test
     fun example() {

         val result = Day04.part1("day04_example.txt")

         assertEquals(18, result)
     }

    @Test
    fun example_part2() {

        var result = Day04.part2("day04_example.txt")

        assertEquals(9, result)
    }

    @Test
    fun part1() {

        val result = Day04.part1("day04_part1.txt")

        println(result)
    }

    @Test
    fun part2() {

        val result = Day04.part2("day04_part1.txt")
        println(result)
    }
 }