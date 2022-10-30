import org.junit.Test

class TresEnRayaTest {

    @Test
    fun `should tal when tal y tal`() {
        val game = Game()
        game.setNode(Game.User.HUMAN, 1)
        assert(1 !in game.getFreeNodes())

        try {
            game.setNode(Game.User.HUMAN, 10)
        } catch (e: Exception) {
            assert(true)
        }
    }
}
