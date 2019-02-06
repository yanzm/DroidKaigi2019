package net.yanzm.droidkaigi2019.timetable

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.Gravity
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.view.forEach
import net.yanzm.droidkaigi2019.R
import net.yanzm.droidkaigi2019.domain.Language
import net.yanzm.droidkaigi2019.domain.Lunch
import net.yanzm.droidkaigi2019.domain.Party
import net.yanzm.droidkaigi2019.domain.PublicSession
import net.yanzm.droidkaigi2019.domain.Session
import net.yanzm.droidkaigi2019.domain.TimetableItem

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
            val timeAndDate = child.item.timeAndDate
            val startTime = timeAndDate.startTime
            val offset = startTime.hour + startTime.minute / 60f - 10

            val index = when (child.item) {
                is Session -> child.item.room.ordinal
                is Party -> child.item.room.ordinal
                is Lunch -> 0
            }

            val left = l + leftOffset + width * index
            val right = when (child.item) {
                is Lunch -> r.toFloat()
                else -> left + width - density
            }

            val top = oneHourHeight * offset
            val bottom = top + oneHourHeight * timeAndDate.durationMinutes / 60f

            child.layout(left.toInt(), top.toInt(), right.toInt(), bottom.toInt())
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val leftOffset = timeTextSize * 3
        val width = (widthSize - leftOffset) / 9

        forEach {
            val child = it as TimetableItemView
            val timeAndDate = child.item.timeAndDate

            val w = when (child.item) {
                is Lunch -> widthSize - leftOffset
                else -> width - density
            }
            val h = oneHourHeight * timeAndDate.durationMinutes / 60f

            child.measure(
                MeasureSpec.makeMeasureSpec(w.toInt(), MeasureSpec.EXACTLY),
                MeasureSpec.makeMeasureSpec(h.toInt(), MeasureSpec.EXACTLY)
            )
        }

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
    val item: TimetableItem
) : AppCompatTextView(context) {

    init {
        setPadding(4, 4, 4, 4)
        textSize = 10f
        setTextColor(Color.BLACK)
        text = when (item) {
            is Session -> item.title
            is Party -> context.getString(R.string.party)
            is Lunch -> context.getString(R.string.lunch)
        }
        val backgroundResId = when (item) {
            is PublicSession -> when (item.language) {
                Language.JA -> R.drawable.time_table_item_ja
                Language.EN -> R.drawable.time_table_item_en
                Language.MIX -> R.drawable.time_table_item
            }
            is Lunch -> R.drawable.time_table_item_lunch
            else -> R.drawable.time_table_item
        }
        setBackgroundResource(backgroundResId)

        if (item is Lunch) {
            gravity = Gravity.CENTER
        }
    }
}
