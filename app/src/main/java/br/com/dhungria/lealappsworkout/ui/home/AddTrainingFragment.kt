package br.com.dhungria.lealappsworkout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.lealappsworkout.constants.Constants.TRAINING_LIST_TO_EDIT
import br.com.dhungria.lealappsworkout.databinding.AddTrainingFragmentBinding
import br.com.dhungria.lealappsworkout.extensions.tryLoadImage
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.ui.dialog.FormularioImagemDialog
import br.com.dhungria.lealappsworkout.viewmodel.AddTrainingViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class AddTrainingFragment : Fragment() {

    private lateinit var binding: AddTrainingFragmentBinding

    private val viewModel: AddTrainingViewModel by viewModels()

    private val trainingListToEdit by lazy {
        arguments?.getParcelable<Training>(
            TRAINING_LIST_TO_EDIT
        )
    }

    private var url: String? = null


    private fun setupItemBackMenuBar() {
        binding.toolbarExerciseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateFields() = with(binding) {
        editTextName.setOnFocusChangeListener { _, focused ->
            if (!focused && editTextName.text.toString().isBlank()) {
                editTextInputLayoutName.apply {
                    helperText = "Insira o número do treino"
                    error = "Insira o número do treino"
                }
            } else {
                editTextInputLayoutName.apply {
                    helperText = null
                    error = null
                }
            }
        }
        editTextDescription.setOnFocusChangeListener { _, focused ->
            if (!focused && editTextDescription.text.toString().isBlank()) {
                editTextInputLayoutDescription.apply {
                    helperText = "Insira uma descrição"
                    error = "Insira uma descrição"
                }
            } else {
                editTextInputLayoutDescription.apply {
                    helperText = null
                    error = null
                }
            }
        }
    }


    private fun setupListener() = with(binding) {
        buttonDatePickerAddTraining.visibility = View.VISIBLE
        textviewDateAddTrainingFragment.visibility = View.VISIBLE

        imageViewAddTraining.setOnClickListener {
            FormularioImagemDialog(requireContext())
                .show(url) { imagemUrl ->
                    url = imagemUrl
                    imageViewAddTraining.tryLoadImage(url)
                }
        }

        buttonDoneAddTrainingFragment.setOnClickListener {
            if (!editTextName.text.isNullOrBlank() &&
                !editTextDescription.text.isNullOrBlank() &&
                !textviewDateAddTrainingFragment.text.isNullOrBlank()
            ) {
                val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(
                    textviewDateAddTrainingFragment.text.toString()
                )?.time ?: 0

                viewModel.insertTraining(
                    name = editTextName.text.toString(),
                    description = editTextDescription.text.toString(),
                    data = date,
                    image = url
                )
                findNavController().popBackStack()
            } else {
                Toast.makeText(requireContext(), "Preencha todos os campos", Toast.LENGTH_LONG)
                    .show()
            }
        }
    }

    private fun setupAccordingToEditMode(training: Training?) = with(binding) {
        training?.run {
            url = training.image
            val dateFormatted =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }.format(Date(date))

            viewModel.setupEditMode(id)
            editTextName.setText(name.toString())
            editTextDescription.setText(description)
            textviewDateAddTrainingFragment.text = dateFormatted
            imageViewAddTraining.tryLoadImage(image)
        }
    }

    private fun setupDatePicker() {
        val calendar = Calendar.getInstance()

        binding.buttonDatePickerAddTraining.setOnClickListener {
            val minDate = CalendarConstraints.Builder().setStart(calendar.timeInMillis).run {
                setValidator(DateValidatorPointForward.now())
            }
            MaterialDatePicker.Builder.datePicker().setCalendarConstraints(minDate.build()).build()
                .apply {
                    addOnPositiveButtonClickListener {
                        val dateFormatted =
                            SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                                timeZone = TimeZone.getTimeZone("UTC")
                            }.format(Date(it))
                        binding.textviewDateAddTrainingFragment.text = dateFormatted
                    }
                }.show(parentFragmentManager, "DatePicker")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddTrainingFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        validateFields()
        setupItemBackMenuBar()
        setupAccordingToEditMode(trainingListToEdit)
        setupListener()
        setupDatePicker()
    }

}