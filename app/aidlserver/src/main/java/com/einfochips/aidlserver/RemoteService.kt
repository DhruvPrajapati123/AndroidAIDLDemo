package com.einfochips.aidlserver

import android.app.Service
import android.content.Intent
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import kotlin.random.Random

class RemoteService: Service() {

    private val binder = object : MyAidlInterface.Stub() {
        override fun getRandomNumber(): Int = Random.nextInt(100)
        override fun getStudentDetails(student: Student?): String {
            return "Rollno: ${student?.rollNo} Name: ${student?.name}"
        }
        override fun createStudent(): Student = Student(2, "Maddy", 45, 67, 88)
        override fun getResult(student: Student?): Result = Result(student)
        override fun getResultKt(student: Student?): ResultKt = ResultKt(student!!)
        override fun createStudentFromBundle(bundle: Bundle?): Student? {
            bundle?.classLoader = classLoader
            return bundle?.getParcelable<Student>("student")
        }

        override fun receiveStudentFromBundle(): Bundle {
            val student = Student(45, "Aakash", 67, 89, 99)
            val bundle = Bundle()
            bundle.putParcelable("student", student)
            return bundle
        }

        override fun sendStudents(arrayListOfStudents: ArrayListOfStudents?): String = arrayListOfStudents?.allNameString.toString()
        override fun sendStudentEnumKt(studentEnum: StudentEnum?): Int = studentEnum?.getRollNo()!!
        override fun sendStudentEnum(studentEnum: StudentEnumJava?): Int = studentEnum?.rollNo!!

        override fun add(a: Int, b: Int) = a + b

        override fun substract(a: Int, b: Int) = a - b
        override fun sumOfArray(intArray: IntArray?): Int = intArray?.sum()!!
        override fun concateString(strArray: Array<out String>?): String {
            var str = ""
            strArray?.forEach { str += it }
            return str
        }
    }

    override fun onBind(intent: Intent?): IBinder = binder
}