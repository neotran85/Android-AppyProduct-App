package com.appyhome.appyproduct.mvvm.ui.appyproduct.topic;


import com.appyhome.appyproduct.mvvm.data.local.db.realm.ProductTopic;

import io.realm.RealmResults;

public interface ProductTopicNavigator {
    void showTopics(RealmResults<ProductTopic> topics);

    void openProductCategories(int idTopic);
}
