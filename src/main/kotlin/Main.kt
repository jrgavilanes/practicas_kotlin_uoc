//data class Horse(
//    val name: String,
//    var position: Int? = null
//)
//
//data class Player(
//    val name: String,
//    var horses: MutableList<Horse> = mutableListOf(),
//    var cards: MutableList<Card> = mutableListOf()
//) {
//    fun pickCard(card: Card) {
//        cards.add(card)
//        for (horseName in card.horsesNames) {
//            horses.add(Horse(horseName, 1))
//        }
//    }
//}
//
//data class Card(val name: String, var horsesNames: List<String>)
//
//fun main() {
//
//    val cards = listOf(
//        Card(name = "T1", listOf("C1", "C2", "C3")),
//        Card(name = "T2", listOf("C4", "C1")),
//        Card(name = "T3", listOf("C5", "C6", "C1")),
//        Card(name = "T4", listOf("C7", "C2")),
//        Card(name = "T5", listOf("C7", "C4", "C3", "C6")),
//        Card(name = "T6", listOf("C5", "C7")),
//        Card(name = "T7", listOf("C8", "C2", "C6")),
//        Card(name = "T8", listOf("C4", "C8")),
//        Card(name = "T9", listOf("C5", "C3", "C8")),
//    )
//
//    var usedCards = mutableListOf<Card>()
//
//    val player1 = Player("player1", mutableListOf())
//    val player2 = Player("player2", mutableListOf())
//
//    player1.horses.add(Horse("C1", 1))
//    player2.horses.add(Horse("C3", 1))
//    player1.horses.add(Horse("C2", 2))
//    player1.horses.remove()
//
//    print(player1)
//    print(player2)
//}
