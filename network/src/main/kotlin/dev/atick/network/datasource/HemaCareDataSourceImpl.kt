package dev.atick.network.datasource

import dev.atick.network.api.HemaCareApi
import javax.inject.Inject

class HemaCareDataSourceImpl @Inject constructor(
    private val hemaCareApi: HemaCareApi
) : HemaCareDataSource