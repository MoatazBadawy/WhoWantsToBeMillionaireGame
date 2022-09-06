package com.example.android.whowantstobemillionaire.ui.question.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.Quiz
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.utils.helper.Answer
import com.example.android.whowantstobemillionaire.utils.helper.Constants.ERROR
import com.example.android.whowantstobemillionaire.utils.helper.Constants.STOP_TIMER
import com.example.android.whowantstobemillionaire.utils.helper.Constants.TIMER
import com.example.android.whowantstobemillionaire.utils.helper.add
import com.example.android.whowantstobemillionaire.utils.state.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class QuestionViewModel : ViewModel() {
    private val repository = QuizRepository()
    private val disposable = CompositeDisposable()

    private val _questionResponse = MutableLiveData<State<QuizResponse>>(State.Loading)
    val questionResponse: LiveData<State<QuizResponse>> get() = _questionResponse

    lateinit var disposableTimer: Disposable

    private fun getQuiz() {
        repository.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onGetQuestionsSuccess, ::onGetQuestionsFailed).add(disposable)
    }

    private fun onGetQuestionsSuccess(state: State<QuizResponse>) {
        if (state is State.Success) {
            _questionResponse.postValue(state)
            state.toData()?.quizzes.let { result ->
                sortQuestions(result as List<Quiz>)
            }
        } else {
            _questionResponse.postValue(State.Error(ERROR))
        }
    }

    private fun onGetQuestionsFailed(throwable: Throwable) {
        _questionResponse.postValue(State.Error(throwable.message.toString()))
    }

    private val allQuestion = mutableListOf<Quiz>()
    private val questionToReplace = mutableListOf<Quiz>()

    private fun sortQuestions(list: List<Quiz>) {
        list.forEachIndexed { index, quiz ->
            when (index) {
                5, 11, 17 -> questionToReplace.add(quiz)
                else -> allQuestion.add(quiz)
            }
        }
        setCurrentQuestion(list[0])
    }

    private val _currentQuestion = MutableLiveData<Quiz>()
    val currentQuestion: LiveData<Quiz> get() = _currentQuestion
    private var questionIndex = 0

    private fun setCurrentQuestion(quiz: Quiz) {
        _currentQuestion.postValue(allQuestion[questionIndex])
        questionIndex++
        setShuffledAnswers(quiz)
        prepareTimer()
        increaseCounter(counter++)
    }

    private val _answers = MutableLiveData<List<Answer?>>()
    val answers: LiveData<List<Answer?>> get() = _answers

    private fun setShuffledAnswers(quiz: Quiz) {
        val listOfAnswers = quiz.incorrectAnswers?.map {
            Answer(it, false)
        }?.plus(Answer(quiz.correctAnswer!!, true))?.shuffled()
        _answers.postValue(listOfAnswers!!)

        Log.v("QuizModel", quiz.correctAnswer.toString())
    }

    private var counter = 1
    private val _numberOfQuestion = MutableLiveData(counter)
    val numberOfQuestion: LiveData<Int> get() = _numberOfQuestion

    private fun increaseCounter(counter: Int) {
        _numberOfQuestion.postValue(counter)
    }

    private val _losingNavigate = MutableLiveData(false)
    val losingNavigate: LiveData<Boolean> get() = _losingNavigate

    private val _resultNavigate = MutableLiveData(false)
    val resultNavigate: LiveData<Boolean> get() = _resultNavigate

    fun onAnswerClickListener(answer: Answer) {
        if (answer.isCorrect && questionIndex < 15) {
            disposableTimer.dispose()
            setCurrentQuestion(allQuestion[questionIndex])
        } else if (questionIndex == 15) {
            disposableTimer.dispose()
            _resultNavigate.postValue(true)

        } else {
            disposableTimer.dispose()
            _losingNavigate.postValue(true)
        }
    }

    private val _timer = MutableLiveData<String>("30")
    val timer: LiveData<String> = _timer

    private fun prepareTimer() {
        disposableTimer = Observable.intervalRange(
            0, 31, 1, 1, TimeUnit.SECONDS
        ).map { TIMER - it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.d("tag", it.toString())
                _timer.postValue(it.toString())
                onTimeIsFinished()

            }, { e ->
                e.message
            })
    }

    private fun onTimeIsFinished() {
        if (_timer.value == STOP_TIMER) _losingNavigate.postValue(true)
    }

    private val _leaveQuestion = MutableLiveData(false)
    val leaveQuestion: LiveData<Boolean> get() = _losingNavigate

    fun onLeaveClickListener() {
        _leaveQuestion.postValue(true)
    }

    init {
        getQuiz()
        onTimeIsFinished()
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}