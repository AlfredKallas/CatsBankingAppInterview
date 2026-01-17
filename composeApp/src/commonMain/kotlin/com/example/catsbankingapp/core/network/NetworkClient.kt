package com.example.catsbankingapp.core.network

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.request
import io.ktor.http.HttpStatusCode
import io.ktor.http.isSuccess
import io.ktor.serialization.JsonConvertException
import io.ktor.util.reflect.TypeInfo
import io.ktor.util.reflect.typeInfo
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class NetworkClient(
    private val dispatcher: CoroutineDispatcher,
    private val appConfig: AppConfig,
    private val httpClient: HttpClient
) {

    inline fun <reified T : Any> performNetworkCall(serviceType: ServiceType<T>): Flow<Result<T>> {
        return performNetworkCallInternal(serviceType, typeInfo<T>())
    }

    @PublishedApi
    internal fun <T : Any> performNetworkCallInternal(
        serviceType: ServiceType<T>,
        typeInfo: TypeInfo,
    ): Flow<Result<T>> = flow {
        try {
            val httpResponse = httpClient.request(appConfig.baseUrl) {
                url({
                    pathSegments = serviceType.path.split("/")
                    serviceType.queryParameters.forEach {
                        parameters.append(it.key, it.value)
                    }
                    serviceType.headers.forEach {
                        headers.append(it.key, it.value)
                    }
                })
                method = serviceType.method
            }
            if (!httpResponse.status.isSuccess()) {
                val error: Result<T> = parseNetworkErrorExceptions(httpResponse.status)
                emit(error)
                return@flow
            }
            emit(Result.success(httpResponse.body(typeInfo)))
        } catch (ex: Exception) {
            emit(parseAndConvertException(ex))
        }
    }.flowOn(dispatcher)

    private fun <T>parseAndConvertException(ex: Throwable): Result<T> = when (ex) {
        is IllegalStateException -> Result.failure(CatsBankingException.UnknownErrorException("Illegal State: ${ex.message}"))
        is JsonConvertException -> Result.failure(CatsBankingException.UnknownErrorException("Json Convert Error: ${ex.message}"))
        else -> Result.failure(CatsBankingException.UnknownErrorException("Unknown Error: ${ex.message}"))
    }

    private fun <T>parseNetworkErrorExceptions(httpResponseCode: HttpStatusCode): Result<T> =
        when (httpResponseCode) {
            HttpStatusCode.BadRequest -> Result.failure(
                CatsBankingException.BadRequestException("Bad Request")
            )
            HttpStatusCode.Unauthorized -> Result.failure(
                CatsBankingException.UnauthorizedException("Unauthorized Request")
            )
            HttpStatusCode.Forbidden -> Result.failure(
                CatsBankingException.ForbiddenException("Forbidden Request")
            )
            HttpStatusCode.NotFound -> Result.failure(
                CatsBankingException.NotFoundException("Request Not Found")
            )
            HttpStatusCode.InternalServerError -> Result.failure(
                CatsBankingException.InternalServerErrorException("Internal Server Error")
            )
            else -> Result.failure(CatsBankingException.UnknownErrorException("Unknown Error"))
        }

}

