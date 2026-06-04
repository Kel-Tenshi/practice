package ci.nsu.mobile.main.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ci.nsu.mobile.main.model.*
import ci.nsu.mobile.main.repository.AuthRepository
import ci.nsu.mobile.main.network.TokenManager
import ci.nsu.mobile.main.ui.Screen
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class AuthViewModel(private val repository: AuthRepository = AuthRepository()) : ViewModel() {

    private val _currentScreen = MutableStateFlow(Screen.LOGIN)
    val currentScreen = _currentScreen.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _currentUser = MutableStateFlow<UserDto?>(null)
    val currentUser = _currentUser.asStateFlow()

    private val _groups = MutableStateFlow<List<GroupDto>>(emptyList())
    val groups = _groups.asStateFlow()

    private val _usersList = MutableStateFlow<List<UserDto>>(emptyList())
    val usersList = _usersList.asStateFlow()

    // --- Поля ввода: Экран Входа ---
    val loginUsername = MutableStateFlow("")
    val loginPassword = MutableStateFlow("")

    private val _loginUsernameError = MutableStateFlow<String?>(null)
    val loginUsernameError = _loginUsernameError.asStateFlow()

    private val _loginPasswordError = MutableStateFlow<String?>(null)
    val loginPasswordError = _loginPasswordError.asStateFlow()

    // --- Поля ввода: Экран Регистрации ---
    val regFirstName = MutableStateFlow("")
    val regLastName = MutableStateFlow("")
    val regMiddleName = MutableStateFlow("")
    val regBirthDate = MutableStateFlow("")
    val regGender = MutableStateFlow("MALE")
    val regGroupId = MutableStateFlow<Long?>(null)
    val regLogin = MutableStateFlow("")
    val regPassword = MutableStateFlow("")
    val regEmail = MutableStateFlow("")
    val regPhone = MutableStateFlow("")

    private val _regFirstNameError = MutableStateFlow<String?>(null)
    val regFirstNameError = _regFirstNameError.asStateFlow()

    private val _regLastNameError = MutableStateFlow<String?>(null)
    val regLastNameError = _regLastNameError.asStateFlow()

    private val _regBirthDateError = MutableStateFlow<String?>(null)
    val regBirthDateError = _regBirthDateError.asStateFlow()

    private val _regGroupIdError = MutableStateFlow<String?>(null)
    val regGroupIdError = _regGroupIdError.asStateFlow()

    private val _regLoginError = MutableStateFlow<String?>(null)
    val regLoginError = _regLoginError.asStateFlow()

    private val _regPasswordError = MutableStateFlow<String?>(null)
    val regPasswordError = _regPasswordError.asStateFlow()

    private val _regEmailError = MutableStateFlow<String?>(null)
    val regEmailError = _regEmailError.asStateFlow()

    init {
        // Если токен уже сохранён, сразу открываем главный экран
        if (TokenManager.token != null) {
            _currentScreen.value = Screen.MAIN
            loadUsers()
        } else {
            _currentScreen.value = Screen.LOGIN
        }
    }

    fun navigateTo(screen: Screen) {
        _errorMessage.value = null
        _currentScreen.value = screen
        if (screen == Screen.REGISTER) {
            loadGroups()
        } else if (screen == Screen.MAIN) {
            loadUsers()
        }
    }

    fun loadGroups() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            repository.getGroups()
                .onSuccess { _groups.value = it }
                .onFailure { _errorMessage.value = "Ошибка загрузки групп: ${it.localizedMessage}" }
            _isLoading.value = false
        }
    }

    fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            repository.getUsers()
                .onSuccess { _usersList.value = it }
                .onFailure { _errorMessage.value = "Ошибка загрузки пользователей: ${it.localizedMessage}" }
            _isLoading.value = false
        }
    }

    fun login() {
        var hasError = false
        if (loginUsername.value.isBlank()) {
            _loginUsernameError.value = "Логин не может быть пустым"
            hasError = true
        } else {
            _loginUsernameError.value = null
        }

        if (loginPassword.value.isBlank()) {
            _loginPasswordError.value = "Пароль не может быть пустым"
            hasError = true
        } else {
            _loginPasswordError.value = null
        }

        if (hasError) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            repository.login(loginUsername.value, loginPassword.value)
                .onSuccess { user ->
                    _currentUser.value = user
                    navigateTo(Screen.MAIN)
                }
                .onFailure {
                    _errorMessage.value = "Авторизация отклонена: неверные учетные данные."
                }
            _isLoading.value = false
        }
    }

    fun register() {
        var hasError = false

        if (regLastName.value.isBlank()) {
            _regLastNameError.value = "Фамилия обязательна"
            hasError = true
        } else {
            _regLastNameError.value = null
        }

        if (regFirstName.value.isBlank()) {
            _regFirstNameError.value = "Имя обязательно"
            hasError = true
        } else {
            _regFirstNameError.value = null
        }

        val datePattern = """\d{4}-\d{2}-\d{2}""".toRegex()
        if (regBirthDate.value.isNotBlank() && !regBirthDate.value.matches(datePattern)) {
            _regBirthDateError.value = "Неверный формат даты (ГГГГ-ММ-ДД)"
            hasError = true
        } else {
            _regBirthDateError.value = null
        }

        if (regGroupId.value == null) {
            _regGroupIdError.value = "Выберите учебную группу"
            hasError = true
        } else {
            _regGroupIdError.value = null
        }

        if (regLogin.value.isBlank()) {
            _regLoginError.value = "Логин обязателен"
            hasError = true
        } else {
            _regLoginError.value = null
        }

        if (regPassword.value.length < 6) {
            _regPasswordError.value = "Пароль должен содержать от 6 символов"
            hasError = true
        } else {
            _regPasswordError.value = null
        }

        val emailPattern = """[a-zA-Z0-9._-]+@[a-z]+\.[a-z]+""".toRegex()
        if (regEmail.value.isBlank() || !regEmail.value.matches(emailPattern)) {
            _regEmailError.value = "Некорректный формат email"
            hasError = true
        } else {
            _regEmailError.value = null
        }

        if (hasError) return

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val person = PersonDto(
                firstName = regFirstName.value,
                lastName = regLastName.value,
                middleName = regMiddleName.value.ifBlank { null },
                birthDate = regBirthDate.value.ifBlank { null },
                gender = regGender.value,
                groupId = regGroupId.value!!
            )

            val request = RegisterRequest(
                login = regLogin.value,
                password = regPassword.value,
                email = regEmail.value,
                phoneNumber = regPhone.value.ifBlank { null },
                roleId = 1,
                authAllowed = true,
                person = person
            )

            repository.register(request)
                .onSuccess {
                    _errorMessage.value = "Регистрация прошла успешно! Вы можете войти."
                    navigateTo(Screen.LOGIN)
                    loginUsername.value = regLogin.value
                }
                .onFailure {
                    _errorMessage.value = "Ошибка при регистрации: ${it.localizedMessage}"
                }
            _isLoading.value = false
        }
    }

    fun logout() {
        TokenManager.clearToken()
        _currentUser.value = null
        _usersList.value = emptyList()
        loginPassword.value = ""
        navigateTo(Screen.LOGIN)
    }
}