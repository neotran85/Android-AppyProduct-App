package com.appyhome.appyproduct.mvvm.ui.account.myprofile.textinput;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

@Module
public abstract class TextInputDialogProvider {

    @ContributesAndroidInjector(modules = TextInputDialogModule.class)
    abstract TextInputDialog provideTextInputDialogFactory();

}
