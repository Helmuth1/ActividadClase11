Prompts usados

1-“Me puedes ayudar con una app Android (Kotlin, Compose) que consuma un API REST en http://10.0.2.2:8080/
 con endpoints de productos (GET/POST/PUT/DELETE y búsqueda por nombre) usando MVVM + Repository + Retrofit?”

2-“Genérame el data class Product con propiedades id, name, price, category, createdAt y un ProductRequest.”

3-“Define una interfaz ProductApi con Retrofit para estos endpoints exactos (lista, get por id, create, update, delete, search con productName).”

4-“Crea un RetrofitInstance con base URL http://10.0.2.2:8080/
 usando GsonConverterFactory y OkHttp logging nivel BODY.”

5-“Crea una clase ProductRepository que use RetrofitInstance.api y exponga funciones listAll, get, create, update, delete, search.”

6-“Crea un ProductViewModel con ProductUiState(loading, items, message) usando MutableStateFlow/StateFlow.”

Explicación MVVM 

MVVM (Model-View-ViewModel):
Se usó para separar la lógica de UI del acceso a datos.

View = ProductsScreen (Compose UI).

ViewModel = ProductViewModel (maneja estado y lógica).

Model = ProductRepository y RetrofitInstance (fuente de datos REST).

Retrofit:
Maneja todas las llamadas HTTP hacia la API REST (ProductApi).
Usa GsonConverterFactory para serializar/parsear JSON y un HttpLoggingInterceptor para depuración.

Repository Pattern:
ProductRepository abstrae Retrofit para que el ViewModel no conozca detalles de red.

StateFlow + Compose:
Permite que la UI reaccione automáticamente a los cambios en los datos (ProductUiState → collectAsStateWithLifecycle).

Errores encontrados

Error: “NetworkOnMainThreadException”
Solución: aseguré que todas las llamadas HTTP se ejecutaran dentro de viewModelScope.launch, que usa un hilo de background (corrutinas).

Error: “Unresolved reference: collectAsStateWithLifecycle”
Solución: agregué la dependencia androidx.lifecycle:lifecycle-runtime-compose en build.gradle.

Error: “NullPointerException en product.id” al actualizar o eliminar
Solución: aseguré que los id fuesen opcionales (Long?) y validé con ?.let { ... } antes de llamar al repositorio.

Problemas de recomposición infinita en Compose
Solución: envolví la carga inicial dentro de LaunchedEffect(Unit) para ejecutarla una sola vez.

Error 404 con el endpoint de búsqueda
Solución: corregí el endpoint en ProductApi para usar @GET("/api/products/search") con @Query("productName").