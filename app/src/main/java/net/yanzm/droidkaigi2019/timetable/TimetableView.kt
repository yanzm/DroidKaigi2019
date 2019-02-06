package net.yanzm.droidkaigi2019.timetable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.forEach
import net.yanzm.droidkaigi2019.R
import net.yanzm.droidkaigi2019.domain.Session

class TimetableView : ViewGroup {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private val density = context.resources.displayMetrics.density
    private val oneHourHeight = 120 * density
    private val timeTextSize = 10 * density
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.LTGRAY
        strokeWidth = density
        textSize = timeTextSize
    }

    init {
        setBackgroundColor(Color.WHITE)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        val leftOffset = timeTextSize * 3
        val width = (r - l - leftOffset) / 9

        forEach {
            val child = it as TimetableItemView
            val timeAndDate = child.session.timeAndDate
            val startTime = timeAndDate.startTime
            val offset = startTime.hour + startTime.minute / 60f - 10

            val left = l + leftOffset + width * child.session.room.ordinal
            val right = left + width - density

            val top = oneHourHeight * offset
            val bottom = top + oneHourHeight * timeAndDate.durationMinutes / 60f

            child.layout(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(
            widthMeasureSpec,
            MeasureSpec.makeMeasureSpec((oneHourHeight * 10).toInt(), MeasureSpec.EXACTLY)
        )
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        for (i in 0 until 10) {
            val y = oneHourHeight * i
            if (i > 0) {
                paint.style = Paint.Style.STROKE
                canvas.drawLine(0f, y, width.toFloat(), y, paint)
            }
            paint.style = Paint.Style.FILL
            canvas.drawText(
                (10 + i).toString(),
                timeTextSize * 0.5f,
                y + timeTextSize * 1.5f,
                paint
            )
        }
    }
}

class TimetableItemView(
    context: Context,
    val session: Session
) : AppCompatTextView(context) {

    init {
        setPadding(4, 4, 4, 4)
        textSize = 10f
        setTextColor(Color.BLACK)
        text = session.title
        setBackgroundResource(R.drawable.time_table_item)
    }
}
