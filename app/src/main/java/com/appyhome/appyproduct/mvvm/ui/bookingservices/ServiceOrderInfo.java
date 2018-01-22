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
    private ArrayList<JSONObject> mArrayHomeCleaningServices;
    private ArrayList<JSONObject> mArrayAirConServices;
    private JSONObject mAirConServicing;
    private int mRoomNumber = 0;
    private boolean mIsFlexible = false;
    private JSONObject mSelectedService;
    private String mAdditionalInfo = "";
    private String mAddress = "";

    private String mTimeSlot1 = "";
    private String mTimeSlot2 = "";
    private String mTimeSlot3 = "";

    private String mExtraServices = "";

    private String mAppointmentId;

    private ServiceOrderInfo() {

    }

    public static ServiceOrderInfo getInstance() {
        if (mServiceOrderInfo == null)
            mServiceOrderInfo = new ServiceOrderInfo();
        return mServiceOrderInfo;
    }

    public JSONObject getServiceInfo() {
        return mServiceInfo;
    }

    public String getServiceName() {
        try {
            return mServiceInfo.getString("name");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return "";
    }

    public ArrayList<JSONObject> getServices() {
        try {
            ArrayList<JSONObject> array = (ArrayList<JSONObject>) mServiceInfo.get("services");
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

    public int getRoomNumber() {
        return mRoomNumber;
    }

    public void setRoomNumber(int number) {
        mRoomNumber = number;
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
                    service1.put("detail", "-Cleaning and checking indoor filter\n" +
                            "-Cleaning of AC evaporator coil\n" +
                            "-Flushing of drainage system\n" +
                            "-Checking ac fan bearing");

                    service2.put("name", "Chemical servicing hp1.0-1.5");
                    service2.put("price", "160");
                    service2.put("description", "");
                    service2.put("detail", "-Cleaning and checking indoor filter\n" +
                            "-Cleaning of AC evaporator coil\n" +
                            "-Flushing of drainage system\n" +
                            "-Checking ac fan bearing" + "-Cleaning blower wheels and blades \n" +
                            "-Checking thermostat and control\n" +
                            "-Internal unit chemical wash");

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
                            " \t     - Cleaning of all bathroom and toilets requested\n" +
                            "\t     - Cleaning of living room and balcony (if applicable) \n" +
                            "\t     - Cleaning including sweeping, mopping, dusting, general tidying and vacuuming \n" +
                            "\t     - Not valid for moving in/out cleaning or Post Renovation cleaning\n");

                    service2.put("name", "Standard Cleaning (Supplies Provided)");
                    service2.put("price", "105");
                    service2.put("description", "1 Cleaner 3 Hours");
                    service2.put("detail", "- Cleaning of all bedrooms requested\n" +
                            " \t     - Cleaning of all bathroom and toilets requested\n" +
                            "\t     - Cleaning of living room and balcony (if applicable) \n" +
                            "\t     - Cleaning including sweeping, mopping, dusting, general tidying and vacuuming \n" +
                            "\t     - Not valid for moving in/out cleaning or Post Renovation cleaning\n" +
                            "\t     - Standard Cleaning Supplies \n" +
                            "\t\t* Mop, Broom, Vacuum Cleaner, Multipurpose Chemicals and Detergents, Sponge and Brush, Wiping Cloth, Duster, Pail\n");

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

    public String getExtraServices() {
        return mExtraServices;
    }

    public void setExtraServices(String mExtraServices) {
        this.mExtraServices = mExtraServices;
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
            datetime.put("datetime1", getTimeSlot1());
            datetime.put("datetime2", getTimeSlot2());
            datetime.put("datetime3", getTimeSlot3());
            request.setDateTime(datetime.toString());

            JSONObject services = new JSONObject();
            services.put("service1", getSelectedService().getString("name"));
            services.put("service2", "");
            services.put("service3", "");
            request.setServices(services.toString());

            JSONObject additional = new JSONObject();
            additional.put("extra", getExtraServices().toString());
            additional.put("is_flexible", mIsFlexible + "");
            if (mType == SERVICE_HOME_CLEANING) {
                additional.put("room", getRoomNumber() + "");
            }
            request.setAdditional(additional.toString());

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return request;
    }
}
