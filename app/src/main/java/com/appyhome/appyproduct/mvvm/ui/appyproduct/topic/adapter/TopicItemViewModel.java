package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic.adapter;

import android.databinding.ObservableField;

import com.appyhome.appyproduct.mvvm.data.DataManager;
import com.appyhome.appyproduct.mvvm.ui.base.BaseViewModel;
import com.appyhome.appyproduct.mvvm.utils.rx.SchedulerProvider;

public class TopicItemViewModel extends BaseViewModel<TopicItemNavigator> {

    public ObservableField<String> imageURL = new ObservableField<>("");

    private int idTopic;

    public TopicItemViewModel(DataManager dataManager,
                              SchedulerProvider schedulerProvider) {
        super(dataManager, schedulerProvider);
    }

    public TopicItemViewModel() {
        super();
    }

    public int getIdTopic() {
        return idTopic;
    }

    public void setIdTopic(int idTopic) {
        this.idTopic = idTopic;
    }
}
