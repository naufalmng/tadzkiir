package org.destinyardiente.tadzkiir.core.data.repository

import org.destinyardiente.tadzkiir.core.data.source.remote.RemoteDataSource
import org.destinyardiente.tadzkiir.core.data.source.remote.response.BaseApiResponse


class AppRepository(val remote: RemoteDataSource): BaseApiResponse()