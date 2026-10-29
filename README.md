# Navigation Compose & Layered Architecture - NavigationLab

Repository ini berisi proyek praktikum **Pemrograman Aplikasi Perangkat Bergerak (CIF62041)** Fakultas Ilmu Komputer, Universitas Brawijaya. Proyek ini mengimplementasikan alur navigasi multi-halaman berbasis **Navigation Compose** (Modul 05) yang telah direfaktor menggunakan **Layered Architecture / MVVM & Separation of Concerns** (Modul 06).

---

## 🚀 Fitur Utama & Halaman Aplikasi

Aplikasi **NavigationLab** terdiri dari 4 halaman (*destinations*) utama:
1. **Home Screen (`startDestination`)**: Halaman utama yang menampilkan daftar mahasiswa yang diambil melalui `HomeViewModel` dan `StudentRepository`. Berisi opsi navigasi ke Detail, Profile, dan About.
2. **Detail Screen**: Halaman detail yang menampilkan data lengkap mahasiswa (ID, Nama, Program Studi, Angkatan) berbasis `DetailUiState` dari `DetailViewModel`.
3. **Profile Screen**: Halaman profil pengguna yang menyajikan informasi mahasiswa.
4. **About Screen**: Halaman informasi aplikasi (*about page*) dengan tampilan UI kustom.

---

## 🏗️ Modul 06 - Arsitektur Aplikasi Android

Aplikasi menerapkan prinsip **Separation of Concerns** dengan membagi kode ke dalam beberapa lapisan (*layers*) independen. Composable UI tidak lagi mengakses data secara langsung.

### 🔄 Alur Data (Data Flow)
Alur data dan state berjalan secara satu arah (*Unidirectional Data Flow*):

```text
[ UI Layer ]  ---> (User Event) --->  [ ViewModel ]
DetailScreen /                        DetailViewModel / HomeViewModel
HomeScreen                                    │
    ▲                                         ▼
    │ (UI State)                      [ Repository ]
    └─────────────────────────────  StudentRepository / DefaultStudentRepository
                                              │
                                              ▼
                                       [ Data Source ]
                                   StudentLocalDataSource (in-memory)
```

1. **UI Layer**: Menerima user event dan merender state.
2. **ViewModel (State Holder)**: Mengelola UI State (`DetailUiState`, `HomeUiState`) dan memanggil fungsi pada Repository.
3. **Repository**: Menjadi satu pintu akses data bagi ViewModel dan mengabstraksi sumber data.
4. **Data Source**: Menangani penyediaan data mentah (pada modul ini menggunakan *in-memory list*).

---

## 📁 Struktur Package & Proyek

```text
com.example.praktikum5_navigationcompose_245150200111040/
├── MainActivity.kt                 # Entry point aplikasi
├── NavigationLabApp.kt             # Setup NavHost, NavController, & DI Manual
├── Routes.kt                       # Definisi rute navigasi
├── data/                           # Data Layer
│   ├── model/
│   │   └── Student.kt              # Data class model objek Student
│   ├── repository/
│   │   └── StudentRepository.kt    # Interface & Implementasi Repository
│   └── source/
│       └── StudentLocalDataSource.kt # Sumber data in-memory
└── ui/                             # UI Layer & State Holders
    ├── detail/
    │   ├── DetailUiState.kt        # State untuk Detail Screen
    │   └── DetailViewModel.kt      # ViewModel pengelola state Detail
    ├── home/
    │   ├── HomeUiState.kt          # State untuk Home Screen
    │   └── HomeViewModel.kt        # ViewModel pengelola daftar mahasiswa
    └── screen/
        ├── HomeScreen.kt           # UI Halaman Utama (Daftar Mahasiswa)
        ├── DetailScreen.kt         # UI Halaman Detail
        ├── ProfileScreen.kt        # UI Halaman Profil
        └── AboutScreen.kt          # UI Halaman About
```

---

## 🧠 Konsep & Komponen Utama

1. **`Student.kt` (Data Model)**: Pure Kotlin data class tanpa ketergantungan pada UI/Android Framework.
2. **`StudentRepository` (Interface Contract)**: Memisahkan dependensi UI dari sumber data mentah, memuat fungsi `getStudentById(id: Int)` dan `getStudents()`.
3. **State Management (`UiState`)**: `DetailUiState` memuat properti `student`, `isLoading`, dan `errorMessage` untuk penanganan kondisi UI yang terisolasi.
4. **Back Stack (`popBackStack`)**: Navigasi menggunakan `navController.popBackStack()` untuk mengembalikan stack ke halaman sebelumnya secara konsisten.

---

## 🛠️ Modul & Lingkungan Pengembangan

* **IDE**: Android Studio
* **Language**: Kotlin
* **UI Toolkit**: Jetpack Compose
* **Architecture Pattern**: Layered Architecture / MVVM
* **Target SDK**: Android 14 / API Level 35+
