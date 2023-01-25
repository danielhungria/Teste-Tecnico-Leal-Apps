package br.com.dhungria.lealappsworkout.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.lealappsworkout.R
import br.com.dhungria.lealappsworkout.constants.Constants.DATE_DEFAULT
import br.com.dhungria.lealappsworkout.constants.Constants.TAG_DATE_PICKER
import br.com.dhungria.lealappsworkout.constants.Constants.TRAINING_LIST_TO_EDIT
import br.com.dhungria.lealappsworkout.constants.Constants.UTC_TIMEZONE
import br.com.dhungria.lealappsworkout.databinding.AddTrainingFragmentBinding
import br.com.dhungria.lealappsworkout.extensions.setupFieldValidationListener
import br.com.dhungria.lealappsworkout.extensions.toast
import br.com.dhungria.lealappsworkout.extensions.tryLoadImage
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.ui.dialog.FormImageDialog
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

    private var url: String? = ""


    private fun setupItemBackMenuBar() {
        binding.toolbarExerciseFragment.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun validateFields() = with(binding) {
        editTextInputLayoutName.setupFieldValidationListener(R.string.insert_number_of_training)
        editTextInputLayoutDescription.setupFieldValidationListener(R.string.insert_description)
    }


    private fun setupListener() = with(binding) {
        buttonDatePickerAddTraining.visibility = View.VISIBLE
        textviewDateAddTrainingFragment.visibility = View.VISIBLE

        imageViewAddTraining.setOnClickListener {
            FormImageDialog(it.context)
                .show(url) { imageUrl ->
                    url = imageUrl
                    imageViewAddTraining.tryLoadImage(url)
                }
        }

        buttonDoneAddTrainingFragment.setOnClickListener {
            viewModel.insertTraining(
                name = editTextName.text.toString(),
                description = editTextDescription.text.toString(),
                date = textviewDateAddTrainingFragment.text.toString(),
                image = url,
                closeScreen = { findNavController().popBackStack() },
                onError = { toast(R.string.fill_all_fields) }
            )
        }
    }

    private fun setupAccordingToEditMode(training: Training?) = with(binding) {
        training?.run {
            url = training.image
            val dateFormatted =
                SimpleDateFormat(DATE_DEFAULT, Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone(UTC_TIMEZONE)
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
                            SimpleDateFormat(DATE_DEFAULT, Locale.getDefault()).apply {
                                timeZone = TimeZone.getTimeZone(UTC_TIMEZONE)
                            }.format(Date(it))
                        binding.textviewDateAddTrainingFragment.text = dateFormatted
                        viewModel.setupDate(dateFormatted)
                    }
                }.show(parentFragmentManager, TAG_DATE_PICKER)
        }

        binding.textviewDateAddTrainingFragment.text = viewModel.date

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
        setupListener()
        setupDatePicker()
        setupAccordingToEditMode(trainingListToEdit)
    }

}