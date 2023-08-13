package com.einfochips.aidlserver

import android.os.Parcel
import android.os.Parcelable

enum class StudentEnum(private var rollNo: Int) : Parcelable {

    DHRUV(1),
    OM(2),
    MADDY(3),
    HET(4),
    UNKNOWN(0);

    fun getRollNo() = rollNo

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(rollNo)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<StudentEnum> {
        override fun createFromParcel(parcel: Parcel): StudentEnum {
            return StudentEnum.getStudentEnumByRollNo(parcel.readInt())
        }

        override fun newArray(size: Int): Array<StudentEnum?> {
            return arrayOfNulls(size)
        }

        private fun getStudentEnumByRollNo(rollNo: Int): StudentEnum =
            when (rollNo) {
                1 -> StudentEnum.DHRUV
                2 -> StudentEnum.OM
                3 -> StudentEnum.MADDY
                4 -> StudentEnum.HET
                else -> StudentEnum.UNKNOWN
            }
    }
}