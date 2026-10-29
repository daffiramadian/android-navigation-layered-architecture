package com.example.praktikum5_navigationcompose_245150200111040.data.repository

import com.example.praktikum5_navigationcompose_245150200111040.data.model.Student
import com.example.praktikum5_navigationcompose_245150200111040.data.source.StudentLocalDataSource

interface StudentRepository {
    fun getStudents(): List<Student>
    fun getStudentById(id: Int): Student?
}

class DefaultStudentRepository(
    private val dataSource: StudentLocalDataSource
) : StudentRepository {
    override fun getStudents(): List<Student> =
        dataSource.getStudents()

    override fun getStudentById(id: Int): Student? =
        dataSource.getStudentById(id)
}
