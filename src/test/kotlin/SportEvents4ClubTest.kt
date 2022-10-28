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
            dni = "11111111H",
            name = "jugador 1",
            surname = "apellido",
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
            dni = "11111111H",
            name = "jugador 1",
            surname = "apellido",
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
            dni = "11111111H",
            name = "jugador 1",
            surname = "apellido",
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
                    dni = "11111111Hx",
                    name = "jugador 1x",
                    surname = "apellidox",
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
            dni = "11111111H",
            name = "jugador 1",
            surname = "apellido",
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
            dni = "11111111H",
            name = "jugador 1",
            surname = "apellido",
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

    @Test
    fun getEventsByOrgEntity() {
        val sportEvents4Club = SportEvents4Club()
        val organizingEntity = OrganizingEntity(id = "abc", name = "cruz roja")
        val organizingEntity2 = OrganizingEntity(id = "abc2", name = "cruz roja2")
        val organizingEntity3 = OrganizingEntity(id = "abc3", name = "cruz roja3")
        val sportingEvent = SportingEvent(
            id = "spo",
            "evento 1",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )
        val sportingEvent2 = SportingEvent(
            id = "spo2",
            "evento 2",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            resources = listOf(Resource.BASIC_VITAL_SUPPORT, Resource.SECURITY, Resource.VOLUNTEERS),
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )

        val sportingEvent3 = SportingEvent(
            id = "spo3",
            "evento 3",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )

        sportEvents4Club.addOrganizingEntity(organizingEntity)
        sportEvents4Club.addOrganizingEntity(organizingEntity2)
        sportEvents4Club.addOrganizingEntity(organizingEntity3)

        val sportingEventform = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )

        val sportingEventform2 = sportEvents4Club.addSportingEventForm(
            id = "kkk2",
            entity = organizingEntity2,
            event = sportingEvent2
        )

        val sportingEventform3 = sportEvents4Club.addSportingEventForm(
            id = "kkk3",
            entity = organizingEntity2,
            event = sportingEvent3
        )

        val newSportingEvent = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = true
        )

        val newSportingEvent2 = sportEvents4Club.evaluateEventForm(
            sportingEventform2,
            isAccepted = true
        )

        val newSportingEvent3 = sportEvents4Club.evaluateEventForm(
            sportingEventform3
        )

        val result = sportEvents4Club.getEventsByOrgEntity(organizingEntity)
        val result2 = sportEvents4Club.getEventsByOrgEntity(organizingEntity2)
        val result3 = sportEvents4Club.getEventsByOrgEntity(organizingEntity3)

        assert(result.size == 1 && result2.size == 2 && result3.size == 0)

        val resultAllEvents = sportEvents4Club.getAllSportingEvents()

        assert(resultAllEvents.size == 3)
    }

    @Test
    fun `getAllSportingEvents when empty`() {
        val sportEvents4Club = SportEvents4Club()
        try {
            sportEvents4Club.getAllSportingEvents()
        } catch (e: Error) {
            e.message?.contains("are no events")?.let { assert(it) }
        }
    }

    @Test
    fun `getRejectedFormsPercentage when empty`() {
        val sportEvents4Club = SportEvents4Club()
        val result = sportEvents4Club.getRejectedFormsPercentage()
        assert(result == 0)
    }

    @Test
    fun `getRejectedFormsPercentage`() {
        val sportEvents4Club = SportEvents4Club()
        val organizingEntity = OrganizingEntity(id = "abc", name = "cruz roja")
        val organizingEntity2 = OrganizingEntity(id = "abc2", name = "cruz roja2")
        val organizingEntity3 = OrganizingEntity(id = "abc3", name = "cruz roja3")
        val sportingEvent = SportingEvent(
            id = "spo",
            "evento 1",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )
        val sportingEvent2 = SportingEvent(
            id = "spo2",
            "evento 2",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            resources = listOf(Resource.BASIC_VITAL_SUPPORT, Resource.SECURITY, Resource.VOLUNTEERS),
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )

        val sportingEvent3 = SportingEvent(
            id = "spo3",
            "evento 3",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )

        sportEvents4Club.addOrganizingEntity(organizingEntity)
        sportEvents4Club.addOrganizingEntity(organizingEntity2)
        sportEvents4Club.addOrganizingEntity(organizingEntity3)

        val sportingEventform = sportEvents4Club.addSportingEventForm(
            id = "kkk",
            entity = organizingEntity,
            event = sportingEvent
        )

        val sportingEventform2 = sportEvents4Club.addSportingEventForm(
            id = "kkk2",
            entity = organizingEntity2,
            event = sportingEvent2
        )

        val sportingEventform3 = sportEvents4Club.addSportingEventForm(
            id = "kkk3",
            entity = organizingEntity2,
            event = sportingEvent3
        )

        val newSportingEvent = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = true
        )

        val newSportingEvent2 = sportEvents4Club.evaluateEventForm(
            sportingEventform2,
            isAccepted = true
        )

        val newSportingEvent3 = sportEvents4Club.evaluateEventForm(
            sportingEventform3,
            isAccepted = false
        )

        val result = sportEvents4Club.getRejectedFormsPercentage()
        assert(result == 33)
    }

    @Test
    fun `getSportingEventsByPlayer`() {

        val sportEvents4Club = SportEvents4Club()
        val player = Player(
            dni = "11111111H",
            name = "jugador 1",
            surname = "apellido",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        val player2 = Player(
            name = "name2",
            surname = "surname2",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        val player3 = Player(
            name = "name3",
            surname = "surname3",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        sportEvents4Club.addPlayerToClub(player)
        sportEvents4Club.addPlayerToClub(player2)
        sportEvents4Club.addPlayerToClub(player3)
        val organizingEntity = OrganizingEntity(id = "abc", name = "cruz roja")
        val sportingEvent = SportingEvent(
            id = "spo",
            "evento 1",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )
        val sportingEvent2 = SportingEvent(
            id = "spo2",
            "evento 2",
            sportingEventSize = SportingEventSize.BIG_SCALE,
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
        val sportingEventform2 = sportEvents4Club.addSportingEventForm(
            id = "kkk2",
            entity = organizingEntity,
            event = sportingEvent2
        )
        val newSportingEvent = sportEvents4Club.evaluateEventForm(
            sportingEventform,
            isAccepted = true
        )
        val newSportingEvent2 = sportEvents4Club.evaluateEventForm(
            sportingEventform2,
            isAccepted = true
        )

        sportEvents4Club.addPlayerToSportingEvent(newSportingEvent!!, player)
        sportEvents4Club.addPlayerToSportingEvent(newSportingEvent!!, player2)
        sportEvents4Club.addPlayerToSportingEvent(newSportingEvent2!!, player2)

        val result = sportEvents4Club.getSportingEventsByPlayer(player)
        val result2 = sportEvents4Club.getSportingEventsByPlayer(player2)

        assert(result.size == 1 && result2.size == 2)

        try {
            sportEvents4Club.getSportingEventsByPlayer(player3)
        } catch (e: Error) {
            e.message?.contains("no events by player")?.let { assert(it) }
        }
    }

    @Test
    fun `createRating`() {

        val sportEvents4Club = SportEvents4Club()
        val player = Player(
            dni = "4838",
            name = "jugador 1",
            surname = "Gavilanes",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        val player2 = Player(
            name = "xxx",
            surname = "xxxx",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
        sportEvents4Club.addPlayerToClub(player)
        sportEvents4Club.addPlayerToClub(player2)

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

        try {
            val resultRatingsUnknownEvent = sportEvents4Club.getRatingByEvent(sportingEvent)
        } catch (e: Error) {
            e.message?.lowercase()?.contains("does not exist")?.let { assert(it) }
        }

        val result = sportEvents4Club.createRating(
            player = player,
            sportingEvent = newSportingEvent,
            rate = 8,
            comment = "me gusta"
        )

        assert(result)

        try {
            val resultSamePlayerCommentAgain = sportEvents4Club.createRating(
                player = player,
                sportingEvent = newSportingEvent,
                rate = 8,
                comment = "me gusta"
            )
        } catch (e: Error) {
            e.message?.lowercase()?.contains("can vote only once")?.let { assert(it) }
        }

        try {
            val resultPlayerNotSignedInEvent = sportEvents4Club.createRating(
                player = player2,
                sportingEvent = newSportingEvent,
                rate = 8
            )
        } catch (e: Error) {
            e.message?.lowercase()?.contains("did not play in that event")?.let { assert(it) }
        }

        val sportingEvent2 = SportingEvent(
            id = "spoNO",
            "evento NO",
            sportingEventSize = SportingEventSize.SMALL_SCALE,
            maxParticipant = 8,
            startDate = LocalDate.now().plusDays(10),
            finishDate = LocalDate.now().plusDays(11)
        )

        try {
            val result2 = sportEvents4Club.createRating(
                player = player,
                sportingEvent = sportingEvent2,
                rate = 8,
                comment = "envento no existe"
            )
        } catch (e: Error) {
            e.message?.contains("does not exist")?.let { assert(it) }
            e.message?.lowercase()?.contains("sportingevent")?.let { assert(it) }
        }

        try {
            val resultNoExistentPlayer = sportEvents4Club.createRating(
                player = Player(
                    name = "no exist",
                    surname = "no",
                    dateOfBirth = LocalDate.now().plusDays(10)
                ),
                sportingEvent = newSportingEvent,
                rate = 8,
                comment = "me gusta"
            )
        } catch (e: Error) {
            e.message?.lowercase()?.contains("player")?.let { assert(it) }
            e.message?.lowercase()?.contains("does not exist")?.let { assert(it) }
        }

        val resultRatings = sportEvents4Club.getRatingByEvent(sportingEvent)

        assert(resultRatings.size == 1)

        try {
            val resultRatingsUnknownEvent = sportEvents4Club.getRatingByEvent(sportingEvent2)
        } catch (e: Error) {
            e.message?.lowercase()?.contains("does not exist")?.let { assert(it) }
        }

        val mostValuablePlayer = sportEvents4Club.getMostActivePlayer()

        assert(mostValuablePlayer == player)

        val mostValuableEvent = sportEvents4Club.getMostValuableEvent()

        assert(mostValuableEvent == sportingEvent)
    }
}
