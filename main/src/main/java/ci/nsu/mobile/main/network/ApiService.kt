package ci.nsu.mobile.main.network

import ci.nsu.mobile.main.model.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("auth/login")
    suspend fun login(@Body request: UserLoginRequest): AuthResponseDto

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponseDto

    @GET("users")
    suspend fun getUsers(): List<UserDto>

    @GET("groups")
    suspend fun getGroups(): List<GroupDto>

    @GET("users/login/{login}")
    suspend fun getUserByLogin(@Path("login") login: String): UserDto
}