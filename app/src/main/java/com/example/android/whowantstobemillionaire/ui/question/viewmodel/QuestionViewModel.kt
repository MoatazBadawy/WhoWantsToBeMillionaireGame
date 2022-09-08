package com.example.android.whowantstobemillionaire.ui.question.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.Quiz
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.utils.helper.Answer
import com.example.android.whowantstobemillionaire.utils.helper.AnswerState
import com.example.android.whowantstobemillionaire.utils.helper.Constants.COINS
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

    private val allQuestion = mutableListOf<Quiz>()
    private val questionToReplace = mutableListOf<Quiz>()

    private val _currentQuestion = MutableLiveData<Quiz>()
    val currentQuestion: LiveData<Quiz> get() = _currentQuestion
    private var questionIndex = 0

    private val _answers = MutableLiveData<List<Answer?>>()
    val answers: LiveData<List<Answer?>> get() = _answers

    private var _answerState = MutableLiveData<AnswerState>()
    val answerState: LiveData<AnswerState>
        get() = _answerState

    private var counter = 1
    private val _numberOfQuestion = MutableLiveData(counter)
    val numberOfQuestion: LiveData<Int> get() = _numberOfQuestion

    private var currentCoin = 0
    private var coinsCount = COINS
    private val _coins = MutableLiveData(coinsCount[0])
    val coins: LiveData<Int> get() = _coins

    private lateinit var disposableTimer: Disposable
    private val _timer = MutableLiveData("30")
    val timer: LiveData<String> = _timer

    private val _losingNavigate = MutableLiveData(false)
    val losingNavigate: LiveData<Boolean> get() = _losingNavigate

    private val _resultNavigate = MutableLiveData(false)
    val resultNavigate: LiveData<Boolean> get() = _resultNavigate

    private val _leaveQuestion = MutableLiveData(false)
    val leaveQuestion: LiveData<Boolean> get() = _leaveQuestion

    private val _changeQuestionClickOnce = MutableLiveData(false)
    val changeQuestionClickOnce: LiveData<Boolean> get() = _changeQuestionClickOnce

    private val _removeQuestionClickOnce = MutableLiveData(false)
    val removeQuestionClickOnce: LiveData<Boolean> get() = _removeQuestionClickOnce

    init {
        getQuiz()
        onTimeIsFinished()
    }

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

    private fun sortQuestions(list: List<Quiz>) {
        list.forEachIndexed { index, quiz ->
            when (index) {
                5, 11, 17 -> questionToReplace.add(quiz)
                else -> allQuestion.add(quiz)
            }
        }
        setCurrentQuestion(0, false)
    }

    private fun setCurrentQuestion(index: Int, isReplaced: Boolean) {
        _currentQuestion.postValue(allQuestion[questionIndex])
        questionIndex++
        setShuffledAnswers(allQuestion[index])
        prepareTimer()
        if (!isReplaced) {
            increaseCounter(counter++)
            increaseCoins(currentCoin++)
        }
    }

    private fun setShuffledAnswers(quiz: Quiz) {
        val listOfAnswers = quiz.incorrectAnswers?.map {
            Answer(it, false)
        }?.plus(Answer(quiz.correctAnswer!!, true))?.shuffled()
        _answers.postValue(listOfAnswers!!)

        Log.v("QuizModel", quiz.correctAnswer.toString())
    }

    private fun increaseCounter(counter: Int) {
        _numberOfQuestion.postValue(counter)
    }

    private fun increaseCoins(currentCoins: Int) {
        _coins.postValue(coinsCount[currentCoins])
    }

    fun onAnswerClickListener(answer: Answer) {
        if (answer.isCorrect && questionIndex < 6)
            succeedAnswer()
        else if (!answer.isCorrect && questionIndex in 6..14)
            releaseCoins()
        else if (answer.isCorrect && questionIndex in 6..14)
            succeedAnswer()
        else if (questionIndex == 15) {
            increaseCoins(currentCoin++)
            finishAnswers()
        } else
            wrongAnswer()
    }

    private fun succeedAnswer() {
        _answerState.postValue(AnswerState.CORRECT_ANSWER)

        Observable.timer(1, TimeUnit.SECONDS).subscribe {
            _answerState.postValue(AnswerState.DEFAULT)
            disposableTimer.dispose()
            setCurrentQuestion(questionIndex, false)
        }.add(disposable)
    }

    private fun releaseCoins() {
        _answerState.postValue(AnswerState.WRONG_ANSWER)

        Observable.timer(1, TimeUnit.SECONDS).subscribe {
            disposableTimer.dispose()
            _resultNavigate.postValue(true)
        }.add(disposable)
    }

    private fun wrongAnswer() {
        _answerState.postValue(AnswerState.WRONG_ANSWER)

        Observable.timer(1, TimeUnit.SECONDS).subscribe {
            disposableTimer.dispose()
            _losingNavigate.postValue(true)
        }.add(disposable)
    }

    private fun finishAnswers() {
        disposableTimer.dispose()
        _resultNavigate.postValue(true)
    }

    private fun prepareTimer() {
        disposableTimer = Observable.intervalRange(
            0, 31, 1, 1, TimeUnit.SECONDS
        ).map { TIMER - it }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _timer.postValue(it.toString())
                onTimeIsFinished()

            }, { e ->
                e.message
            })
    }

    private fun onTimeIsFinished() {
        if (_timer.value == STOP_TIMER) _losingNavigate.postValue(true)
    }

    fun onLeaveClickListener() {
        _leaveQuestion.postValue(true)
    }

    fun changeQuestionByDifficulty() {
        when (questionIndex) {
            in 0..4 -> onChangeQuestion(0)
            in 5..9 -> onChangeQuestion(1)
            in 10..14 -> onChangeQuestion(2)
        }
    }

    private fun onChangeQuestion(index: Int) {
        allQuestion.removeAt(questionIndex)
        allQuestion.add(questionToReplace[index])
        questionToReplace.removeAt(index)
        disposableTimer.dispose()
        _changeQuestionClickOnce.postValue(true)
        setCurrentQuestion(questionIndex, true)
    }

    fun remove2Answers() {
        val indices = listOf(0, 1, 2, 3).shuffled()
        var count = 0
        var index = 0
        _answers.value?.let {
            val listOfAnswers = it
            while (count < 2) {
                if (listOfAnswers[indices[index]]?.isCorrect == false) {
                    listOfAnswers[indices[index]]?.answer = ""
                    count++
                }
                index++
            }
            _removeQuestionClickOnce.postValue(true)
            _answers.postValue(listOfAnswers)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
}