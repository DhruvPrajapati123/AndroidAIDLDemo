package com.einfochips.aidlserver;

import android.os.Parcel;
import android.os.Parcelable;

public enum StudentEnumJava implements Parcelable {

    DHRUV(1),
    OM(2),
    MADDY(3),
    HET(4),
    UNKNOWN(0);

    private final int rollNo;

    StudentEnumJava(int rollNo) {
        this.rollNo = rollNo;
    }

    public int getRollNo() { return rollNo; }

    public static StudentEnumJava getStudentEnumByRollNo(int rollNo) {
        StudentEnumJava studentEnumJava;
        switch (rollNo) {
            case 1 :
                studentEnumJava = DHRUV;
                break;
            case 2 :
                studentEnumJava = OM;
                break;
            case 3 :
                studentEnumJava = MADDY;
                break;
            case 4 :
                studentEnumJava = HET;
                break;
            default:
                studentEnumJava = UNKNOWN;
                break;
        }
        return studentEnumJava;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<StudentEnumJava> CREATOR = new Creator<StudentEnumJava>() {
        @Override
        public StudentEnumJava createFromParcel(Parcel in) {
            return StudentEnumJava.getStudentEnumByRollNo(in.readInt());
        }

        @Override
        public StudentEnumJava[] newArray(int size) {
            return new StudentEnumJava[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(rollNo);
    }
}
