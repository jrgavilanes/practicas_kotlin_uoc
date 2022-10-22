//data class Horse(
//    val name: String,
//    var position: Int = 0,
//    var owner: String? = null,
//    var enabled: Boolean = true
//)
//
//val cards = mapOf(
//    "T1" to listOf("C1", "C2", "C3"),
//    "T2" to listOf("C4", "C1"),
//    "T3" to listOf("C5", "C6", "C1"),
//    "T4" to listOf("C7", "C2"),
//    "T5" to listOf("C7", "C4", "C3", "C6"),
//    "T6" to listOf("C5", "C7"),
//    "T7" to listOf("C8", "C2", "C6"),
//    "T8" to listOf("C4", "C8"),
//    "T9" to listOf("C5", "C3", "C8"),
//)
//
//val usedCards = mutableListOf<String>()
//
//val raceState = mapOf(
//    "C1" to Horse(name = "C1", position = 1, owner = null, enabled = true),
//    "C2" to Horse(name = "C2", position = 1, owner = null, enabled = true),
//    "C3" to Horse(name = "C3", position = 1, owner = null, enabled = true),
//    "C4" to Horse(name = "C4", position = 1, owner = null, enabled = true),
//    "C5" to Horse(name = "C5", position = 1, owner = null, enabled = true),
//    "C6" to Horse(name = "C6", position = 1, owner = null, enabled = true),
//    "C7" to Horse(name = "C7", position = 1, owner = null, enabled = true),
//    "C8" to Horse(name = "C8", position = 1, owner = null, enabled = true),
//)
//
///**
// * Higher value is better option for the given player.
// * Weight's distribution:
// *   Increase my almost winner horse * 20
// *   Disable rival's almost winner horse * 10
// *   Increase my regular horses * 3
// *   Disable rival's regular horses * 2
// *   Get New Horse * 1
// */
//fun heuristic(player: Player, cardName: String): Int {
//    val myActiveHorses = raceState.filter {
//        it.value.owner == player.name && it.value.enabled
//    }
//
//    val rivalActiveHorses = raceState.filter {
//        it.value.owner != null &&
//            it.value.owner != player.name &&
//            it.value.enabled
//    }
//
//    var result: Int = 0
//
//    for (horse in cards[cardName].orEmpty()) {
//        if (horse in myActiveHorses.keys) {
//            raceState[horse]?.let {
//                result += when (it.position) {
//                    3 -> 20 // I'm about to win
//                    else -> 3
//                }
//            }
//        } else if (horse in rivalActiveHorses.keys) {
//            raceState[horse]?.let {
//                result += when (it.position) {
//                    3 -> 10 // it's about to win !!
//                    else -> 2
//                }
//            }
//        } else if (raceState[horse]?.owner == null) {
//            // Get a new horse
//            result += 1
//        }
//    }
//
//    return result
//}
//
//data class Player(val name: String) {
//
//    fun pickCard(cardName: String) {
//        println("Jugador ${this.name} ha elegido la carta $cardName")
//        usedCards.add(cardName)
//        for (horse in cards[cardName].orEmpty()) {
//            with(raceState[horse]) {
//                if (this?.enabled == true) {
//                    if (this.owner == null) {
//                        this.owner = this@Player.name
//                        position = position.plus(1)
//                    } else {
//                        if (this.owner == this@Player.name) {
//                            position = position.plus(1)
//                        } else {
//                            enabled = false
//                        }
//                    }
//                    if (position == 4) {
//                        println("${this@Player.name} ha ganado con caballo $horse")
//                        return
//                    }
//                }
//            }
//        }
//        println(raceState)
//        isGameOver()
//    }
//}
//
//fun showAvailableCardsToPlayer(player: Player) {
//
//    println("**********")
//    println("${player.name}, elige una carta ")
//    println("**********")
//    for (card in cards) {
//        if (card.key !in usedCards) {
//            println(
//                "${card.key} (Estimación heurística ${
//                heuristic(
//                    player = player,
//                    cardName = card.key
//                )
//                })"
//            )
//        }
//    }
//    println()
//}
//
//fun isGameOver(): Boolean {
//    if (raceState.filter { !it.value.enabled }.size == 9) {
//        println("Empate -> Todos los caballos están descalificados")
//        return true
//    }
//
//    if (raceState.filter { it.value.position == 4 }.isEmpty() &&
//        usedCards.size == 9
//    ) {
//        println("Empate -> Se han usado todas las cartas y no hay ganador")
//        return true
//    }
//
//    return false
//}
//
//fun main() {
//    val player1 = Player("Juanra")
//    val player2 = Player("Máquina")
//
//    // set the state
//    usedCards += mutableListOf("T1", "T3", "T4", "T5", "T7")
//    raceState["C1"]?.enabled = false
//    raceState["C2"]?.enabled = false
//    raceState["C3"]?.enabled = false
//    raceState["C6"]?.enabled = false
//    raceState["C4"]?.owner = player1.name
//    raceState["C4"]?.position = 2
//    raceState["C5"]?.owner = player1.name
//    raceState["C5"]?.position = 2
//    raceState["C7"]?.owner = player1.name
//    raceState["C7"]?.position = 3
//    raceState["C8"]?.owner = player2.name
//    raceState["C8"]?.position = 2
//
//    // let's play
//    showAvailableCardsToPlayer(player2)
//    player2.pickCard("T6")
//    showAvailableCardsToPlayer(player1)
//    player1.pickCard("T8")
//    showAvailableCardsToPlayer(player2)
//    player2.pickCard("T2")
//    showAvailableCardsToPlayer(player1)
//    player1.pickCard("T9")
//}
