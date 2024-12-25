package com.example.carsharingapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import com.example.carsharingapp.R
import com.example.carsharingapp.data.CarInfo
import com.example.carsharingapp.databinding.FragmentAddCarInfoBinding


class AddCarInfoFragment : Fragment() {

    private var _binding: FragmentAddCarInfoBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_add_car_info, container, false)
        _binding = FragmentAddCarInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val locate = arguments?.getString("locate")

        val options = arrayOf("А/Т", "Р/Т")

        /*val etYearAddCar = view.findViewById<EditText>(R.id.etYearAddCar)
        val etBrandAddCar = view.findViewById<EditText>(R.id.etBrandAddCar)
        val etModelAddCar = view.findViewById<EditText>(R.id.etModelAddCar)
        val etMileageAddCar = view.findViewById<EditText>(R.id.etMileageAddCar)
        val etDescriptionAddCar = view.findViewById<EditText>(R.id.etDescriptionAddCar)
        val etRentPriceAddCar = view.findViewById<EditText>(R.id.etRentPriceAddCar)
        val etSavePriceAddCar = view.findViewById<EditText>(R.id.etSavePriceAddCar)*/
        val autoCompleteTextView = view.findViewById<AutoCompleteTextView>(R.id.actvChooseTrans)
        val adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, options)
        autoCompleteTextView.setAdapter(adapter)

        // Обработка выбора элемента
        /*autoCompleteTextView.setOnItemClickListener { parent, view, position, id ->
            val selectedItem = parent.getItemAtPosition(position) as String
            // Действия после выбора элемента
        }*/

        autoCompleteTextView.setOnClickListener {
            autoCompleteTextView.showDropDown()
            checkingfields()
        }

        binding.etYearAddCar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkingfields()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etBrandAddCar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkingfields()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etModelAddCar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkingfields()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etMileageAddCar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkingfields()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etDescriptionAddCar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                checkingfields()
            }
            override fun afterTextChanged(p0: Editable?) {}
        })

        binding.etRentPriceAddCar.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                val per = Integer.parseInt(p0.toString()) / 10
                binding.etSavePriceAddCar.setText(per.toString())
                checkingfields()
            }
        })

        binding.btnEndAddCar.setOnClickListener {
            val newCar = CarInfo(
                null,
                binding.etModelAddCar.text.toString(),
                binding.etBrandAddCar.text.toString(),
                binding.etRentPriceAddCar.text.toString(),
                binding.etSavePriceAddCar.text.toString(),
                binding.actvChooseTrans.text.toString(),
                "Бензин",
                binding.etDescriptionAddCar.text.toString(),
                binding.etMileageAddCar.text.toString(),
                binding.etYearAddCar.text.toString(),
                locate?: "",
                listOf("")
                )

            val fragment = AddCarPhotoFragment()
            val bundle = Bundle()
            bundle.putParcelable("car_info", newCar)
            fragment.arguments = bundle

            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_container_settings, fragment)
                .addToBackStack(null)
                .commit()
        }

        binding.ibBackAddCarInfo.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }

    fun checkingfields() {
        if (binding.etYearAddCar.text.toString() != "" &&
            binding.etBrandAddCar.text.toString() != "" &&
            binding.etModelAddCar.text.toString() != "" &&
            binding.etMileageAddCar.text.toString() != "" &&
            binding.etDescriptionAddCar.text.toString() != "" &&
            binding.etRentPriceAddCar.text.toString() != "" &&
            binding.etSavePriceAddCar.text.toString() != "" &&
            binding.actvChooseTrans.text.toString() != "") {binding.btnEndAddCar.visibility = View.VISIBLE}
        else { binding.btnEndAddCar.visibility = View.INVISIBLE }
    }
}