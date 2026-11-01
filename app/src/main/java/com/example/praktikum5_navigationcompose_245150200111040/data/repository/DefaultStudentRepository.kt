package com.example.praktikum5_navigationcompose_245150200111040.data.repository

import com.example.praktikum5_navigationcompose_245150200111040.data.model.Student
import com.example.praktikum5_navigationcompose_245150200111040.data.remote.StudentRemoteDataSource
import com.example.praktikum5_navigationcompose_245150200111040.data.remote.dto.toStudent

class DefaultStudentRepository(
    private val remoteDataSource: StudentRemoteDataSource
) : StudentRepository {

    override fun getStudents(
        callback: (Result<List<Student>>) -> Unit
    ) {
        remoteDataSource.getStudents { result ->
            callback(result.map { users -> users.map { it.toStudent() } })
        }
    }

    override fun getStudent(
        id: Int,
        callback: (Result<Student>) -> Unit
    ) {
        remoteDataSource.getStudent(id) { result ->
            callback(result.map { it.toStudent() })
        }
    }
}
