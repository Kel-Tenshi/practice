package ci.nsu.mobile.main.repository

import ci.nsu.mobile.main.model.*
import ci.nsu.mobile.main.network.ApiService
import ci.nsu.mobile.main.network.RetrofitClient
import ci.nsu.mobile.main.network.TokenManager

class AuthRepository(private val apiService: ApiService = RetrofitClient.apiService) {

    suspend fun login(login: String, password: String): Result<UserDto> {
        return try {
            val response = apiService.login(UserLoginRequest(login, password))
            TokenManager.token = response.token
            // Загружаем профиль вошедшего пользователя для отображения
            val user = apiService.getUserByLogin(login)
            Result.success(user)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun register(registerRequest: RegisterRequest): Result<Unit> {
        return try {
            apiService.register(registerRequest)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getUsers(): Result<List<UserDto>> {
        return try {
            val users = apiService.getUsers()
            Result.success(users)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    suspend fun getGroups(): Result<List<GroupDto>> {
        return try {
            val groups = apiService.getGroups()
            Result.success(groups)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}