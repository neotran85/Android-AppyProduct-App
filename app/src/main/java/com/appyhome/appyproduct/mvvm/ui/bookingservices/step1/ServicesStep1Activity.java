package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep1Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step2.ServicesStep2Activity;
import com.appyhome.appyproduct.mvvm.ui.custom.ItemsSelectionView;
import com.appyhome.appyproduct.mvvm.ui.custom.detail.TextDetailActivity;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import javax.inject.Inject;

public class ServicesStep1Activity extends BaseActivity<ActivityServicesBookingStep1Binding, ServicesStep1ViewModel> implements ServicesStep1Navigator, View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    ServicesStep1ViewModel mServicesStep1ViewModel;

    ActivityServicesBookingStep1Binding mBinder;
    private ArrayList<JSONObject> mServicesList;
    private View mCurrentServiceView;
    private int mSelectedServiceIndex;
    private ItemsSelectionView mMainServiceView;

    public static Intent getStartIntent(Context context) {
        Intent intent = new Intent(context, ServicesStep1Activity.class);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinder = getViewDataBinding();
        mBinder.setViewModel(mServicesStep1ViewModel);
        mServicesStep1ViewModel.setNavigator(this);
        setTitle(ServiceOrderInfo.getInstance().getServiceName());
        activeBackButton();
        setUpData();
        setUpListeners();
        setUpMultipleExtraServices();
    }

    private void setUpMultipleExtraServices() {
        View viewCleaning = mBinder.llServiceMain.findViewById(R.id.llCleaning);
        View viewAirCon = mBinder.llServiceMain.findViewById(R.id.llAirCon);
        View viewPlumbing = mBinder.llServiceMain.findViewById(R.id.llPlumbing);
        View viewElectric = mBinder.llServiceMain.findViewById(R.id.llElectrical);
        mMainServiceView = new ItemsSelectionView(true, viewCleaning, viewAirCon, viewPlumbing, viewElectric);
        mMainServiceView.setOnClickListener(this);
    }


    private void setUpData() {
        mServicesStep1ViewModel.setTypeServices(ServiceOrderInfo.getInstance().getType());
        mServicesList = ServiceOrderInfo.getInstance().getServices();
        if (mServicesList != null && mServicesList.size() > 0) {
            mBinder.lvServices.setAdapter(new ServicesAdapter(this, mServicesList));
            mBinder.lvServices.setOnItemClickListener(this);
        } else {
            mBinder.lvServices.setVisibility(View.GONE);
        }
    }

    private void setUpListeners() {
        mBinder.btnNext.setOnClickListener(this);
        mBinder.btSeeDetailService.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                boolean selected = checkIfServiceSelected();
                if (selected) {
                    goToStep2();
                    updateServiceOrderInfo();
                } else AlertManager.getInstance(this).showLongToast("Please choose a service");
                break;
            case R.id.btSeeDetailService:
                if (ServiceOrderInfo.getInstance().getSelectedService() != null)
                    viewDetailService();
                else AlertManager.getInstance(this).showLongToast("Please choose a service");
                break;
        }
    }

    private void updateServiceOrderInfo() {
        if (ServiceOrderInfo.getInstance().getType() == ServiceOrderInfo.SERVICE_AIR_CON_CLEANING) {
            AirConOptionView airConView = new AirConOptionView(mBinder.llServiceAirConCleaning);
            ServiceOrderInfo.getInstance().setArrayAirConOpts(airConView.getResultStrings());
            ServiceOrderInfo.getInstance().setNumberOfAirCons(airConView.getNumberOfAirCons());
        }
        if (ServiceOrderInfo.getInstance().getType() == ServiceOrderInfo.SERVICE_HOME_CLEANING) {
            HomeCleaningOptionView homeView = new HomeCleaningOptionView(mBinder.llServiceHomeCleaning);
            ServiceOrderInfo.getInstance().setArrayHomeCleaningOpts(homeView.getResultStrings());
        }
        ServiceOrderInfo.getInstance().setServiceMain(mMainServiceView.getSelectedStringValue());
    }

    private boolean checkIfServiceSelected() {
        JSONObject data = ServiceOrderInfo.getInstance().getSelectedService();
        if (data != null) {
            try {
                String nameService = data.getString("name");
                return nameService != null && nameService.length() > 0;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ServiceOrderInfo.getInstance().clear();
    }


    @Override
    public void handleError(Throwable throwable) {
        // handle error
    }


    @Override
    public ServicesStep1ViewModel getViewModel() {
        return mServicesStep1ViewModel;
    }

    @Override
    public int getBindingVariable() {
        return BR.viewModel;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_services_booking_step1;
    }

    @Override
    public void goToStep2() {
        startActivity(ServicesStep2Activity.getStartIntent(this));
    }

    @Override
    public void viewDetailService() {
        JSONObject data = ServiceOrderInfo.getInstance().getSelectedService();
        if (data != null) {
            try {
                Intent intent = TextDetailActivity.getStartIntent(this);
                intent.putExtra("title", data.get("name").toString());
                intent.putExtra("detail", data.get("detail").toString());
                startActivity(intent);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (mCurrentServiceView != null) {
            mCurrentServiceView.setBackgroundColor(ContextCompat.getColor(this, R.color.white));
        }
        if (view != null) {
            mCurrentServiceView = view;
            mSelectedServiceIndex = position;
            ServiceOrderInfo.getInstance().setSelectedService(mServicesList.get(position));
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        } else {
            ServiceOrderInfo.getInstance().setSelectedService(null);
        }
    }
}
