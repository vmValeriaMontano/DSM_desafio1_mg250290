# Desafío Práctico 1 - Desarrollo de Software para Móvil (DSM)
**Universidad Don Bosco**  
**Valeria del Rosario Montano Gonzalez MG250290**  

---

## 📋 Descripción del Proyecto
Aalicación móvil desarrollada en **Kotlin** para Android tiene un menú principal interactivo que conecta a tres módulos independientes para resolver problemáticas de promedios, salrios y claculadora.

---

## 🚀 Proyecto

### 1. 🎓 Ejercicio 1: Promedio del Estudiante
Permite ingresar el nombre del estudiante y 5 notas. Realiza el cálculo del promedio final ponderado y determina el estado de aprobación.
- **Validaciones**: Comprueba que los campos no estén vacíos y que las notas se encuentren estrictamente entre 0.0 y 10.0.
- **Formato**: Muestra los resultados con formato numérico de dos decimales (`DecimalFormat`).
- **Notificaciones**: Genera una notificación del sistema mediante `NotificationManager` e `NotificationCompat` informando el promedio final y el estado (Aprobado / Reprobado).
- **Manejo de vacios**: tambien se agrega vibración

### 2. 💰 Ejercicio 2: Descuentos al Salario
Calcula el salario neto de un empleado a partir de su salario bruto y las retenciones de ley de El Salvador.
- **Descuentos evaluados**:
  - **ISSS**: 3% (con tope correspondiente).
  - **AFP**: 7.25%.
  - **Renta (ISR)**: Calculado dinámicamente mediante una función por tramos sobre el salario gravable.
- **Vibración y Validaciones**: Ante salarios negativos o entradas inválidas, activa el motor de vibración del dispositivo (`VIBRATE` permission) y resalta el campo con `setError()`.
- **Diseño Visual**: Diferenciación clara de colores (`salario_bruto`, `descuento`, `salario_neto`) mediante recursos en `colors.xml`.

### 3. 🧮 Ejercicio 3: Calculadora Básica
Ofrece una interfaz ágil para la realización de operaciones aritméticas y avanzadas:
- **Operaciones**: Suma, Resta, Multiplicación, División, Potenciación ($x^y$) y Raíz Cuadrada ($\sqrt{x}$).
- **Validaciones**: Prevención de división por cero y raíces cuadradas de números negativos.
- **Persistencia**: Almacena el historial de operaciones realizadas en el almacenamiento interno del dispositivo utilizando `openFileOutput`.
- **Manejo de vacios**: tambien se agrega vibración en caso de necesitar dos numeros

---

## 🛠️ Tecnologías y Requisitos
- **Lenguaje**: Kotlin
- **Entorno de Desarrollo**: Android Studio (Ladybug / Jellyfish o superior)
- **Min SDK**: API 24 (Android 7.0)
- **Target SDK**: API 34 / 35
- **Layouts**: ConstraintLayout, LinearLayout, ScrollView

---

## 📱 Permisos Requeridos (`AndroidManifest.xml`)
- `android.permission.VIBRATE`: Requerido para errores o vacios.
- `android.permission.POST_NOTIFICATIONS`: Requerido para emitir notificaciones locales en Android.
