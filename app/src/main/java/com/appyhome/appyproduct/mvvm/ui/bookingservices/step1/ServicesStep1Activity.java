package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyService;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep1Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.ServiceOrderInfo;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step2.ServicesStep2Activity;
import com.appyhome.appyproduct.mvvm.ui.custom.ItemsSelectionView;
import com.appyhome.appyproduct.mvvm.ui.custom.detail.TextDetailActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import java.util.ArrayList;

import javax.inject.Inject;

public class ServicesStep1Activity extends BaseActivity<ActivityServicesBookingStep1Binding, ServicesStep1ViewModel> implements ServicesStep1Navigator, View.OnClickListener, AdapterView.OnItemClickListener {

    @Inject
    ServicesStep1ViewModel mServicesStep1ViewModel;

    ActivityServicesBookingStep1Binding mBinder;
    private ArrayList<AppyService> mServicesList;
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
        ViewUtils.setOnClickListener(this, mBinder.btnNext,
                mBinder.btSeeDetailService,
                mBinder.btOurFAQ,
                mBinder.btOurTANDC,
                mBinder.llServiceHomeCleaning,
                mBinder.llServiceAirConCleaning);
        ViewUtils.setOnClickListener(mBinder.llServiceAirConCleaning, this, R.id.ibAirConSizeMoreInfo,
                R.id.ibAirConTypeMoreInfo);
        ViewUtils.setOnClickListener(mBinder.llServiceHomeCleaning, this, R.id.ibSuppliesMoreInfo);
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
            case R.id.btOurFAQ:
                viewOurFAQ();
                break;
            case R.id.btOurTANDC:
                viewOurTANDC();
                break;
            case R.id.ibSuppliesMoreInfo:
                viewSuppliesMoreInformation();
                break;
            case R.id.ibAirConSizeMoreInfo:
                viewAirConSizeMoreInformation();
                break;
            case R.id.ibAirConTypeMoreInfo:
                viewAirConTypeMoreInformation();
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
        AppyService data = ServiceOrderInfo.getInstance().getSelectedService();
        if (data != null) {
            String nameService = data.name;
            return nameService != null && nameService.length() > 0;
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
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
        AppyService data = ServiceOrderInfo.getInstance().getSelectedService();
        if (data != null) {
            Intent intent = TextDetailActivity.getStartIntent(this);
            intent.putExtra("title", data.name);
            intent.putExtra("detail", data.detail);
            startActivity(intent);
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
        }
    }

    @Override
    public void viewOurFAQ() {
        AlertManager.getInstance(this).openInformationBrowser("Booking / Ordering FAQ",
                "file:///android_asset/html/our_faq.html");
    }

    @Override
    public void viewOurTANDC() {
        AlertManager.getInstance(this).openInformationBrowser("OUR BOOKING T&C",
                "http://appyhomeplus.com/terms-conditions/");
    }

    @Override
    public void viewSuppliesMoreInformation() {
        AlertManager.getInstance(this).openInformationBrowser("CLEANING SUPPLIES",
                "file:///android_asset/html/cleaning_supplies.html");
    }

    public void viewAirConTypeMoreInformation() {
        AlertManager.getInstance(this).openInformationBrowser("Aircon type of service",
                "file:///android_asset/html/air_con_type_info.html");
    }

    public void viewAirConSizeMoreInformation() {
        AlertManager.getInstance(this).openInformationBrowser("Aircon Size",
                "file:///android_asset/html/air_con_size_info.html");
    }
}
