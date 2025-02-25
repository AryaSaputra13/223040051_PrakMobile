package com.example.pertemuan4latihan2

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            // Pastikan menggunakan MaterialTheme dari Material3
            MaterialTheme(
                colorScheme = lightColorScheme()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    FormRegister()
                }
            }
        }
    }
}

@Composable
fun FormRegister(modifier: Modifier = Modifier) {
    val context = LocalContext.current
    val nama = remember { mutableStateOf(TextFieldValue("")) }
    val username = remember { mutableStateOf(TextFieldValue("")) }
    val nomorTelepon = remember { mutableStateOf(TextFieldValue("")) }
    val email = remember { mutableStateOf(TextFieldValue("")) }
    val alamat = remember { mutableStateOf(TextFieldValue("")) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = "Nama", modifier = Modifier.padding(4.dp))
        TextField(value = nama.value, onValueChange = { nama.value = it }, modifier = Modifier.fillMaxWidth())

        Text(text = "Username", modifier = Modifier.padding(4.dp))
        TextField(value = username.value, onValueChange = { username.value = it }, modifier = Modifier.fillMaxWidth())

        Text(text = "Nomor Telepon", modifier = Modifier.padding(4.dp))
        TextField(
            value = nomorTelepon.value,
            onValueChange = { nomorTelepon.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Email", modifier = Modifier.padding(4.dp))
        TextField(
            value = email.value,
            onValueChange = { email.value = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
            modifier = Modifier.fillMaxWidth()
        )

        Text(text = "Alamat Rumah", modifier = Modifier.padding(4.dp))
        TextField(value = alamat.value, onValueChange = { alamat.value = it }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    if (nama.value.text.isNotEmpty() && username.value.text.isNotEmpty() &&
                        nomorTelepon.value.text.isNotEmpty() && email.value.text.isNotEmpty() && alamat.value.text.isNotEmpty()) {
                        Toast.makeText(context, "Halo, ${nama.value.text}", Toast.LENGTH_LONG).show()
                    } else {
                        Toast.makeText(context, "Semua inputan harus diisi", Toast.LENGTH_LONG).show()
                    }
                }
            ) {
                Text(text = "Simpan", style = TextStyle(color = Color.White, fontSize = 18.sp))
            }

            Spacer(modifier = Modifier.width(8.dp))

            Button(
                modifier = Modifier.weight(1f),
                onClick = {
                    nama.value = TextFieldValue("")
                    username.value = TextFieldValue("")
                    nomorTelepon.value = TextFieldValue("")
                    email.value = TextFieldValue("")
                    alamat.value = TextFieldValue("")
                }
            ) {
                Text(text = "Reset", style = TextStyle(color = Color.White, fontSize = 18.sp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RegisterPreview() {
    MaterialTheme(
        colorScheme = lightColorScheme()
    ) {
        FormRegister()
    }
}
