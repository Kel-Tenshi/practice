package ci.nsu.mobile.main.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import ci.nsu.mobile.main.viewmodel.AuthViewModel
import androidx.compose.material3.MenuAnchorType
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    val firstName by viewModel.regFirstName.collectAsState()
    val lastName by viewModel.regLastName.collectAsState()
    val middleName by viewModel.regMiddleName.collectAsState()
    val birthDate by viewModel.regBirthDate.collectAsState()
    val gender by viewModel.regGender.collectAsState()
    val groupId by viewModel.regGroupId.collectAsState()
    val login by viewModel.regLogin.collectAsState()
    val password by viewModel.regPassword.collectAsState()
    val email by viewModel.regEmail.collectAsState()
    val phone by viewModel.regPhone.collectAsState()

    val firstNameError by viewModel.regFirstNameError.collectAsState()
    val lastNameError by viewModel.regLastNameError.collectAsState()
    val birthDateError by viewModel.regBirthDateError.collectAsState()
    val groupIdError by viewModel.regGroupIdError.collectAsState()
    val loginError by viewModel.regLoginError.collectAsState()
    val passwordError by viewModel.regPasswordError.collectAsState()
    val emailError by viewModel.regEmailError.collectAsState()

    val groups by viewModel.groups.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMsg by viewModel.errorMessage.collectAsState()

    var dropdownExpanded by remember { mutableStateOf(false) }
    val selectedGroup = groups.find { it.id == groupId }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Регистрация",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        errorMsg?.let {
            Text(
                text = it,
                color = MaterialTheme.colorScheme.error,
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(bottom = 16.dp)
            )
        }

        Text(
            text = "Персональные данные",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = lastName,
            onValueChange = { viewModel.regLastName.value = it },
            label = { Text("Фамилия *") },
            isError = lastNameError != null,
            supportingText = lastNameError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = firstName,
            onValueChange = { viewModel.regFirstName.value = it },
            label = { Text("Имя *") },
            isError = firstNameError != null,
            supportingText = firstNameError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = middleName,
            onValueChange = { viewModel.regMiddleName.value = it },
            label = { Text("Отчество") },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = birthDate,
            onValueChange = { viewModel.regBirthDate.value = it },
            label = { Text("Дата рождения (ГГГГ-ММ-ДД)") },
            placeholder = { Text("Пример: 2000-01-31") },
            isError = birthDateError != null,
            supportingText = birthDateError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("Пол: ", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = gender == "MALE",
                onClick = { viewModel.regGender.value = "MALE" },
                enabled = !isLoading
            )
            Text("Мужской")
            Spacer(modifier = Modifier.width(16.dp))
            RadioButton(
                selected = gender == "FEMALE",
                onClick = { viewModel.regGender.value = "FEMALE" },
                enabled = !isLoading
            )
            Text("Женский")
        }

        Spacer(modifier = Modifier.height(16.dp))

        ExposedDropdownMenuBox(
            expanded = dropdownExpanded,
            onExpandedChange = { if (!isLoading) dropdownExpanded = !dropdownExpanded }
        ) {
            OutlinedTextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(type = MenuAnchorType.PrimaryNotEditable, enabled = true),
                readOnly = true,
                value = selectedGroup?.name ?: "Выберите группу *",
                onValueChange = {},
                label = { Text("Учебная группа *") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = dropdownExpanded) },
                colors = ExposedDropdownMenuDefaults.outlinedTextFieldColors(),
                isError = groupIdError != null,
                supportingText = groupIdError?.let { { Text(it) } }
            )
            ExposedDropdownMenu(
                expanded = dropdownExpanded,
                onDismissRequest = { dropdownExpanded = false }
            ) {
                if (groups.isEmpty()) {
                    DropdownMenuItem(
                        text = { Text("Группы отсутствуют на сервере") },
                        onClick = {}
                    )
                } else {
                    groups.forEach { group ->
                        DropdownMenuItem(
                            text = { Text(group.name) },
                            onClick = {
                                viewModel.regGroupId.value = group.id
                                dropdownExpanded = false
                            }
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        Text(
            text = "Данные учетной записи",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.align(Alignment.Start).padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = login,
            onValueChange = { viewModel.regLogin.value = it },
            label = { Text("Логин *") },
            isError = loginError != null,
            supportingText = loginError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { viewModel.regPassword.value = it },
            label = { Text("Пароль *") },
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError = passwordError != null,
            supportingText = passwordError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { viewModel.regEmail.value = it },
            label = { Text("Email *") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            isError = emailError != null,
            supportingText = emailError?.let { { Text(it) } },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = phone,
            onValueChange = { viewModel.regPhone.value = it },
            label = { Text("Телефон") },
            placeholder = { Text("Например: +79998887766") },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading
        )

        Spacer(modifier = Modifier.height(32.dp))

        if (isLoading) {
            CircularProgressIndicator()
        } else {
            Button(
                onClick = { viewModel.register() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Зарегистрироваться")
            }

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(
                onClick = { viewModel.navigateTo(Screen.LOGIN) }
            ) {
                Text("Уже зарегистрированы? Войти")
            }
        }
    }
}