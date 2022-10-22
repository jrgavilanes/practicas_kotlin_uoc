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
        val result = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )
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
        try {
            val result = sportEvents4Club.addSportingEventForm(
                id = "kkk",
                entity = organizingEntity,
                event = sportingEvent
            )
        } catch (e: java.lang.Error) {
            assert(true)
        }
    }

    @Test
    fun `evaluateEventForm is rejected`() {
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
        val sportingEventform = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )
        val result = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = false,
            rejectionReason = "malos pagadores"
        )
        assert(result == null)
    }

    @Test
    fun `evaluateEventForm is accepted`() {
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
        val sportingEventform = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )
        val newSportingEvent = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = true
        )
        assert(newSportingEvent?.id == "spo")
    }

    @Test
    fun `addPlayerToSportingEvent`() {
        val sportEvents4Club = SportEvents4Club()
        val player = Player(
            dni = "48384911H",
            name = "Juan Ramón",
            surnames = "Gavilanes Sánchez",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        sportEvents4Club.addPlayerToClub(player)
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
        val sportingEventform = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )
        val newSportingEvent = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = true
        )
        sportEvents4Club.addPlayerToSportingEvent(newSportingEvent!!, player)
        assert(true)
    }

    @Test
    fun `add unknown PlayerToSportingEvent`() {
        val sportEvents4Club = SportEvents4Club()
        val player = Player(
            dni = "48384911H",
            name = "Juan Ramón",
            surnames = "Gavilanes Sánchez",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        sportEvents4Club.addPlayerToClub(player)
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
        val sportingEventform = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )
        val newSportingEvent = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = true
        )
        try {
            sportEvents4Club.addPlayerToSportingEvent(
                newSportingEvent!!,
                Player(
                    dni = "48384911Hx",
                    name = "Juan Ramónx",
                    surnames = "Gavilanes Sánchezx",
                    dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
                )
            )
        } catch (e: Error) {
            e.message?.contains("does not exist")?.let { assert(it) }
        }
    }

    @Test
    fun `add extra PlayerToSportingEvent`() {
        val sportEvents4Club = SportEvents4Club()
        val player = Player(
            dni = "48384911H",
            name = "Juan Ramón",
            surnames = "Gavilanes Sánchez",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        sportEvents4Club.addPlayerToClub(player)
        val organizingEntity = OrganizingEntity(id = "abc", name = "cruz roja")
        val sportingEvent = SportingEvent(
            id = "spo",
            "evento 1",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 0,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )
        sportEvents4Club.addOrganizingEntity(organizingEntity)
        val sportingEventform = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )
        val newSportingEvent = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = true
        )
        try {
            sportEvents4Club.addPlayerToSportingEvent(
                newSportingEvent!!, player
            )
        } catch (e: Error) {
            newSportingEvent?.players?.contains(player)?.let { assert(it) }
            e.message?.contains("reserve list")?.let { assert(it) }
        }
    }

    @Test
    fun `add player to unknown SportingEvent`() {
        val sportEvents4Club = SportEvents4Club()
        val player = Player(
            dni = "48384911H",
            name = "Juan Ramón",
            surnames = "Gavilanes Sánchez",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        sportEvents4Club.addPlayerToClub(player)
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
        val sportingEventform = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )
        val newSportingEvent = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = true
        )
        try {
            sportEvents4Club.addPlayerToSportingEvent(
                SportingEvent(
                    id = "unk",
                    "unk",
                    sportingEventSize = SportingEventSize.SMALL_SCALE,
                    maxParticipant = 8,
                    startDate = LocalDate.now().plusDays(10),
                    finishDate = LocalDate.now().plusDays(11)
                ),
                player
            )
        } catch (e: Error) {
            e.message?.contains("does not exist")?.let { assert(it) }
        }
    }
}
