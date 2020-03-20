package com.guesswho.covidstats.model

/**
 *
 * @author Maxim on 19.03.20
 */
data class CasesResponse(val confirmed: CaseModel, val deaths: CaseModel, val latest: LatestModel, val recovered: CaseModel)