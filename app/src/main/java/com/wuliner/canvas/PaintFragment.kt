package com.wuliner.canvas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.wuliner.canvas.databinding.FragmentPaintBinding

class PaintFragment : Fragment() {
    private lateinit var binding: FragmentPaintBinding
    private val viewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPaintBinding.inflate(layoutInflater, container, false)

        binding.drawingBoardView.addDrawListener(viewModel) {
            checkState()
        }

        viewModel.reDoState.observe(viewLifecycleOwner) { changeRedoState(it) }
        viewModel.unDoState.observe(viewLifecycleOwner) { changeRedoState(it) }
        viewModel.penState.observe(viewLifecycleOwner) { changeRedoState(it) }
        viewModel.eraserState.observe(viewLifecycleOwner) { changeRedoState(it) }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.redo.setOnClickListener {
            binding.drawingBoardView.reDraw()
        }

        binding.undo.setOnClickListener {
            binding.drawingBoardView.unDraw()
        }

        binding.remove.setOnClickListener {
            
            binding.drawingBoardView.clearCanvas()
        }

        binding.easer.setOnClickListener {
            changeEraserState(true)
        }

        binding.pen.setOnClickListener {
            changePenState(true)
        }
    }

    fun checkState() {
        val version = binding.drawingBoardView.version
        changeUndoState(version - 1 >= 0)
        changeRedoState(version + 1 < binding.drawingBoardView.pathList.size - 1)
    }

    fun changeUndoState(enabled: Boolean) {
        if (enabled) {
            binding.undo.setImageResource(R.drawable.undo)
            binding.undo.isEnabled = true
        } else {
            binding.undo.setImageResource(R.drawable.undo_un)
            binding.undo.isEnabled = false
        }
    }

    fun changeRedoState(enabled: Boolean) {
        if (enabled) {
            binding.redo.setImageResource(R.drawable.redo)
            binding.redo.isEnabled = true
        } else {
            binding.redo.setImageResource(R.drawable.redo_un)
            binding.redo.isEnabled = false
        }
    }

   fun changePenState(enabled: Boolean) {
       if (enabled) {
           binding.pen.setImageResource(R.drawable.pen)
           binding.pen.isEnabled = true
       } else {
           binding.pen.setImageResource(R.drawable.pen_un)
           binding.pen.isEnabled = false
       }
   }

    fun changeEraserState(enabled: Boolean) {
        if (enabled) {
            binding.easer.setImageResource(R.drawable.eraser)
        } else {
            binding.easer.setImageResource(R.drawable.eraser_un)
        }
    }

}