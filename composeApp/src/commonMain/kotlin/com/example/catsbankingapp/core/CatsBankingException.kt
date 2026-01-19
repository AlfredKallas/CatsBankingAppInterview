package com.example.catsbankingapp.core

sealed class CatsBankingException(open val errorMessage: String): Throwable(errorMessage){
    data class BadRequestException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class UnauthorizedException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class InternalServerErrorException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class ForbiddenException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class NotFoundException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class UnknownErrorException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class NetworkConnectionException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class NoLocalStorageException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class TimeoutErrorException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class IllegalStateErrorException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class UnknownHostErrorException(override val errorMessage: String) : CatsBankingException(errorMessage)
    data class JsonParsingErrorException(override val errorMessage: String) : CatsBankingException(errorMessage)
}