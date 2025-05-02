package com.example.proyectobussinesone.navigation



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.proyectobussinesone.R
import com.example.proyectobussinesone.ui.theme.AzulSecundario
import com.example.proyectobussinesone.ui.theme.FondoClaro
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material.icons.Icons
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
/*
*  Añadir :
*   Formacion academica y certificaciones
*   Datos personales como.
*       DNI
*       Fecha de nacimiento
*       Numero de seguridad social
*       Informacion bancaria
*       Curriculum vitae
*
*
* */
@Composable
fun AcademicSection() {
    SectionTitle("Formación Académica y Certificaciones")
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        Text("• Grado en Ingeniería Informática - Universidad de Valencia (2015-2019)")
        Text("• Máster en Gestión de Proyectos - Universidad Politécnica de Valencia (2020-2021)")
        Text("• Certificación PMP - Project Management Institute (2022)")
        Text("• Curso de Kotlin Avanzado - Udemy (2023)")
    }
    Spacer(modifier = Modifier.height(24.dp))
}
@Composable
fun PersonalDataSection() {
    SectionTitle("Datos Personales")
    Column(modifier = Modifier.padding(horizontal = 16.dp)) {
        InfoRow(icon = Icons.Default.Badge, label = "DNI", value = "12345678A")
        InfoRow(icon = Icons.Default.Cake, label = "Fecha de Nacimiento", value = "15 de marzo de 1990")
        InfoRow(icon = Icons.Default.Security, label = "Nº Seguridad Social", value = "12/34567890/12")
        InfoRow(icon = Icons.Default.AccountBalance, label = "IBAN", value = "ES12 3456 7890 1234 5678 9012")
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = { /* Lógica para abrir el currículum vitae */ },
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.Description, contentDescription = "Currículum Vitae")
            Spacer(modifier = Modifier.width(8.dp))
            Text("Ver Currículum Vitae")
        }
    }
    Spacer(modifier = Modifier.height(24.dp))
}

@Composable
fun ProfileScreen() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // --- Header de perfil ---
        item {
            Spacer(modifier = Modifier.height(24.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.logo),
                    contentDescription = "Foto de perfil",
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text("Juan Pérez", style = MaterialTheme.typography.headlineSmall)
                Spacer(modifier = Modifier.height(4.dp))
                Text("Rol", style = MaterialTheme.typography.headlineSmall, color = Color.Gray)
                Spacer(modifier = Modifier.height(4.dp))
                Text("juan.perez@example.com", color = Color.LightGray)
                Spacer(modifier = Modifier.height(24.dp))
            }
        }

        // --- Sección Contacto ---
        item {
            SectionTitle("Contacto")
            InfoRow(icon = Icons.Default.Phone, label = "Teléfono", value = "+34 600 123 456")
            InfoRow(icon = Icons.Default.Email, label = "Email", value = "juan.perez@example.com")
            InfoRow(icon = Icons.Default.Home, label = "Dirección", value = "Calle Falsa 123, Madrid")
            Spacer(modifier = Modifier.height(24.dp))
        }

        // --- Sección Sobre mí ---
        item {
            SectionTitle("Sobre mí")
            Text(
                text = "Desarrollador Android con 5 años de experiencia en Jetpack Compose y arquitectura MVVM. "
                        + "Apasionado por la usabilidad y el buen diseño de interfaces, además de mejorar continuamente "
                        + "mis habilidades en Kotlin y modernas prácticas de testing.",
                style = MaterialTheme.typography.bodyMedium,
                modifier = Modifier.padding(horizontal = 16.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
        }
    //secciones extra mias
        item { AcademicSection() }
        item { PersonalDataSection() }


    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f))
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
private fun InfoRow(icon: ImageVector, label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(icon, contentDescription = null, modifier = Modifier.size(24.dp))
        Spacer(modifier = Modifier.width(12.dp))
        Text("$label:", style = MaterialTheme.typography.bodySmall, color = Color.Gray)
        Spacer(modifier = Modifier.width(8.dp))
        Text(value, style = MaterialTheme.typography.bodyMedium)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProfileScreen(){
    ProyectoBussinesOneTheme {
        ProfileScreen()
    }
}