package com.example.calendar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.haibin.calendarview.Calendar
import com.haibin.calendarview.CalendarView
import com.haibin.calendarview.CalendarView.OnMonthChangeListener
import java.util.*

class MainActivity : AppCompatActivity(), View.OnClickListener, OnMonthChangeListener,CalendarView.OnCalendarSelectListener {
    lateinit var mCalendarView: CalendarView
    lateinit var mTextYear: TextView
    lateinit var mTextMonth: TextView
    val tag = "jow"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()
        mCalendarView = findViewById(R.id.calendarView)
        mTextYear = findViewById(R.id.year_tv)
        mTextMonth = findViewById(R.id.month_bt)
        findViewById<Button>(R.id.pre_year_bt).setOnClickListener(this)
        findViewById<Button>(R.id.pre_month_bt).setOnClickListener(this)
        findViewById<Button>(R.id.next_month_bt).setOnClickListener(this)
        findViewById<Button>(R.id.next_year_bt).setOnClickListener(this)
        mCalendarView.setOnMonthChangeListener(this)
        mCalendarView.setOnCalendarSelectListener(this)

    }

    override fun onResume() {
        super.onResume()
        val map: MutableMap<String, Calendar> = HashMap()
        for (y in 1997..2081) {
            for (m in 1..12) {

                map.put(
                    getSchemeCalendar(y, m, 15, Color.BLACK, "会").toString(),
                    getSchemeCalendar(y, m, 15, Color.BLACK, "会")
                )

                map.put(
                    getSchemeCalendar(y, m, 19, Color.BLACK, "码").toString(),
                    getSchemeCalendar(y, m, 19, Color.BLACK, "码")
                )

                map.put(
                    getSchemeCalendar(y, m, 28, Color.BLACK, "码").toString(),
                    getSchemeCalendar(y, m, 28, Color.BLACK, "码")
                )
            }
        }

        //28560 数据量增长不会影响UI响应速度，请使用这个API替换
        mCalendarView.setSchemeDate(map)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.pre_year_bt -> {
                mCalendarView.scrollToPreYear()
            }
            R.id.pre_month_bt -> {
                mCalendarView.scrollToPre()
            }
            R.id.next_month_bt -> {
                mCalendarView.scrollToNext()
            }
            R.id.next_year_bt -> {
                mCalendarView.scrollToNextYear()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onMonthChange(year: Int, month: Int) {
        Log.e(tag, "切换月份或者年份到：  -- $year  --  $month")
        mTextMonth.text = month.toString()
        mTextYear.text = year.toString()

    }

    override fun onCalendarOutOfRange(calendar: Calendar?) {
    }

    override fun onCalendarSelect(calendar: Calendar?, isClick: Boolean) {
        Log.d(tag,"选中的日期： ${calendar?.year}年 ${calendar?.month}月 ${calendar?.day}日   事件:${calendar?.scheme}")
    }

    fun getSchemeCalendar(
        year: Int,
        month: Int,
        day: Int,
        color: Int,
        text: String
    ): Calendar {
        val calendar = Calendar()
        calendar.year = year
        calendar.month = month
        calendar.day = day
        calendar.schemeColor = color //如果单独标记颜色、则会使用这个颜色
        calendar.scheme = text
        return calendar
    }

}