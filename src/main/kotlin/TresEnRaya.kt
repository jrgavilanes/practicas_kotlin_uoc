import java.lang.Exception

class Game() {

    enum class User {
        HUMAN,
        IA
    }

    data class Node(var id: Int, var owner: User? = null)

    private var board = mutableMapOf(
        1 to Node(id = 1, owner = null),
        2 to Node(id = 2, owner = null),
        3 to Node(id = 3, owner = null),
        4 to Node(id = 4, owner = null),
        5 to Node(id = 5, owner = null),
        6 to Node(id = 6, owner = null),
        7 to Node(id = 7, owner = null),
        8 to Node(id = 8, owner = null),
        9 to Node(id = 9, owner = null),
    )

    fun getFreeNodes() = board.filter { it.value.owner == null }.map { it.key }.sorted()

    private fun getIAChoice() = getFreeNodes().random()

    fun setNode(player: User, position: Int) {
        if (position !in board) {
            throw Exception("Selected position is not valid")
        }
        if (position !in getFreeNodes()) {
            throw Exception("Selected position is already in use")
        }
        board[position] = Node(id = position, owner = player)
    }

    fun askUserInput() {
        println("Select an available position ${getFreeNodes()}")
        val position = readLine()
        if (position?.toIntOrNull() == null) {
            println("error: You must type a number. Try again")
            return askUserInput()
        }
        try {
            setNode(User.HUMAN, position.toInt())
            if (getFreeNodes().isNotEmpty()) {
                val iaChoice = getIAChoice()
                setNode(User.IA, iaChoice)
                println("You have selected $position, and the computer $iaChoice")
            }
            printDesk()
        } catch (e: Exception) {
            println(e.message)
            askUserInput()
        }
    }

    fun isGameFinished(): Boolean {

        val winnerPositions = listOf(
            listOf(1, 2, 3),
            listOf(4, 5, 6),
            listOf(7, 8, 9),
            listOf(1, 4, 7),
            listOf(2, 5, 8),
            listOf(3, 6, 9),
            listOf(1, 5, 9),
            listOf(3, 5, 7),
        )

        val humanPositions = board.filter { it.value.owner == User.HUMAN }.map { it.key }
        val computerPositions = board.filter { it.value.owner == User.IA }.map { it.key }

        for (winnerPosition in winnerPositions) {
            if (winnerPosition.all { it in humanPositions }) {
                println("You have win with $winnerPosition")
                return true
            }
            if (winnerPosition.all { it in computerPositions }) {
                println("Computer wins with $winnerPosition")
                return true
            }
        }

        if (getFreeNodes().isEmpty()) {
            println("Nobody wins")
            return true
        }
        return false
    }

    fun printDesk() {

        var desk = """
            1 | 2 | 3
            ---------
            4 | 5 | 6
            ---------
            7 | 8 | 9
        """.trimIndent()

        for (x in board) {
            val position = x.key.toString()
            desk = when (x.value.owner) {
                User.HUMAN -> desk.replace(position, "X")
                User.IA -> desk.replace(position, "O")
                null -> {
                    desk.replace(position, " ")
                }
            }
        }

        println(desk)
    }

    fun printBanner() {
        val banner = """ 
         ________          _______    ________           ________   ________       ___    ___  ________     
        |\_____  \        |\  ___ \  |\   ___  \        |\   __  \ |\   __  \     |\  \  /  /||\   __  \    
        \|____|\ /_       \ \   __/| \ \  \\ \  \       \ \  \|\  \\ \  \|\  \    \ \  \/  /    \  \|\  \   
              \|\  \       \ \  \_|/__\ \  \\ \  \       \ \   _  _\\ \   __  \    \ \    / /  \ \   __  \  
             __\_\  \       \ \  \_|\ \\ \  \\ \  \       \ \  \\  \|\ \  \ \  \    \/  /  /    \ \  \ \  \ 
            |\_______\       \ \_______\\ \__\\ \__\       \ \__\\ _\ \ \__\ \__\ __/  / /       \ \__\ \__\
            \|_______|        \|_______| \|__| \|__|        \|__|\|__| \|__|\|__||\___/ /         \|__|\|__|
                                                                                 \|___|/                    
        """.trimIndent()
        println(banner)
    }
}

fun main() {
    val game = Game()
    game.printBanner()
    game.printDesk()
    while (!game.isGameFinished()) {
        game.askUserInput()
    }
}
