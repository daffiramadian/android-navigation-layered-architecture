# NavigationLab - Navigation Compose, Layered Architecture, dan REST API

Repository ini berisi proyek praktikum Pemrograman Aplikasi Perangkat Bergerak (CIF62041), Fakultas Ilmu Komputer, Universitas Brawijaya.

NavigationLab dikembangkan secara bertahap melalui Modul Praktikum 05, 06, dan 07. Modul 05 membangun navigasi multi-halaman dengan Navigation Compose. Modul 06 merapikan struktur aplikasi menggunakan Layered Architecture dan MVVM. Pada Modul 07, sumber data lokal diganti dengan REST API DummyJSON menggunakan Retrofit dan Gson.

## Modul Praktikum

### Modul 05 - Navigation Compose

Modul ini membangun alur navigasi utama aplikasi menggunakan Navigation Compose. Aplikasi memiliki empat destination:

1. Home Screen sebagai start destination dan halaman daftar mahasiswa.
2. Detail Screen untuk menampilkan detail mahasiswa berdasarkan ID.
3. Profile Screen untuk menampilkan informasi profil pengguna.
4. About Screen untuk menampilkan informasi aplikasi.

Navigasi menggunakan NavController dan route terpusat. Tombol Back memanfaatkan `popBackStack()` agar pengguna kembali ke halaman sebelumnya.

### Modul 06 - Layered Architecture dan MVVM

Pada Modul 06, kode dipisahkan menjadi beberapa layer agar UI tidak bergantung langsung pada sumber data.

```text
UI Layer
    ↓ user event
ViewModel
    ↓ request data
Repository
    ↓
StudentLocalDataSource (in-memory)
```

Tanggung jawab tiap layer:

- UI menerima event pengguna dan merender UI state.
- ViewModel mengelola `HomeUiState` dan `DetailUiState`.
- Repository menjadi satu-satunya pintu akses data bagi ViewModel.
- Data source menyediakan data mahasiswa dari daftar in-memory.

Model `Student` dibuat sebagai data class Kotlin yang tidak bergantung pada UI. `StudentRepository` menyediakan operasi untuk mengambil seluruh mahasiswa dan mengambil mahasiswa berdasarkan ID.

### Modul 07 - REST API dan Retrofit

Modul 07 mengganti data in-memory dengan data eksternal dari DummyJSON. Retrofit digunakan sebagai HTTP client, Gson sebagai converter JSON, dan `Call.enqueue` sebagai mekanisme callback asynchronous.

Alur data setelah perubahan:

```text
HomeScreen / DetailScreen
          ↓ user event
HomeViewModel / DetailViewModel
          ↓ UI state dan request data
StudentRepository
          ↓
StudentRemoteDataSource
          ↓
StudentApiService / RetrofitClient
          ↓
DummyJSON REST API
```

UI tetap hanya merender state dan mengirim event. Retrofit, API service, dan remote data source tidak dipanggil langsung dari composable.

## Fitur Aplikasi

- Home Screen menampilkan daftar profil mahasiswa dari REST API.
- Detail Screen menampilkan ID, nama, email, dan universitas mahasiswa.
- Profile Screen menampilkan informasi profil pengguna.
- About Screen menampilkan informasi aplikasi.
- Loading indicator ditampilkan saat request sedang berjalan.
- Error state menampilkan pesan kesalahan dan tombol `Coba Lagi`.
- Navigation Home → Detail → Back tetap dipertahankan.
- Pencarian profil tersedia melalui endpoint `users/search`.

## Struktur Data Layer

```text
data/
├── model/
│   └── Student.kt
├── repository/
│   ├── StudentRepository.kt
│   └── DefaultStudentRepository.kt
└── remote/
    ├── StudentApiService.kt
    ├── RetrofitClient.kt
    ├── StudentRemoteDataSource.kt
    └── dto/
        ├── UserDto.kt
        └── UserResponse.kt
```

`UserDto` dan `UserResponse` mengikuti struktur JSON dari DummyJSON. Mapper `toStudent()` mengubah data eksternal menjadi model `Student` yang digunakan aplikasi. Dengan cara ini, UI tidak bergantung pada bentuk response API.

## Struktur UI dan State

```text
ui/
├── home/
│   ├── HomeUiState.kt
│   └── HomeViewModel.kt
├── detail/
│   ├── DetailUiState.kt
│   └── DetailViewModel.kt
└── screen/
    ├── HomeScreen.kt
    ├── DetailScreen.kt
    ├── ProfileScreen.kt
    └── AboutScreen.kt
```

`HomeUiState` menyimpan daftar mahasiswa, status loading, pesan error, dan query pencarian. `DetailUiState` menyimpan data mahasiswa yang dipilih, status loading, dan pesan error.

## REST API

Base URL yang digunakan:

```text
https://dummyjson.com/
```

Endpoint utama:

| Kebutuhan | Endpoint |
| --- | --- |
| Daftar mahasiswa | `GET /users?limit=10` |
| Detail mahasiswa | `GET /users/{id}` |
| Pencarian profil | `GET /users/search?q={query}` |

Permission jaringan yang diperlukan:

```xml
<uses-permission android:name="android.permission.INTERNET" />
```

## Pencarian Profil

Fitur pencarian ditambahkan pada `StudentApiService` melalui fungsi `searchUsers(@Query("q") query: String)`. Request diteruskan ke `StudentRemoteDataSource`, `StudentRepository`, dan `HomeViewModel`.

`HomeScreen` menyediakan `TextField` dan tombol `Cari`. Request hanya dijalankan setelah tombol ditekan. Jika query kosong, aplikasi memuat ulang daftar awal menggunakan `loadStudents()`. Hasil pencarian tetap dapat dipilih untuk membuka Detail Screen berdasarkan ID mahasiswa.

## Pengelolaan Loading dan Error

Request network dapat memerlukan waktu atau gagal karena masalah koneksi. Karena itu, ViewModel memperbarui state dalam tiga kondisi utama:

1. Loading: UI menampilkan `CircularProgressIndicator`.
2. Success: UI menampilkan daftar atau detail mahasiswa.
3. Error: UI menampilkan pesan kesalahan dan tombol `Coba Lagi`.

Kegagalan request ditangani oleh `StudentRemoteDataSource` dan diteruskan sebagai `Result` melalui repository. Dengan pola ini, UI tidak perlu mengetahui detail Retrofit atau HTTP client.

## Pengujian

Pengujian yang dilakukan meliputi:

- Gradle Sync setelah Retrofit dan converter Gson ditambahkan.
- Permission `INTERNET` pada `AndroidManifest.xml`.
- Response JSON dari endpoint `/users`.
- Home Screen menampilkan minimal 10 profil dari API.
- Detail Screen menampilkan nama, email, dan universitas.
- Loading indicator saat request berjalan.
- Error state ketika koneksi internet dimatikan.
- Tombol `Coba Lagi` untuk mengulangi request.
- Pencarian profil melalui endpoint `users/search`.
- Navigasi Home → Detail → Back.
- Navigasi ke Profile dan About.
- UI tidak mengakses `StudentApiService` atau `RetrofitClient` secara langsung.

## Lingkungan Pengembangan

- IDE: Android Studio
- Bahasa: Kotlin
- UI Toolkit: Jetpack Compose
- Navigation: Navigation Compose
- Arsitektur: Layered Architecture / MVVM
- HTTP Client: Retrofit
- JSON Converter: Gson
- API: DummyJSON
- Target SDK: Android 14 / API Level 35+