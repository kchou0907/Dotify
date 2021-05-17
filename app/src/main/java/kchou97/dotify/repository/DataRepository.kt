package kchou97.dotify.repository

import kchou97.dotify.model.User
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

class DataRepository {

    private val userService = Retrofit.Builder()
        .baseUrl("https://raw.githubusercontent.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(userService::class.java)

    suspend fun getUser() = userService.getUser()

}

interface userService {
    @GET("echeeUW/codesnippets/master/user_info.json")
    suspend fun getUser(): User
}