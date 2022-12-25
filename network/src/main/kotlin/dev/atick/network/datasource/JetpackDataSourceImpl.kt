package dev.atick.network.datasource

import dev.atick.network.api.JetpackApi
import javax.inject.Inject

class JetpackDataSourceImpl @Inject constructor(
    private val jetpackApi: JetpackApi
) : JetpackDataSource