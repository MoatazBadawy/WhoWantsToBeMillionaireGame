package com.example.android.whowantstobemillionaire.ui.question.viewmodel

import android.os.CountDownTimer
import android.util.Log
import androidx.constraintlayout.widget.ConstraintSet
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.Quiz
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.utils.helper.Answer
import com.example.android.whowantstobemillionaire.utils.helper.Constants.ERROR
import com.example.android.whowantstobemillionaire.utils.helper.add
import com.example.android.whowantstobemillionaire.utils.state.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class QuestionViewModel : ViewModel() {
    private val repository = QuizRepository()
    private val disposable = CompositeDisposable()

    private val _questionResponse = MutableLiveData<State<QuizResponse>>(State.Loading)
    val questionResponse: LiveData<State<QuizResponse>> get() = _questionResponse
    val _timer  = MutableLiveData<String>("30")
    val timer:LiveData<String> = _timer


    private fun getQuiz() {
        repository.getAllQuestions()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(::onGetQuestionsSuccess, ::onGetQuestionsFailed).add(disposable)
    }

    private fun onGetQuestionsSuccess(state: State<QuizResponse>) {
        if (state is State.Success) {
            _questionResponse.postValue(state)
            state.data?.quizzes.let { result ->
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

    private val _losingNavigate = MutableLiveData(false)
    val losingNavigate: LiveData<Boolean> get() = _losingNavigate

    fun onAnswerClickListener(answer: Answer) {
        if (answer.isCorrect) {
            setCurrentQuestion(allQuestion[questionIndex])
        } else {
            _losingNavigate.postValue(true)
        }
    }

    init {
        getQuiz()

    }

    override fun onCleared() {
        super.onCleared()
        disposable.dispose()
    }
    private fun prepareTimer() {
        val observable = Observable.intervalRange(0, 11, 2, 1, TimeUnit.SECONDS)
            .map { 10- it}

            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())

        observable.subscribe({
            Log.d("tag", it.toString())
            _timer.postValue(it.toString())

        }, { e ->
            e.message
        }).add(disposable)

//        object : CountDownTimer(1000,30000){
//            override fun onTick(p0: Long) {
//                _timer.postValue(p0.toString())
//            }
//
//            override fun onFinish() {
//                TODO("Not yet implemented")
//
//
//
//
//            }
//var i = 0
//Observable.interval(1000L, TimeUnit.MILLISECONDS)
//    .timeInterval()
//    .observeOn(AndroidSchedulers.mainThread())
//    .subscribe {
//        Log.v("tag", (i++).toString())
//    }
//

// Observable.interval(10,1, TimeUnit.SECONDS)
//     .take(10)
//
//            .timeInterval()
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .subscribe {
//                Log.d("tag", it.value().toString())
//            }
//        Observable.range(1,25)
//            .map { 25 - it}.
//            timeInterval(TimeUnit.SECONDS)
//            .subscribe {
//                Log.d("tag", it.toString())
//            }

    }




}

