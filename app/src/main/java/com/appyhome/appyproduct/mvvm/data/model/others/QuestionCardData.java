package com.appyhome.appyproduct.mvvm.data.model.others;

import com.appyhome.appyproduct.mvvm.data.model.db.Option;
import com.appyhome.appyproduct.mvvm.data.model.db.Question;

import java.util.List;


public class QuestionCardData {

    public Question question;
    public List<Option> options;
    public boolean mShowCorrectOptions;

    public QuestionCardData(Question question, List<Option> options) {
        this.question = question;
        this.options = options;
    }
}
