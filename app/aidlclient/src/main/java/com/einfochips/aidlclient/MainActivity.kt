package com.einfochips.aidlclient

import android.annotation.SuppressLint
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.einfochips.aidlserver.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"
    private lateinit var textView: TextView
    private var myRemoteService: MyAidlInterface? = null
    private var isBound = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            Log.d(TAG, "onServiceConnected: caled")
            isBound = true
            myRemoteService = MyAidlInterface.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            Log.d(TAG, "onServiceDisconnected: caled")
            isBound = false
            myRemoteService = null
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        textView = findViewById(R.id.textView)

        val bindBtn = findViewById<Button>(R.id.bindBtn)
        val unbindBtn = findViewById<Button>(R.id.unbindBtn)
        val randomBtn = findViewById<Button>(R.id.randomBtn)
        val getStudentDetailsBtn = findViewById<Button>(R.id.getStudentDetailsBtn)
        val createStudentBtn = findViewById<Button>(R.id.createStudentBtn)
        val getResultBtn = findViewById<Button>(R.id.getResultBtn)
        val getBundleBtn = findViewById<Button>(R.id.getBundleBtn)
        val sendStudentsListBtn = findViewById<Button>(R.id.sendStudentsListBtn)
        val receiveStudentFromBundleBtn = findViewById<Button>(R.id.receiveStudentFromBundle)
        val sendStudentEnumBtn = findViewById<Button>(R.id.sendStudentEnum)
        val sendExpressionBtn = findViewById<Button>(R.id.sendExpression)
        val sendIntArrayBtn = findViewById<Button>(R.id.sendIntArray)
        val sendStringArrayBtn = findViewById<Button>(R.id.sendStringArray)

        bindBtn.setOnClickListener(bindBtnOnClickListener)
        unbindBtn.setOnClickListener(unbindBtnOnClickListener)
        randomBtn.setOnClickListener(randomOnClickListener)
        getStudentDetailsBtn.setOnClickListener(getStudentDetailsOnClickListener)
        createStudentBtn.setOnClickListener(createStudentOnClickListener)
        getResultBtn.setOnClickListener(getResultOnClickListener)
        getBundleBtn.setOnClickListener(getBundleOnClickListener)
        sendStudentsListBtn.setOnClickListener(sendStudentsOnClickListener)
        receiveStudentFromBundleBtn.setOnClickListener(receiveStudentFromBundleOnClickListener)
        sendStudentEnumBtn.setOnClickListener(sendStudentEnumOnClickListener)
        sendExpressionBtn.setOnClickListener(sendExpressionOnClickListener)
        sendIntArrayBtn.setOnClickListener(sendIntArrayOnClickListener)
        sendStringArrayBtn.setOnClickListener(sendStringArrayOnClickListener)
    }

    private fun generateRandomString(length: Int): String {
        val charset = "abcdefghijklmnopqrstuvwxyz"
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }

    private val bindBtnOnClickListener = View.OnClickListener {
        if (!isBound) {
            val serviceIntent = Intent("android.intent.action.RemoteService")
            serviceIntent.setPackage("com.einfochips.aidlserver")
            bindService(serviceIntent, serviceConnection, BIND_AUTO_CREATE)
        }
    }

    private val unbindBtnOnClickListener = View.OnClickListener {
        if (isBound) {
            unbindService(serviceConnection)
            isBound = false
            myRemoteService = null
        }
    }

    private val randomOnClickListener = View.OnClickListener {
        textView.text = "${myRemoteService?.randomNumber}"
    }

    private val getStudentDetailsOnClickListener = View.OnClickListener {
        textView.text = "${myRemoteService?.getStudentDetails(Student(1, "Dhruv", 97, 85, 94))}"
    }

    private val createStudentOnClickListener = View.OnClickListener {
        textView.text = myRemoteService?.createStudent().toString()
    }

    private val getResultOnClickListener = View.OnClickListener {
        textView.text = myRemoteService?.getResult(Student(21, "Dhruv", 56, 78, 88)).toString()
        textView.text = myRemoteService?.getResultKt(Student(22, "Maddy", 56, 78, 88)).toString()
    }

    private val getBundleOnClickListener = View.OnClickListener {
        val bundle = Bundle()
        bundle.putParcelable("student", Student(15, "Het", 34, 67, 90))
        textView.text = myRemoteService?.createStudentFromBundle(bundle).toString()
    }

    private val sendStudentsOnClickListener = View.OnClickListener {
        val studentList = ArrayList<Student>()
        studentList.add(Student(10, "Dhruv", 56, 78, 99))
        studentList.add(Student(20, "Het", 56, 78, 99))
        studentList.add(Student(30, "Maddy", 56, 78, 99))
        studentList.add(Student(40, "Om", 56, 78, 99))
        studentList.add(Student(50, "Aakash", 56, 78, 99))
        textView.text = myRemoteService?.sendStudents(ArrayListOfStudents(studentList))
    }

    private val receiveStudentFromBundleOnClickListener = View.OnClickListener {
        val bundle = myRemoteService?.receiveStudentFromBundle()
        Log.d(TAG, bundle.toString())
        bundle?.classLoader = classLoader
        val student = bundle?.getParcelable<Student>("student");
        textView.text = student.toString()
    }

    @SuppressLint("SetTextI18n")
    private val sendStudentEnumOnClickListener = View.OnClickListener {
        textView.text = "Java Enum ${myRemoteService?.sendStudentEnum(StudentEnumJava.DHRUV)} AND Kotlin Enum ${myRemoteService?.sendStudentEnumKt(StudentEnum.MADDY)}"
    }

    @SuppressLint("SetTextI18n")
    private val sendExpressionOnClickListener = View.OnClickListener {
        val a = Random.nextInt(100)
        val b = Random.nextInt(100)
        val c = Random.nextInt(100)

        textView.text = "$a + $b - $c = ${myRemoteService?.add(a, b)?.let { it1 -> myRemoteService?.substract(it1, c) }}"
    }

    @SuppressLint("SetTextI18n")
    private val sendIntArrayOnClickListener = View.OnClickListener {
        val intArray = IntArray(10)
        for (i in intArray.indices) {
            intArray[i] = Random.nextInt(100)
        }
        textView.text = "Sum of Array = ${myRemoteService?.sumOfArray(intArray)}"
    }

    private val sendStringArrayOnClickListener = View.OnClickListener {
        textView.text = "${myRemoteService?.concateString(arrayOf(generateRandomString(5), " ", generateRandomString(5)))}"
    }
}