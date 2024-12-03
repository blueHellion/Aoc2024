import java.nio.file.Files
import java.nio.file.Paths

object Day03 {

    fun part1(fileName: String): Int {

        val path = Paths.get("src/main/resources/$fileName")
        val lines = Files.readAllLines(path)

        val memoryLines = lines.map {
            MemoryLine(it)
        }

        var state = true
        val allValidCandidates = memoryLines.map {
            val pairing = it.multiplicationCandidates(false, state)
            state = pairing.second
            pairing.first
        }.flatten().filter { it.isValid() }
        return allValidCandidates.sumOf {
            it.calculation()
        }
    }

    fun part2(fileName: String): Int {

        val path = Paths.get("src/main/resources/$fileName")
        val lines = Files.readAllLines(path)

        val memoryLines = lines.map {
            MemoryLine(it)
        }

        var state = true
        val allValidCandidates = memoryLines.map {
            val pairing = it.multiplicationCandidates(true, state)
            state = pairing.second
            pairing.first
        }.flatten().filter { it.isValid() }

        return allValidCandidates.sumOf {
            it.calculation()
        }
    }

    data class Switch(val position: Int, val enabled: Boolean)

    data class SwitchesLine(val line: String) {

        fun getSwitches(lookup: String, switchValue: Boolean) : List<Switch> {
            val switches = mutableListOf<Switch>()
            var finished = false
            var startIndex = 0
            while (!finished) {
                val index = line.indexOf(lookup, startIndex)
                when(index) {
                    -1 -> finished = true
                    else -> {
                        switches.add(Switch(index, switchValue))
                        startIndex = index + 1
                    }
                }
            }

            return switches
        }
    }

    data class MemoryLine(val line: String) {

        fun multiplicationCandidates(inspectSwitches: Boolean, startingState: Boolean) : Pair<List<MultiplicationCandidate>, Boolean> {
            val candidates = mutableListOf<MultiplicationCandidate>()
            var startingIndex = 0
            var finished = false

            val switchesLine = SwitchesLine(line)
            val switches = (switchesLine.getSwitches("do()", true) + switchesLine.getSwitches("don't()", false))
                .sortedByDescending { it.position }

            while(!finished) {
                val index = line.indexOf("mul(", startingIndex)

                when(index){
                    -1 -> finished = true
                    else -> {
                        val mostRecentPreviousSwitch = switches.firstOrNull { it.position < index }
                        val enabled = mostRecentPreviousSwitch?.enabled ?: startingState
                        if (enabled || !inspectSwitches) {
                            candidates.add(MultiplicationCandidate(index, line))
                        }
                        startingIndex = index + 1
                    }
                }
            }

            val mostRecentSwitch = switches.firstOrNull()
            val stateToPassOn = !inspectSwitches || mostRecentSwitch?.enabled ?: startingState
            return candidates to stateToPassOn
        }
    }

    data class MultiplicationCandidate(val startIndex: Int, val entireLine: String){

        private val parsed : Boolean
        private lateinit var left : String
        private lateinit var right: String

        init {
            val closingBracketIndex = entireLine.indexOf(")", startIndex)
            when(closingBracketIndex) {
                -1 -> parsed = false
                else -> {
                    val candidatePart = entireLine.substring(startIndex + 4, closingBracketIndex)
                    val commaIndex = candidatePart.indexOf(",")
                    when(commaIndex) {
                        -1 -> parsed = false
                        else -> {
                            var parts = candidatePart.split(",")
                            when(parts.count()) {
                                2 -> {
                                    left = parts[0]
                                    right = parts[1]
                                    parsed = true
                                }
                                else -> {
                                    parsed = false
                                }
                            }
                        }
                    }
                }
            }
        }

        fun isValid() : Boolean {
            return parsed && left.toIntOrNull() != null && right.toIntOrNull() != null
        }

        fun calculation() : Int {
            return (left.toIntOrNull() ?: 0) * (right.toIntOrNull() ?: 0)
        }
    }

}