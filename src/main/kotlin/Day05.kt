import java.nio.file.Files
import java.nio.file.Paths

object Day05 {

    fun part1(fileName: String): Int {

        val path = Paths.get("src/main/resources/$fileName")
        val lines = Files.readAllLines(path)

        val safetyManual = SleighLaunchSafetyManual(lines)

        val correctSequences = safetyManual.correctlyOrderedSequences()

       return correctSequences.sumOf {
           it.middleNumber()
       }
    }

    fun part2(fileName: String): Int {

        val path = Paths.get("src/main/resources/$fileName")
        val lines = Files.readAllLines(path)

        val safetyManual = SleighLaunchSafetyManual(lines)

        val reworkedSequences = safetyManual.reworkedIncorrectSequences()

        return reworkedSequences.sumOf {
            it.middleNumber()
        }
    }

    class SleighLaunchSafetyManual(lines: List<String>) {

        val pageOrderingRules: List<PageRule>
        val updateSequences: List<Sequence>

        init {
            pageOrderingRules = lines.filter {
                it.contains("|")
            }.map {
                PageRule(it.split("|"))
            }

            updateSequences = lines.filter {
                !it.contains("|") && it.contains(",")
            }.map {
                Sequence(it)
            }
        }

        fun correctlyOrderedSequences() : List<Sequence> {

            return updateSequences.filter {
                it.isCorrect(pageOrderingRules)
            }
        }

        fun reworkedIncorrectSequences() : List<Sequence> {

            val incorrectSequences = updateSequences.filter {
                !it.isCorrect(pageOrderingRules)
            }

            return incorrectSequences.map {
                it.rework(pageOrderingRules)
            }
        }

    }

    data class Sequence(val line: String) {

        private val pageNumbers : List<Int> = line.split(",").map { it.toInt() }

        fun isCorrect(rules: List<PageRule>): Boolean {

            val relevantPageRules = rules.filter { rule ->
                pageNumbers.contains(rule.left) && pageNumbers.contains(rule.right)
            }

            val pageSize = pageNumbers.count()

            val validity = pageNumbers.mapIndexed { i, page ->
                val pageRulesWithLeft = relevantPageRules.filter { it.left == page }
                val pageNumbersToTheRight = pageNumbers.takeLast(pageSize - i + 1)
                val allAppearAfter = pageRulesWithLeft.all {
                    pageNumbersToTheRight.contains(it.right)
                }

                val pageRulesWithRight = relevantPageRules.filter { it.right == page }
                val pageNumbersToTheLeft = if(i == 0) emptyList() else pageNumbers.take(i)
                val allAppearBefore = pageRulesWithRight.all {
                    pageNumbersToTheLeft.contains(it.left)
                }

                allAppearAfter && allAppearBefore
            }


            return validity.all { it == true };
        }

        fun rework(rules: List<PageRule>) : Day05.Sequence {
            return reworkNumbers(pageNumbers, rules)
        }

        private fun reworkNumbers(sequence: List<Int>, rules: List<PageRule>) : Day05.Sequence {

            val newPageSequence = sequence.toMutableList()

            rules.forEach { rule ->

                val left = newPageSequence.indexOf(rule.left)
                val right = newPageSequence.indexOf(rule.right)

                if(left > right && right != -1) {
                    newPageSequence.removeAt(right)
                    newPageSequence.add(left, rule.right)
                }
            }

            val newSequenceRepresentation = newPageSequence.joinToString(",") { it.toString() }
            val newSequence = Sequence(newSequenceRepresentation)
            val isCorrect = newSequence.isCorrect(rules)

            if(!isCorrect)
                return reworkNumbers(newPageSequence, rules)

            return newSequence
        }

        fun middleNumber() : Int {
            val middle = pageNumbers.count() / 2
            return pageNumbers[middle]
        }
    }

    data class PageRule(private val parts: List<String>) {

        val left : Int = parts[0].toInt()
        val right: Int = parts[1].toInt()
    }
}

