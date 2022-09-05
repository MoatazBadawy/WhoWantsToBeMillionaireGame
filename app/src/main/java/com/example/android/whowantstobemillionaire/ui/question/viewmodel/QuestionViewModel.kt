package com.example.android.whowantstobemillionaire.ui.question.viewmodel

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.android.whowantstobemillionaire.data.model.Quiz
import com.example.android.whowantstobemillionaire.data.model.QuizResponse
import com.example.android.whowantstobemillionaire.data.repository.QuizRepository
import com.example.android.whowantstobemillionaire.utils.helper.Answer
import com.example.android.whowantstobemillionaire.utils.helper.Constants.ERROR
import com.example.android.whowantstobemillionaire.utils.helper.add
import com.example.android.whowantstobemillionaire.utils.state.State
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class QuestionViewModel : ViewModel() {
    private val repository = QuizRepository()
    private val disposable = CompositeDisposable()
    private var counter=1

    private val _questionResponse = MutableLiveData<State<QuizResponse>>(State.Loading)
    val questionResponse: LiveData<State<QuizResponse>>
    get() = _questionResponse

    private val _numberOfQuestion = MutableLiveData(counter)
    val numberOfQuestion: LiveData<Int>
        get() = _numberOfQuestion

    private val _losingNavigate = MutableLiveData(false)
    val losingNavigate: LiveData<Boolean>
    get() = _losingNavigate

    private val allQuestion = mutableListOf<Quiz>()
    private val questionToReplace = mutableListOf<Quiz>()


    private val _currentQuestion = MutableLiveData<Quiz>()
    val currentQuestion: LiveData<Quiz>
    get() = _currentQuestion
    private var questionIndex = 0


    private val _answers = MutableLiveData<List<Answer?>>()
    val answers: LiveData<List<Answer?>>
    get() = _answers

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



    private fun sortQuestions(list: List<Quiz>) {
        list.forEachIndexed { index, quiz ->
            when (index) {
                5, 11, 17 -> questionToReplace.add(quiz)
                else -> allQuestion.add(quiz)
            }
        }
        setCurrentQuestion(list[0])
    }



    private fun setCurrentQuestion(quiz: Quiz) {
        _currentQuestion.postValue(allQuestion[questionIndex])
        questionIndex++
        increaseCounter(counter++)
        setShuffledAnswers(quiz)
    }

    fun increaseCounter(counter:Int){
        _numberOfQuestion.postValue(counter)
    }


    private fun setShuffledAnswers(quiz: Quiz) {
        val listOfAnswers = quiz.incorrectAnswers?.map {
            Answer(it, false)
        }?.plus(Answer(quiz.correctAnswer!!, true))?.shuffled()
        _answers.postValue(listOfAnswers!!)

        Log.v("QuizModel", quiz.correctAnswer.toString())
    }



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


}