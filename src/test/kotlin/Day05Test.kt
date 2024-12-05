import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

class Day05Test {

    @Test
     fun example() {

         val result = Day05.part1("day05_example.txt")

         assertEquals(143, result)
     }

    @Test
    fun example_part2() {

        var result = Day05.part2("day05_example.txt")

        assertEquals(123, result)
    }

    @Test
    fun part1() {

        val result = Day05.part1("day05_part1.txt")

        println(result)
    }

    @Test
    fun part2() {

        val result = Day05.part2("day05_part1.txt")

        println(result)
    }
 }