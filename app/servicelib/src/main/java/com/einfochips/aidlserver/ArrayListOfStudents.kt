package com.einfochips.aidlserver

import android.os.Parcel
import android.os.Parcelable

class ArrayListOfStudents : Parcelable{
    var studentList = ArrayList<Student>()
    var allNameString = StringBuilder()

    constructor(parcel: Parcel) {
        studentList = arrayListOf<Student>().also { parcel.readTypedList(it, Student.CREATOR) }
        studentList.forEach {
            allNameString.append(it.name).append('\n')
        }
    }

    constructor(studentList: ArrayList<Student>) {
        this.studentList = studentList
    }

    override fun describeContents(): Int = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeTypedList(studentList)
    }

    companion object CREATOR : Parcelable.Creator<ArrayListOfStudents> {
        override fun createFromParcel(parcel: Parcel): ArrayListOfStudents {
            return ArrayListOfStudents(parcel)
        }

        override fun newArray(size: Int): Array<ArrayListOfStudents?> {
            return arrayOfNulls(size)
        }
    }
}