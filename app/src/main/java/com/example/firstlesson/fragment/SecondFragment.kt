package com.example.firstlesson.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.firstlesson.MainActivity
import com.example.firstlesson.dopBalli.SwipeToDeleteCallbackImpl
import com.example.firstlesson.adapter.ElementAdapter
import com.example.firstlesson.databinding.CreateElementDialogBinding
import com.example.firstlesson.databinding.FragmentSecondBinding
import com.example.firstlesson.decorator.ItemDecorator
import com.example.firstlesson.entity.Element

class SecondFragment : Fragment() {

    private lateinit var binding: FragmentSecondBinding
    private lateinit var bindingOfDialog: CreateElementDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSecondBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val elements = (requireActivity() as MainActivity).elements
        val allPictures = (requireActivity() as MainActivity).pictures
        init(ElementAdapter(elements))
        binding.addElementBtn.setOnClickListener {
            val alert = AlertDialog.Builder(context).apply {
                bindingOfDialog = CreateElementDialogBinding.inflate(LayoutInflater.from(context))
                setView(bindingOfDialog.root)
            }.show()
                bindingOfDialog.okBtn.setOnClickListener {
                    var id = 0
                    try {
                        id = bindingOfDialog.vtPosition.text.toString().toInt()
                    } catch (e: Exception) {}
                    val pictures = ArrayList<Int>()
                    for (i in 1..((2 until 10).random())) {
                        pictures.add(allPictures[(0 until allPictures.size).random()])
                    }
                    val element = Element(
                        id,
                        bindingOfDialog.vtName.text.toString(),
                        bindingOfDialog.vtDescription.text.toString(),
                        pictures
                    )
                    if (id - 1 >= elements.size || id <= 0) {
                        (requireActivity() as MainActivity).elements.add(element)
                        element.id = (requireActivity() as MainActivity).elements.size + 1
                    } else {
                        (requireActivity() as MainActivity).elements
                            .add(id - 1, element)
                    }
                    init(ElementAdapter((requireActivity() as MainActivity).elements))
                    alert.dismiss()
                }
            bindingOfDialog.noBtn.setOnClickListener {
                alert.dismiss()
            }
        }
    }

    private fun init(adapter: ElementAdapter) {
        binding.apply {
            listOne.layoutManager = LinearLayoutManager(requireActivity()).apply {
                orientation = RecyclerView.VERTICAL
            }
            context?.let {
                SwipeToDeleteCallbackImpl((requireActivity() as MainActivity).elements, it, binding)
            }?.let {
                ItemTouchHelper(it).attachToRecyclerView(binding.listOne)
            }
            listOne.addItemDecoration(ItemDecorator(16, 8))
            listOne.adapter = adapter
        }
    }
}