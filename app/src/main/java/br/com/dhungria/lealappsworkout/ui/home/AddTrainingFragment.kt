package br.com.dhungria.lealappsworkout.ui.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import br.com.dhungria.lealappsworkout.constants.Constants.TRAINING_LIST_TO_EDIT
import br.com.dhungria.lealappsworkout.databinding.AddTrainingFragmentBinding
import br.com.dhungria.lealappsworkout.models.Training
import br.com.dhungria.lealappsworkout.viewmodel.AddTrainingViewModel
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointForward
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.firebase.Timestamp
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*
import kotlin.time.Duration.Companion.nanoseconds
import kotlin.time.DurationUnit

@AndroidEntryPoint
class AddTrainingFragment: Fragment() {

    private lateinit var binding: AddTrainingFragmentBinding

    private val viewModel: AddTrainingViewModel by viewModels()

    private val trainingListToEdit by lazy { arguments?.getParcelable<Training>(TRAINING_LIST_TO_EDIT) }


    private fun setupItemBackMenuBar(){
        binding.toolbarExerciseFragment.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupListener() = with(binding){
        cardViewAddTraining.visibility = View.GONE

        buttonDoneAddTrainingFragment.setOnClickListener {
            val date = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(textviewDateAddTrainingFragment.text.toString())?.time?:0
            viewModel.insertTraining(
                name = editTextName.text.toString(),
                description = editTextDescription.text.toString(),
                data = date
            )
            findNavController().popBackStack()
        }
    }


    private fun setupAccordingToEditMode(training: Training?) = with(binding){

        training?.run {
            val dateFormatted =
                SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).apply {
                    timeZone = TimeZone.getTimeZone("UTC")
                }.format(Date(date))

            viewModel.setupEditMode(id)
            editTextName.setText(name.toString())
            editTextDescription.setText(description)
            textviewDateAddTrainingFragment.text = dateFormatted
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
        setupItemBackMenuBar()
        setupAccordingToEditMode(trainingListToEdit)
        setupListener()
        setupDatePicker()
    }

}