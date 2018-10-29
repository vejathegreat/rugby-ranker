package com.ricknout.rugbyranker.vo

import com.ricknout.rugbyranker.api.Effective
import com.ricknout.rugbyranker.api.Entry
import com.ricknout.rugbyranker.api.Team
import com.ricknout.rugbyranker.api.WorldRugbyRankingsResponse
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class WorldRugbyRankingDataConverterTest {

    private lateinit var worldRugbyRankingsResponse: WorldRugbyRankingsResponse

    @Before
    fun setup() {
        val effective = Effective(label = "2018-10-29", millis = 1540771200000L, gmtOffset = 0)
        val team1 = Team(id = 100L, name = "Team 1", abbreviation = "T1")
        val entry1 = Entry(pos = 1, previousPos = 2, pts = 100f, previousPts = 90f, matches = 10, team = team1)
        val team2 = Team(id = 200L, name = "Team 2", abbreviation = "T2")
        val entry2 = Entry(pos = 2, previousPos = 3, pts = 90f, previousPts = 80f, matches = 10, team = team2)
        worldRugbyRankingsResponse = WorldRugbyRankingsResponse(
                effective = effective,
                entries = listOf(entry1, entry2),
                label = "Label"
        )
    }

    @Test
    fun getMensWorldRugbyRankingsFromWorldRugbyRankingsResponse() {
        val mensWorldRugbyRankings = WorldRugbyRankingDataConverter.getWorldRugbyRankingsFromWorldRugbyRankingsResponse(worldRugbyRankingsResponse, RankingsType.MENS)
        assertEquals(mensWorldRugbyRankings.size, worldRugbyRankingsResponse.entries.size)
        mensWorldRugbyRankings.forEachIndexed { index, mensWorldRugbyRanking ->
            val entry = worldRugbyRankingsResponse.entries[index]
            assertEquals(mensWorldRugbyRanking.teamId, entry.team.id)
            assertEquals(mensWorldRugbyRanking.teamName, entry.team.name)
            assertEquals(mensWorldRugbyRanking.teamAbbreviation, entry.team.abbreviation)
            assertEquals(mensWorldRugbyRanking.position, entry.pos)
            assertEquals(mensWorldRugbyRanking.previousPosition, entry.previousPos)
            assertEquals(mensWorldRugbyRanking.points, entry.pts)
            assertEquals(mensWorldRugbyRanking.previousPoints, entry.previousPts)
            assertEquals(mensWorldRugbyRanking.matches, entry.matches)
            assertEquals(mensWorldRugbyRanking.rankingsType, RankingsType.MENS)
        }
    }

    @Test
    fun getWomensWorldRugbyRankingsFromWorldRugbyRankingsResponse() {
        val womensWorldRugbyRankings = WorldRugbyRankingDataConverter.getWorldRugbyRankingsFromWorldRugbyRankingsResponse(worldRugbyRankingsResponse, RankingsType.WOMENS)
        assertEquals(womensWorldRugbyRankings.size, worldRugbyRankingsResponse.entries.size)
        womensWorldRugbyRankings.forEachIndexed { index, womensWorldRugbyRanking ->
            val entry = worldRugbyRankingsResponse.entries[index]
            assertEquals(womensWorldRugbyRanking.teamId, entry.team.id)
            assertEquals(womensWorldRugbyRanking.teamName, entry.team.name)
            assertEquals(womensWorldRugbyRanking.teamAbbreviation, entry.team.abbreviation)
            assertEquals(womensWorldRugbyRanking.position, entry.pos)
            assertEquals(womensWorldRugbyRanking.previousPosition, entry.previousPos)
            assertEquals(womensWorldRugbyRanking.points, entry.pts)
            assertEquals(womensWorldRugbyRanking.previousPoints, entry.previousPts)
            assertEquals(womensWorldRugbyRanking.matches, entry.matches)
            assertEquals(womensWorldRugbyRanking.rankingsType, RankingsType.WOMENS)
        }
    }

    @Test
    fun getEffectiveTimeFromWorldRugbyRankingsResponse() {
        val effectiveTime = WorldRugbyRankingDataConverter.getEffectiveTimeFromWorldRugbyRankingsResponse(worldRugbyRankingsResponse)
        assertEquals(effectiveTime, "2018-10-29")
        val effectiveGmtMinus10 = Effective(label = "2018-10-29", millis = 1540771200000L, gmtOffset = -10)
        val worldRugbyRankingsResponseGmtMinus10 = worldRugbyRankingsResponse.copy(effective = effectiveGmtMinus10)
        val effectiveTimeGmtMinus10 = WorldRugbyRankingDataConverter.getEffectiveTimeFromWorldRugbyRankingsResponse(worldRugbyRankingsResponseGmtMinus10)
        assertEquals(effectiveTimeGmtMinus10, "2018-10-28")
        val effectiveGmtPlus10 = Effective(label = "2018-10-29", millis = 1540771200000L, gmtOffset = 10)
        val worldRugbyRankingsResponseGmtPlus10 = worldRugbyRankingsResponse.copy(effective = effectiveGmtPlus10)
        val effectiveTimeGmtPlus10 = WorldRugbyRankingDataConverter.getEffectiveTimeFromWorldRugbyRankingsResponse(worldRugbyRankingsResponseGmtPlus10)
        assertEquals(effectiveTimeGmtPlus10, "2018-10-29")
    }
}
