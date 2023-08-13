// MyAidlInterface.aidl
package com.einfochips.aidlserver;

import com.einfochips.aidlserver.Student;
import com.einfochips.aidlserver.Result;
import com.einfochips.aidlserver.ResultKt;
import com.einfochips.aidlserver.ArrayListOfStudents;
import com.einfochips.aidlserver.StudentEnum;
import com.einfochips.aidlserver.StudentEnumJava;
// Declare any non-default types here with import statements

interface MyAidlInterface {
    int getRandomNumber();
    String getStudentDetails(in Student student);
    Student createStudent();
    Result getResult(in Student student);
    ResultKt getResultKt(in Student student);
    Student createStudentFromBundle(in Bundle bundle);
    Bundle receiveStudentFromBundle();
    String sendStudents(in ArrayListOfStudents arrayListOfStudents);
    int sendStudentEnumKt(in StudentEnum studentEnum);
    int sendStudentEnum(in StudentEnumJava studentEnum);
    int add(int a, int b);
    int substract(int a, int b);
    int sumOfArray(in int[] intArray);
    String concateString(in String[] strArray);
}