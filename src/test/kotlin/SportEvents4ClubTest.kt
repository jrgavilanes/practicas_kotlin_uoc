import org.junit.After
import org.junit.Before
import org.junit.Test
import java.time.LocalDate

class SportEvents4ClubTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `add Player To Club`() {
        val sportEvents4Club = SportEvents4Club()
        val player = Player(
            dni = "48384911H",
            name = "Juan Ramón",
            surnames = "Gavilanes Sánchez",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        sportEvents4Club.addPlayerToClub(player)
        assert((sportEvents4Club.players[player.dni] == player))
    }

    @Test
    fun `add Organizing Entity`() {
        val sportEvents4Club = SportEvents4Club()
        val organizingEntity = OrganizingEntity(id = "abc", name = "cruz roja")
        sportEvents4Club.addOrganizingEntity(organizingEntity)
        assert((sportEvents4Club.organizingEntities["abc"] == organizingEntity))
    }

    @Test
    fun `addSportingEventForm entity`() {
        val sportEvents4Club = SportEvents4Club()
        val organizingEntity = OrganizingEntity(id = "abc", name = "cruz roja")
        val sportingEvent = SportingEvent(
            id = "spo",
            "evento 1",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )
        sportEvents4Club.addOrganizingEntity(organizingEntity)

        val result =
            sportEvents4Club.addSportingEventForm(id = "kkk", entity = organizingEntity, event = sportingEvent)

        assert(
            sportEvents4Club.sportingEventForms["kkk"]?.sportingEvent?.id == "spo" &&
                sportEvents4Club.sportingEventForms["kkk"]?.organizingEntity?.id == "abc"
        )
    }

    @Test
    fun `addSportingEventForm wrong entity`() {
        val sportEvents4Club = SportEvents4Club()
        val organizingEntity = OrganizingEntity(id = "abc", name = "cruz roja")
        val sportingEvent = SportingEvent(
            id = "spo",
            "evento 1",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )
//        sportEvents4Club.addOrganizingEntity(organizingEntity)

        try {
            val result =
                sportEvents4Club.addSportingEventForm(id = "kkk", entity = organizingEntity, event = sportingEvent)
        } catch (e: java.lang.Error) {
            assert(true)
        }
    }
}
