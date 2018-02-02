package com.appyhome.appyproduct.mvvm.ui.bookingservices;


import com.appyhome.appyproduct.mvvm.data.model.api.service.AppointmentCreateRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ServiceOrderInfo {
    public static final int SERVICE_HOME_CLEANING = 0;
    public static final int SERVICE_AIR_CON_CLEANING = 1;
    private static ServiceOrderInfo mServiceOrderInfo;
    private int mType = SERVICE_HOME_CLEANING;
    private JSONObject mServiceInfo;
    private boolean mIsFlexible = false;
    private JSONObject mSelectedService;
    private String mAdditionalInfo = "";
    private String mAddress = "";

    private String mTimeSlot1 = "";
    private String mTimeSlot2 = "";
    private String mTimeSlot3 = "";

    private ArrayList<String> mServiceExtra;
    private ArrayList<String> mServiceMain;

    private String mAppointmentId;

    private ArrayList<String> mArrayHomeCleaningOpts;
    private ArrayList<String> mArrayAirConOpts;

    private String txn_ID;

    private int mNumberOfAirCons = 1;

    private ServiceOrderInfo() {

    }

    public static ServiceOrderInfo getInstance() {
        if (mServiceOrderInfo == null)
            mServiceOrderInfo = new ServiceOrderInfo();
        return mServiceOrderInfo;
    }

    public String getServiceName() {
        try {
            return getServiceInfo().getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<JSONObject> getServices() {
        try {
            ArrayList<JSONObject> array = (ArrayList<JSONObject>) getServiceInfo().get("services");
            return array;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public JSONObject getSelectedService() {
        return mSelectedService;
    }

    public void setSelectedService(JSONObject object) {
        mSelectedService = object;
    }

    public int getType() {
        return mType;
    }

    public void setType(int type) {
        this.mType = type;
    }

    public boolean isFlexible() {
        return mIsFlexible;
    }

    public void setFlexible(boolean mIsFlexible) {
        this.mIsFlexible = mIsFlexible;
    }

    private JSONObject getServiceInfo() {
        if(mServiceInfo == null) {
            setUpData(mType);
        }
        return mServiceInfo;
    }
    public void setUpData(int type) {
        mType = type;
        mServiceInfo = new JSONObject();
        JSONObject service1 = new JSONObject();
        JSONObject service2 = new JSONObject();
        JSONObject service3 = new JSONObject();
        try {
            switch (type) {
                case SERVICE_AIR_CON_CLEANING:
                    mServiceInfo.put("name", "Air-con servicing");
                    service1.put("name", "A standard hp1.0-1.5 1 unit");
                    service1.put("price", "120");
                    service1.put("description", "");
                    service1.put("detail", "Standard Aircon Cleaning\n" +
                            "- Cleaning and checking of indoor filter\n" +
                            "- Cleaning of evaporator coil\n" +
                            "- Flushing of drainage system\n" +
                            "- Checking of A/C fan bearing\n" +
                            "- Test-run of the system to ensure it is in proper working condition\n" +
                            "- Cleaning of blower wheels and blades\n" +
                            "- Checking of thermostats and control R22 gas top up at no additional char\n");

                    service2.put("name", "Chemical servicing hp1.0-1.5");
                    service2.put("price", "160");
                    service2.put("description", "");
                    service2.put("detail", "Chemical Aircon Cleaning \n" +
                            "- Cleaning and checking of indoor filter\n" +
                            "- Cleaning of evaporator coil\n" +
                            "- Flushing of drainage system\n" +
                            "- Checking of A/C fan bearing\n" +
                            "- Test-run of the system to ensure it is in proper working condition\n" +
                            "- Chemical Cleaning of external and internal unit\n" +
                            "- Cleaning of blower wheels and blades\n" +
                            "- Checking of thermostats and control R22 gas top up at no additional charge\n");

                    ArrayList<JSONObject> arrayList = new ArrayList<>();
                    arrayList.add(service1);
                    arrayList.add(service2);
                    mServiceInfo.put("services", arrayList);
                    break;
                case SERVICE_HOME_CLEANING:
                    mServiceInfo.put("name", "Home Cleaning");
                    service1.put("name", "Standard Cleaning (Supplies Not Provided)");
                    service1.put("price", "80");
                    service1.put("description", "1 Cleaner 3 Hours");
                    service1.put("detail", "- Cleaning of all bedrooms requested\n" +
                            "- Cleaning of all bathroom and toilets requested\n" +
                            "- Cleaning of living room and balcony (if applicable) \n" +
                            "- Cleaning including sweeping, mopping, dusting, general tidying and vacuuming \n" +
                            "- Not valid for moving in/out cleaning or Post Renovation cleaning\n");

                    service2.put("name", "Standard Cleaning (Supplies Provided)");
                    service2.put("price", "105");
                    service2.put("description", "1 Cleaner 3 Hours");
                    service2.put("detail", "- Cleaning of all bedrooms requested\n" +
                            "- Cleaning of all bathroom and toilets requested\n" +
                            "- Cleaning of living room and balcony (if applicable) \n" +
                            "- Cleaning including sweeping, mopping, dusting, general tidying and vacuuming \n" +
                            "- Not valid for moving in/out cleaning or Post Renovation cleaning\n" +
                            "- Standard Cleaning Supplies \n\n" +
                            "* Mop, Broom, Vacuum Cleaner, Multipurpose Chemicals and Detergents, Sponge and Brush, Wiping Cloth, Duster, Pail\n");

                    service3.put("name", "Moving in/out Cleaning");
                    service3.put("price", "210");
                    service3.put("description", "2 Cleaners 4 hours");
                    service3.put("detail", service2.get("detail"));

                    arrayList = new ArrayList<>();
                    arrayList.add(service1);
                    arrayList.add(service2);
                    arrayList.add(service3);
                    mServiceInfo.put("services", arrayList);
                    break;
            }
        } catch (Exception e) {

        }
    }

    public String getAdditionalInfo() {
        return mAdditionalInfo;
    }

    public void setAdditionalInfo(String mAdditionalInfo) {
        this.mAdditionalInfo = mAdditionalInfo;
    }

    public String getTimeSlot1() {
        return mTimeSlot1;
    }

    public void setTimeSlot1(String mTimeSlot1) {
        this.mTimeSlot1 = mTimeSlot1;
    }

    public String getTimeSlot2() {
        return mTimeSlot2;
    }

    public void setTimeSlot2(String mTimeSlot2) {
        this.mTimeSlot2 = mTimeSlot2;
    }

    public String getTimeSlot3() {
        return mTimeSlot3;
    }

    public void setTimeSlot3(String mTimeSlot3) {
        this.mTimeSlot3 = mTimeSlot3;
    }

    public String getAddress() {
        return mAddress;
    }

    public void setAddress(String mAddress) {
        this.mAddress = mAddress;
    }

    public ArrayList<String> getExtraServices() {
        return mServiceExtra;
    }

    public void setExtraServices(ArrayList<String> mExtraServices) {
        this.mServiceExtra = mExtraServices;
    }

    public String getAppointmentId() {
        return mAppointmentId;
    }

    public void setAppointmentId(String mAppointmentId) {
        this.mAppointmentId = mAppointmentId;
    }

    public AppointmentCreateRequest getAppointmentCreateRequest() {
        AppointmentCreateRequest request = new AppointmentCreateRequest();
        try {
            request.setIdNumber(getAppointmentId());
            request.setAddress(getAddress());

            JSONObject datetime = new JSONObject();
            String time1 = getTimeSlot1();
            if (time1 != null && time1.length() > 0)
                datetime.put("datetime1", time1);
            String time2 = getTimeSlot2();
            if (time2 != null && time2.length() > 0)
                datetime.put("datetime2", time2);
            String time3 = getTimeSlot3();
            if (time3 != null && time3.length() > 0)
                datetime.put("datetime3", time3);

            request.setDateTime(datetime.toString());

            JSONObject services = new JSONObject();
            String name = getServiceInfo().getString("name");
            String price = getTotalCost();
            if (mSelectedService != null) {
                name = name + " - " + mSelectedService.getString("name");
            }
            services.put("service1", name + "::" + price);
            request.setServices(services.toString());

            JSONObject additional = new JSONObject();

            ArrayList<String> additionalArrayOpts = new ArrayList<>();

            String extra1 = mIsFlexible ? "flexible::0" : "not_flexible::0";
            additionalArrayOpts.add(extra1);
            additionalArrayOpts.addAll(getPrice0Strings(mServiceExtra));

            if (mType == SERVICE_AIR_CON_CLEANING) {
                additionalArrayOpts.addAll(getPrice0Strings(mArrayAirConOpts));
            }
            if (mType == SERVICE_HOME_CLEANING) {
                additionalArrayOpts.addAll(getPrice0Strings(mArrayHomeCleaningOpts));
            }
            String additionalComment = "Additional details: " + mAdditionalInfo + "_" + txn_ID + "::0";
            additionalArrayOpts.add(additionalComment);
            for (int i = 0; i < additionalArrayOpts.size(); i++) {
                additional.put("extra" + i, additionalArrayOpts.get(i));
            }
            request.setAdditional(additional.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return request;
    }

    private ArrayList<String> getPrice0Strings(ArrayList<String> arrayList) {
        ArrayList<String> result = new ArrayList<>();
        for (String str : arrayList) {
            if (str != null && str.length() > 0) {
                result.add(str + "::0");
            }
        }
        return result;
    }

    public ArrayList<String> getServiceMain() {
        return mServiceMain;
    }

    public void setServiceMain(ArrayList<String> mServiceMain) {
        this.mServiceMain = mServiceMain;
    }

    public String getTotalCost() {
        try {
            Integer value = Integer.valueOf(getSelectedService().getString("price"));
            if (mType == SERVICE_AIR_CON_CLEANING) {
                value = value * mNumberOfAirCons;
            }
            return value.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<String> getArrayHomeCleaningOpts() {
        return mArrayHomeCleaningOpts;
    }

    public void setArrayHomeCleaningOpts(ArrayList<String> mArrayHomeCleaningOpts) {
        this.mArrayHomeCleaningOpts = mArrayHomeCleaningOpts;
    }

    public ArrayList<String> getArrayAirConOpts() {
        return mArrayAirConOpts;
    }

    public void setArrayAirConOpts(ArrayList<String> mArrayAirConOpts) {
        this.mArrayAirConOpts = mArrayAirConOpts;
    }

    public String getTxn_ID() {
        return txn_ID;
    }

    public void setTxn_ID(String txn_ID) {
        this.txn_ID = txn_ID;
    }

    public int getNumberOfAirCons() {
        return mNumberOfAirCons;
    }

    public void setNumberOfAirCons(int numberOfAirCons) {
        this.mNumberOfAirCons = numberOfAirCons;
    }
}
