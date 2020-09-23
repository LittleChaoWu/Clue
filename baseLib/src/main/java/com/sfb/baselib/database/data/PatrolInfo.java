package com.sfb.baselib.database.data;


import com.sfb.baselib.data.base.BaseBean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

@Entity(nameInDb = "patrol_table")
public class PatrolInfo extends BaseBean {

    public static final int PATROL_UNSTART = 0;
    public static final int PATROL_ONGOING = 1;
    public static final int PATROL_COMPLETE = 2;
//    public static final int PATROL_TIMEUP = 3;
//    public static final int PATROL_CANCEL = 4;

    @Id
    @Property(nameInDb = "_id")
    private Long id;
    @Property(nameInDb = "username")
    private String username;
    @Property(nameInDb = "patrol_task_id")
    private int taskId;
    @Property(nameInDb = "mileage")
    private long mileage;
    @Property(nameInDb = "locations")
    private String locations;
    @Property(nameInDb = "category")
    private String category;
    @Property(nameInDb = "subcategory")
    private String subcategory;
    @Property(nameInDb = "status")
    private int status;
    @Property(nameInDb = "dead_patrol_time")
    private long deadPatrolTime;
    @Property(nameInDb = "save_time")
    private long save_time;
    @Property(nameInDb = "column1")
    private String column1;
    @Property(nameInDb = "column2")
    private long column2;
    @Property(nameInDb = "column3")
    private long column3;
    @Property(nameInDb = "column4")
    private long column4;

    @Generated(hash = 1122266203)
    public PatrolInfo(Long id, String username, int taskId, long mileage,
                      String locations, String category, String subcategory, int status,
                      long deadPatrolTime, long save_time, String column1, long column2,
                      long column3, long column4) {
        this.id = id;
        this.username = username;
        this.taskId = taskId;
        this.mileage = mileage;
        this.locations = locations;
        this.category = category;
        this.subcategory = subcategory;
        this.status = status;
        this.deadPatrolTime = deadPatrolTime;
        this.save_time = save_time;
        this.column1 = column1;
        this.column2 = column2;
        this.column3 = column3;
        this.column4 = column4;
    }

    @Generated(hash = 1573661729)
    public PatrolInfo() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getTaskId() {
        return this.taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }

    public long getMileage() {
        return this.mileage;
    }

    public void setMileage(long mileage) {
        this.mileage = mileage;
    }

    public String getLocations() {
        return this.locations;
    }

    public void setLocations(String locations) {
        this.locations = locations;
    }

    public String getCategory() {
        return this.category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return this.subcategory;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public int getStatus() {
        return this.status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public long getDeadPatrolTime() {
        return this.deadPatrolTime;
    }

    public void setDeadPatrolTime(long deadPatrolTime) {
        this.deadPatrolTime = deadPatrolTime;
    }

    public long getSave_time() {
        return this.save_time;
    }

    public void setSave_time(long save_time) {
        this.save_time = save_time;
    }

    public String getColumn1() {
        return this.column1;
    }

    public void setColumn1(String column1) {
        this.column1 = column1;
    }

    public long getColumn2() {
        return this.column2;
    }

    public void setColumn2(long column2) {
        this.column2 = column2;
    }

    public long getColumn3() {
        return this.column3;
    }

    public void setColumn3(long column3) {
        this.column3 = column3;
    }

    public long getColumn4() {
        return this.column4;
    }

    public void setColumn4(long column4) {
        this.column4 = column4;
    }
}
