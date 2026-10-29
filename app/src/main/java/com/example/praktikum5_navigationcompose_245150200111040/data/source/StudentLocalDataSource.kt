package com.example.praktikum5_navigationcompose_245150200111040.data.source

import com.example.praktikum5_navigationcompose_245150200111040.data.model.Student

class StudentLocalDataSource {
    private val students = listOf(
        Student(2026, "Alya Putri", "Teknik Informatika", 2026),
        Student(2027, "Bima Pratama", "Teknik Informatika", 2026),
        Student(2028, "Citra Lestari", "Teknik Informatika", 2026),
        Student(2029, "Damar Wicaksono", "Teknik Informatika", 2027),
        Student(2030, "Elsa Maharani", "Sistem Informasi", 2027),
        Student(2031, "Farhan Akbar", "Teknik Komputer", 2027)
    )

    fun getStudents(): List<Student> = students

    fun getStudentById(id: Int): Student? =
        students.find { it.id == id }
}
