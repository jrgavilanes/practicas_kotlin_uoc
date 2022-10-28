import java.time.LocalDate

data class Player(
    val dni: String = uniqueID(),
    val name: String,
    val surname: String,
    val dateOfBirth: LocalDate,
)

data class OrganizingEntity(
    val id: String,
    val name: String,
)

enum class SportingEventSize {
    SMALL_SCALE, MEDIUM_SCALE, BIG_SCALE
}

data class SportingEvent(
    val id: String = uniqueID(),
    val description: String,
    val sportingEventSize: SportingEventSize,
    val maxParticipant: Int,
    val players: MutableList<Player>? = mutableListOf(),
    val resources: List<Resource>? = null,
    val startDate: LocalDate,
    val finishDate: LocalDate,
    val ratings: MutableList<Rating> = mutableListOf(),
    var playerRating: Double = 0.0,
)

data class SportingEventForm(
    val id: String = uniqueID(),
    val sportingEvent: SportingEvent,
    val organizingEntity: OrganizingEntity,
    var accepted: Boolean? = null,
    var revisionDate: LocalDate? = null,
    var rejectedReason: String? = null,
)

enum class Resource {
    SECURITY, BASIC_VITAL_SUPPORT, VOLUNTEERS
}

data class Rating(
    val player: Player,
    val rate: Int,
    val comment: String?,
)

class SportEvents4Club() {
    val players = mutableMapOf<String, Player>()
    val organizingEntities = mutableMapOf<String, OrganizingEntity>()
    val sportingEventForms = mutableMapOf<String, SportingEventForm>()
    val sportingEvents = mutableMapOf<String, SportingEvent>()

    fun addPlayerToClub(player: Player): Player {
        players[player.dni] = player
        return players[player.dni]!!
    }

    fun addOrganizingEntity(organizingEntity: OrganizingEntity): OrganizingEntity {
        organizingEntities[organizingEntity.id] = organizingEntity
        return organizingEntities[organizingEntity.id]!!
    }

    fun addSportingEventForm(
        id: String,
        entity: OrganizingEntity,
        event: SportingEvent
    ): SportingEventForm {
        if (entity.id !in organizingEntities) {
            throw Error("Entity ${entity.name} does not exist")
        }
        val sportingEventForm = SportingEventForm(
            id = id,
            sportingEvent = event,
            organizingEntity = entity
        )
        sportingEventForms[sportingEventForm.id] = sportingEventForm

        return sportingEventForms[sportingEventForm.id]!!
    }

    fun evaluateEventForm(
        sportingEventForm: SportingEventForm,
        isAccepted: Boolean = true,
        rejectionReason: String = ""
    ): SportingEvent? {
        sportingEventForms[sportingEventForm.id]?.let {
            it.accepted = isAccepted
            it.revisionDate = LocalDate.now()
            if (!isAccepted && rejectionReason.isNotEmpty()) {
                it.rejectedReason = rejectionReason.trim()
            }
        } ?: throw Error("Eventform ${sportingEventForm.id} does not exist")

        return if (isAccepted) {
            sportingEvents[sportingEventForm.sportingEvent.id] = sportingEventForm.sportingEvent
            sportingEvents[sportingEventForm.sportingEvent.id]
        } else {
            null
        }
    }

    fun addPlayerToSportingEvent(sportingEvent: SportingEvent, player: Player) {
        if (player.dni !in players) throw Error("Player with id ${player.dni} does not exist")

        sportingEvents[sportingEvent.id]?.let {
            it.players?.add(player)
            if (it.players?.size!! > it.maxParticipant) {
                throw Error("Max number of participants reached. You are in the reserve list")
            }
        } ?: throw Error("SportingEvent with id ${sportingEvent.id} does not exist")
    }

    fun getRejectedFormsPercentage(): Int {
        var totalRejected = 0
        for (form in sportingEventForms) {
            if (form.value.accepted == false) {
                totalRejected++
            }
        }
        if (sportingEventForms.size == 0) return 0
        return ((totalRejected * 100.0) / sportingEventForms.size).toInt()
    }

    fun getEventsByOrgEntity(organizingEntity: OrganizingEntity): List<SportingEvent> {
        return sportingEventForms.filter {
            it.value.organizingEntity.id == organizingEntity.id &&
                it.value.accepted == true
        }.map { it.value.sportingEvent } ?: throw Error("There are no events by that organizing entity")
    }

    fun getAllSportingEvents(): Collection<SportingEvent> {
        val result = sportingEvents.map { it.value }
        if (result.isEmpty()) {
            throw Error("There are no events")
        }
        return result
    }

    fun getSportingEventsByPlayer(player: Player): List<SportingEvent> {
        val result = sportingEvents.filter { it.value.players?.contains(player) ?: false }.map { it.value }
        if (result.isEmpty()) {
            throw Error("There are no events by player ${player.dni}")
        }
        return result
    }

    fun createRating(
        player: Player,
        sportingEvent: SportingEvent,
        rate: Int,
        comment: String? = ""
    ): Boolean {
        if (player.dni !in players) {
            throw Error("Player with id ${player.dni} does not exist")
        }
        if (sportingEvent.id !in sportingEvents) {
            throw Error("SportingEvent with id ${sportingEvent.id} does not exist")
        }
        sportingEvents[sportingEvent.id]?.let {
            if (it.players?.filter { it.dni == player.dni }?.isEmpty() == true) {
                throw Error("Player did not play in that event")
            }
            if (it.ratings.filter { rating -> rating.player == player }.isNotEmpty()) {
                throw Error("Players can vote only once")
            }
            it.ratings.add(Rating(player = player, rate = rate, comment = comment))
            var total = 0
            for (currentRate in it.ratings) {
                total += currentRate.rate
            }
            it.playerRating = (total / it.ratings.size).toDouble()
        }
        return true
    }

    fun getRatingByEvent(sportingEvent: SportingEvent): Collection<Rating> {
        if (sportingEvent.id !in sportingEvents) {
            throw Error("SportingEvent with id ${sportingEvent.id} does not exist")
        }
        sportingEvents[sportingEvent.id]?.ratings?.let {
            return it
        } ?: throw Error("There are no ratings by this event")
    }

    fun getMostActivePlayer(): Player? {
        var gamesPlayed = mutableMapOf<Player, Int>()
        for (player in players) {
            for (event in sportingEvents) {
                if (event.value.players?.contains(player.value) == true) {
                    if (player.value !in gamesPlayed) {
                        gamesPlayed[player.value] = 1
                    } else {
                        gamesPlayed[player.value]?.plus(1)
                    }
                }
            }
        }
//        val result = gamesPlayed.toList().sortedByDescending { (_, value) -> value }.toMap()
        val result = gamesPlayed.toList().sortedByDescending { (_, value) -> value }
        return result[0].first
    }

    fun getMostValuableEvent(): SportingEvent {
        return sportingEvents.maxBy { it.value.playerRating }.value
    }
}

fun main() {
    val sportEvents4Club = SportEvents4Club()
    sportEvents4Club.addPlayerToClub(
        Player(
            dni = uniqueID(),
            name = "Juan Ramón",
            surname = "Gavilanes Sánchez",
            dateOfBirth = LocalDate.parse("08-06-1978", DATE_FORMATTER)
        )
    )
    println(sportEvents4Club.players)
    sportEvents4Club.sportingEvents["event_key"]
}


