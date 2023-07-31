package com.wuliner.canvas

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View


class DrawingBoardView(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private lateinit var mViewModel: SharedViewModel
    var version = 0
    private var path = Path()
    private val eraserIsEnabled = false
    private val penIsEnabled = true
    private var mCallback: (() -> Unit)? = null
    private var paint = Paint().apply {
        isAntiAlias = true
        color = Color.BLACK
        style = Paint.Style.STROKE
        strokeJoin = Paint.Join.ROUND
        strokeWidth = 5f
    }
    var pathList = mutableListOf<Path>(Path())


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawPath(path, paint)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mCallback?.let {
            it()
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val x = event.x
        val y = event.y
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                changeStoredArraylist()
                path.moveTo(x, y)
                invalidate()
            }
            MotionEvent.ACTION_MOVE -> {
                changeStoredArraylist()
                path.lineTo(x, y)
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                version++
                mViewModel.version.apply {
                    postValue(value?.plus(1) ?: 0)
                }
                pathList.add(Path(path))
                mCallback?.let {
                    it()
                }
            }
        }
        return true
    }

    fun setColor(color: Int) {
        paint.color = color
    }

    fun setStrokeWidth(width: Float) {
        paint.strokeWidth = width
    }

    fun clearCanvas() {
        path.reset()
        invalidate()
    }

    fun unDraw() {
        Log.v("test", "现在的版本$version")
        Log.v("test", "path有${pathList.size}")
        path = pathList[version - 1]
        version--
        mCallback?.let { it() }
        invalidate()
    }

    fun reDraw() {
        path = pathList[version + 1]
        version++
        mCallback?.let { it() }
        invalidate()
    }


    fun addDrawListener(viewModel: SharedViewModel, callback: (() -> Unit)) {
        mViewModel = viewModel
        mCallback = callback
    }

    fun changeStoredArraylist() {
        pathList = pathList.subList(0, version + 1)
        mCallback?.let { it() }
    }

}
