package com.example.proyectobussinesone



import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.Surface
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.ui.screens.PantallaIniciarSesion
import com.example.proyectobussinesone.ui.screens.PantallaDetalle




import androidx.compose.runtime.Composable
import androidx.compose.material.MaterialTheme
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.fillMaxSize
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.proyectobussinesone.ui.screens.PantallaCorreoDeRecuperacion
import com.example.proyectobussinesone.ui.screens.PantallaRegistro
import com.example.proyectobussinesone.ui.screens.PreviewPantallaCorreoDeRecuperacion
import com.example.proyectobussinesone.ui.theme.ProyectoBussinesOneTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProyectoBussinesOneTheme() {
                // Paso 3: Crea el NavController
                val navController = rememberNavController()
                Surface(modifier = Modifier.fillMaxSize()) {
                    // Paso 4: Configura el NavHost con rutas
                    NavHost(
                        navController = navController,
                        startDestination = "pantalla_iniciar_sesion"
                    ) {
                        composable("pantalla_iniciar_sesion") {
                           PantallaIniciarSesion(
                                navController
                            )
                        }
                        composable("landing_page") {
                            LandingPage(
                                navController
                            )
                        }
                        composable("pantalla_detalle") {
                            PantallaDetalle(navController)
                        }
                        composable("pantalla_registro") {
                            PantallaRegistro(navController)
                        }
                        composable("pantalla_recuperacion_de_correo") {
                            PantallaCorreoDeRecuperacion(navController)
                        }

                    }
                }
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun PreviewPantallaPrincipal() {
    ProyectoBussinesOneTheme {
       PantallaIniciarSesion(navController = rememberNavController())
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewPantallaDetalle() {
    ProyectoBussinesOneTheme {
       PantallaDetalle(navController = rememberNavController())
    }
}
/*
* 1. Gestión de Inventario (control de stock)
Control de stock en tiempo real: seguimiento automático de entradas y salidas, con alertas de mínimos y máximos
wautechnologies.com
.
Trazabilidad por lotes/series: asigna números de lote o serie a productos (crucial para alimentos perecederos)
wautechnologies.com
.
Gestión de almacenes: optimiza la ubicación y movimientos de mercancía dentro del local o almacén
wautechnologies.com
.
Reabastecimiento automático: crea reglas de reposición automática según demanda y niveles mínimos
wautechnologies.com
.
Un módulo de inventarios bien implementado evita desabastecimientos y excesos de stock
precognis.com
. Permite saber en todo momento qué productos hay disponibles y cuáles están por agotarse, lo cual mejora la planificación de compras y ventas. Por ejemplo, al vender un artículo en el TPV el sistema descuenta automáticamente esa unidad del stock, actualizando el inventario en línea
contabilium.com
. Esto libera al personal de llevar inventarios manuales, reduciendo errores y sobrecostes
contabilium.com
. Entre las buenas prácticas destaca usar códigos de barras o escáneres vinculados al ERP para identificar productos rápidamente
distritok.com
, aplicar el método FIFO (primero en entrar, primero en salir) en productos perecederos, y fijar niveles mínimos de reposición. Todo ello ayuda a mantener el stock equilibrado, mejorar la rotación de productos y minimizar pérdidas (por caducidad o exceso de inventario)
precognis.com
.
2. Gestión de Ventas y TPV
Ciclo completo de ventas: gestión de clientes (historial, condiciones), presupuestos, pedidos/órdenes de venta y facturación integrada
wautechnologies.com
.
Terminal Punto de Venta (TPV): emisión rápida de tickets, tickets regalo y vales de compra, cobro con múltiples métodos (efectivo, tarjeta, móvil), arqueo de caja diario
distritok.com
.
Promociones y fidelización: aplicación de descuentos, tarifas especiales y vales personalizados por cliente o campaña
distritok.com
.
Integración con periféricos: conexión a dispositivos (impresora de tickets, lector de códigos de barras, báscula, firma digital, etc.) para agilizar la venta en el mostrador
distritok.com
.
Este módulo agiliza enormemente la atención al cliente y el proceso de cobro. Al integrar ventas con el inventario, cada venta se registra automáticamente, evitando dobles anotaciones y garantizando que el stock se actualice en tiempo real
holded.com
contabilium.com
. Por ejemplo, al cobrar un producto con TPV táctil conectado por código de barras, el sistema genera la factura correspondiente y ajusta el inventario sin intervención manual. Además, permite emitir facturas electrónicas para cumplir con normativas fiscales
distritok.com
 y enviar recibos digitales al cliente. Todo esto facilita cierres de caja rápidos y precisos. En cuanto a beneficios, un buen módulo de ventas aporta información valiosa sobre patrones de compra (mediante informes y estadísticas) y mejora la experiencia del cliente al simplificar pagos y brindar opciones de fidelización
distritok.com
distritok.com
. Como ejemplo de herramienta, plataformas ERP comerciales como Holded ofrecen módulos integrados de ventas, inventario y finanzas en una misma interfaz
holded.com
.
3. Gestión Financiera y Contabilidad
Contabilidad general: registro automatizado de todas las transacciones (ventas, compras, gastos), elaboración de balances y estados de resultados
wautechnologies.com
.
Cuentas por pagar y por cobrar: seguimiento de facturas de proveedores (cuentas por pagar) y clientes (cuentas por cobrar) para controlar el flujo de efectivo
wautechnologies.com
.
Tesorería y presupuestos: conciliación bancaria, control de caja, planificación de liquidez y elaboración de presupuestos financieros
wautechnologies.com
.
Informes financieros: generación de reportes de pérdidas y ganancias, flujo de caja y análisis de rentabilidad con un clic.
Este módulo es el cerebro contable del negocio. Reemplaza hojas de cálculo con procesos automáticos, garantizando precisión en la facturación y el pago de impuestos. Por ejemplo, al cobrar en el TPV, el asiento contable se genera automáticamente y se registra el IVA conforme a ley
distritok.com
. Esto mejora la transparencia financiera y reduce riesgos de errores o sanciones fiscales. Oracle destaca que un ERP financiero brinda “mayor transparencia, toma de decisiones más informada y menor riesgo de problemas financieros”
oracle.com
. Para el propietario, tener finanzas al día significa poder evaluar la rentabilidad del negocio en tiempo real, conocer con exactitud los márgenes y anticipar problemas de caja. Entre buenas prácticas se recomienda integrar la facturación electrónica (para ahorrar papel y agilizar trámites) y revisar periódicamente los informes de tesorería. En suma, este módulo permite controlar el presupuesto del negocio, optimizar recursos y asegurar el cumplimiento contable sin depender de anotaciones manuales
oracle.com
distritok.com
.
4. Gestión de Empleados (Recursos Humanos)
Nóminas y sueldos: cálculo automático de salarios, deducciones e impuestos, con emisión de recibos de nómina electrónicos
wautechnologies.com
.
Control de asistencia: registro de horas trabajadas, entradas/salidas, gestión de turnos y horarios, cálculo de horas extra
wautechnologies.com
.
Vacaciones y ausencias: seguimiento de días libres, permisos y bajas, asegurando el cumplimiento de convenios laborales
wautechnologies.com
.
Cumplimiento legal: generación de reportes y documentos requeridos por la Seguridad Social y Hacienda (contratos, retenciones, registros horarios)*/
