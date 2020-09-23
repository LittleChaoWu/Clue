package com.sfb.baselib.data;

import com.sfb.baselib.data.base.BaseBean;
import com.sfb.baselib.database.data.PatrolPointInfo;

import java.util.List;

public class PatrolLocationInfo extends BaseBean {

    int userCruiseId;
    int subTaskId;
    List<PatrolPointInfo> locations;
    long mileage;

    public void setUserCruiseId(int userCruiseId) {
        this.userCruiseId = userCruiseId;
    }

    public void setSubTaskId(int subTaskId) {
        this.subTaskId = subTaskId;
    }

    public void setLocations(List<PatrolPointInfo> locations) {
        this.locations = locations;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public int getUserCruiseId() {
        return userCruiseId;
    }

    public int getSubTaskId() {
        return subTaskId;
    }

    public List<PatrolPointInfo> getLocations() {
        return locations;
    }

    public long getMileage() {
        return mileage;
    }
}
