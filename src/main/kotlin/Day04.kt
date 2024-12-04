import java.nio.file.Files
import java.nio.file.Paths

object Day04 {

    fun part1(fileName: String): Int {

        val path = Paths.get("src/main/resources/$fileName")
        val lines = Files.readAllLines(path)

        val wordSearchGrid = WordSearchGrid(lines)

        return wordSearchGrid.occurrencesOf("XMAS")
    }

    fun part2(fileName: String): Int {

        val path = Paths.get("src/main/resources/$fileName")
        val lines = Files.readAllLines(path)

        val wordSearchGrid = WordSearchGrid(lines)

        return wordSearchGrid.occurrencesOfXShape("MAS")
    }

    data class WordSearchGrid(val lines: List<String>) {

        private val grid : List<List<Cell>>
        private val boundaryX: Int
        private val boundaryY: Int
        private val directions: List<Direction>

        init {
            grid = lines.mapIndexed { y, line ->
                 line.mapIndexed { x, c ->
                    Cell(x, y, c, ::getNeighbourInDirection)
                }
            }
            boundaryX = grid[0].count() -1
            boundaryY = grid.count() -1

            directions = (-1..1).map { x ->
                (-1..1).map { y ->
                    Direction(x, y)
                }
            }.flatten().filter { it !=  Direction(0,0) }
        }

        fun occurrencesOf(word: String) : Int {
            return grid.sumOf {
                it.sumOf { cell ->
                    val results = directions.map {
                        cell.doYouSpellWord(word, it, 0)
                    }

                    results.count { it == true }
                }
            }
        }

        fun occurrencesOfXShape(word: String) : Int {

            if(word.length % 2 == 0)
                throw Exception("Can't X shape an even length word")

            val originMultiplier = (word.length-1)/2

            val origins = grid.flatMap {
                it.flatMap { cell ->
                    val originPoints = directions.map { direction ->
                        val spellsWord = cell.doYouSpellWord(word, direction, 0)
                        if(spellsWord && direction.isDiagonal())
                            OriginPoint(cell, direction, originMultiplier)
                        else null
                    }

                    originPoints
                }
            }

            return origins.filterNotNull()
                .groupingBy { it.x to it.y }
                .eachCount()
                .filter { it.component2() > 1 }
                .count()
        }

        private fun getNeighbourInDirection(cell: Cell, direction: Direction) : Cell? {

            val newX = cell.x + direction.x
            val newY = cell.y + direction.y

            if(newX < 0 || newX > boundaryX
                || newY < 0 || newY > boundaryY)
                return null

            return grid[newY][newX]
        }
    }

    data class OriginPoint(private val cell: Cell,
                           private val direction: Direction,
                           private val originMultiplier: Int) {

        val x : Int = cell.x + (direction.x * originMultiplier)
        val y : Int = cell.y + (direction.y * originMultiplier)
    }

    data class Direction(val x: Int, val y: Int) {
        fun isDiagonal() : Boolean {
            return x != 0 && y != 0
        }
    }

    data class Cell(val x: Int, val y: Int, val character: Char, val getNeighbour: (cell: Cell, direction: Direction) -> Cell?) {

        fun doYouSpellWord(word: String, direction: Direction, yourPosition: Int): Boolean {

            if(character != word[yourPosition])
                return false

            if(yourPosition == word.length-1)
                return true

            val neighbour = getNeighbour(this, direction)

            return neighbour?.doYouSpellWord(word, direction, yourPosition+1) ?: false
        }
    }
}