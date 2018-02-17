package com.appyhome.appyproduct.mvvm.ui.bookingservices.step1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RadioGroup;

import com.appyhome.appyproduct.mvvm.BR;
import com.appyhome.appyproduct.mvvm.R;
import com.appyhome.appyproduct.mvvm.data.model.db.AppyService;
import com.appyhome.appyproduct.mvvm.data.model.db.ServiceOrderUserInput;
import com.appyhome.appyproduct.mvvm.data.remote.ApiUrlConfig;
import com.appyhome.appyproduct.mvvm.databinding.ActivityServicesBookingStep1Binding;
import com.appyhome.appyproduct.mvvm.ui.base.BaseActivity;
import com.appyhome.appyproduct.mvvm.ui.bookingservices.step2.ServicesStep2Activity;
import com.appyhome.appyproduct.mvvm.ui.custom.ItemsSelectionView;
import com.appyhome.appyproduct.mvvm.ui.custom.detail.TextDetailActivity;
import com.appyhome.appyproduct.mvvm.utils.helper.ViewUtils;
import com.appyhome.appyproduct.mvvm.utils.manager.AlertManager;

import java.util.ArrayList;

import javax.inject.Inject;

public class ServicesStep1Activity extends BaseActivity<ActivityServicesBookingStep1Binding, ServicesStep1ViewModel> implements ServicesStep1Navigator, View.OnClickListener, AdapterView.OnItemClickListener, RadioGroup.OnCheckedChangeListener {

    @Inject
    ServicesStep1ViewModel mServicesStep1ViewModel;

    ActivityServicesBookingStep1Binding mBinder;
    private ArrayList<AppyService> mServicesList;
    private View mCurrentServiceView;
    private int mSelectedServiceIndex;
    private ItemsSelectionView mMainServiceView;
    private String filteredTags = "yes_provided, standard";

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
        setTitle(mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().getServiceName());
        activeBackButton();
        setUpData();
        setUpListeners();
        setUpMultipleExtraServices();
    }

    private void setUpMultipleExtraServices() {
        mMainServiceView = new ItemsSelectionView(true, mBinder.llServiceMain,
                R.id.llCleaning, R.id.llAirCon,
                R.id.llPlumbing, R.id.llElectrical);
        mMainServiceView.setOnClickListener(this);
        ViewUtils.setOnCheckedChangeListener(getMainView(), this,
                R.id.rgSupplies, R.id.rgTypeService);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.rbYes:
                filteredTags = "yes_provided";
                break;
            case R.id.rbNo:
                filteredTags = "no_provided";
                break;
            case R.id.rbService1:
                filteredTags = "standard";
                break;
            case R.id.rbService2:
                filteredTags = "chemical";
        }
        updateAdapterByFilter();
    }

    private void updateAdapterByFilter() {
        mServicesList = mServicesStep1ViewModel.getFilteredServices(filteredTags);
        if (mServicesList != null && mServicesList.size() > 0) {
            mBinder.lvServices.setAdapter(new ServicesAdapter(this, mServicesList));
            mBinder.lvServices.setOnItemClickListener(this);
        } else {
            mBinder.lvServices.setVisibility(View.GONE);
        }
    }

    private void setUpData() {
        mServicesStep1ViewModel.setTypeServices(mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().getType());
        updateAdapterByFilter();
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
    private void pressNext() {
        boolean selected = checkIfServiceSelected();
        if (selected) {
            goToStep2();
            updateServiceOrderInfo();
        } else AlertManager.getInstance(this).showLongToast(getString(R.string.please_choose_service));
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnNext:
                pressNext();
                break;
            case R.id.btSeeDetailService:
                if (mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().getSelectedService() != null)
                    viewDetailService();
                else AlertManager.getInstance(this).showLongToast(getString(R.string.please_choose_service));
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
        int type = mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().getType();
        switch (type) {
            case ServiceOrderUserInput.SERVICE_AIR_CON_CLEANING:
                AirConOptionView airConView = new AirConOptionView(mBinder.llServiceAirConCleaning);
                mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().setArrayAirConOpts(airConView.getResultStrings());
                mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().setNumberOfAirCons(airConView.getNumberOfAirCons());
                break;
            case ServiceOrderUserInput.SERVICE_HOME_CLEANING:
                HomeCleaningOptionView homeView = new HomeCleaningOptionView(mBinder.llServiceHomeCleaning);
                mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().setArrayHomeCleaningOpts(homeView.getResultStrings());
                break;
        }
        mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().setServiceMain(mMainServiceView.getSelectedStringValue());
    }

    private boolean checkIfServiceSelected() {
        AppyService data = mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().getSelectedService();
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
        AppyService data = mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().getSelectedService();
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
            mServicesStep1ViewModel.getDataManager().getServiceOrderUserInput().setSelectedService(mServicesList.get(position));
            view.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        }
    }

    @Override
    public void viewOurFAQ() {
        AlertManager.getInstance(this).openInformationBrowser(getString(R.string.booking_ordering_faq),
                ApiUrlConfig.URL_OUR_FAQ);
    }

    @Override
    public void viewOurTANDC() {
        AlertManager.getInstance(this).openInformationBrowser(getString(R.string.our_booking_tc),
                ApiUrlConfig.URL_TERMS_CONDITIONS);
    }

    @Override
    public void viewSuppliesMoreInformation() {
        AlertManager.getInstance(this).openInformationBrowser(getString(R.string.cleaning_supplies),
                ApiUrlConfig.URL_CLEANING_SUPPLIES);
    }

    public void viewAirConTypeMoreInformation() {
        AlertManager.getInstance(this).openInformationBrowser(getString(R.string.aircon_type_service),
                ApiUrlConfig.URL_AIR_CON_TYPE_INFO);
    }

    public void viewAirConSizeMoreInformation() {
        AlertManager.getInstance(this).openInformationBrowser(getString(R.string.aircon_size),
                ApiUrlConfig.URL_AIR_CON_SIZE_INFO);
    }
}
