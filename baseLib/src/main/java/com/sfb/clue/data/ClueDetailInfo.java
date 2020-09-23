package com.sfb.clue.data;

import com.sfb.baselib.data.FileModel;
import com.sfb.baselib.data.base.BaseBean;
import com.sfb.baselib.data.ClueInfo;

import java.util.List;

public class ClueDetailInfo extends BaseBean {

    private boolean isDealed;//本单位是否已处置过
    private boolean hasDealedPower;//是否有处置权限
    private boolean hasAssign;//是否有分发权限（决定是否有分发按钮）
    private boolean existsDealOrAssign;//是否执行过分发/核查
    private List<ClueStatusInfo> clue_status_list;
    private List<FileModel> file_model_list;
    private ClueInfo clue;
    private List<ClueCheckInfo> clueStatusList;
    private ClueCheckInfo receiveClueStatus;
    private List<FileModel> feedBackFileModelList;
    private ClueCheckInfo feedBackClueStatus;

    public boolean isDealed() {
        return isDealed;
    }

    public void setDealed(boolean dealed) {
        isDealed = dealed;
    }

    public boolean isHasDealedPower() {
        return hasDealedPower;
    }

    public void setHasDealedPower(boolean hasDealedPower) {
        this.hasDealedPower = hasDealedPower;
    }

    public boolean isHasAssign() {
        return hasAssign;
    }

    public void setHasAssign(boolean hasAssign) {
        this.hasAssign = hasAssign;
    }

    public boolean isExistsDealOrAssign() {
        return existsDealOrAssign;
    }

    public void setExistsDealOrAssign(boolean existsDealOrAssign) {
        this.existsDealOrAssign = existsDealOrAssign;
    }

    public List<ClueStatusInfo> getClue_status_list() {
        return clue_status_list;
    }

    public void setClue_status_list(List<ClueStatusInfo> clue_status_list) {
        this.clue_status_list = clue_status_list;
    }

    public List<FileModel> getFile_model_list() {
        return file_model_list;
    }

    public void setFile_model_list(List<FileModel> file_model_list) {
        this.file_model_list = file_model_list;
    }

    public ClueInfo getClue() {
        return clue;
    }

    public void setClue(ClueInfo clue) {
        this.clue = clue;
    }

    public List<ClueCheckInfo> getClueStatusList() {
        return clueStatusList;
    }

    public void setClueStatusList(List<ClueCheckInfo> clueStatusList) {
        this.clueStatusList = clueStatusList;
    }

    public ClueCheckInfo getReceiveClueStatus() {
        return receiveClueStatus;
    }

    public void setReceiveClueStatus(ClueCheckInfo receiveClueStatus) {
        this.receiveClueStatus = receiveClueStatus;
    }

    public List<FileModel> getFeedBackFileModelList() {
        return feedBackFileModelList;
    }

    public void setFeedBackFileModelList(List<FileModel> feedBackFileModelList) {
        this.feedBackFileModelList = feedBackFileModelList;
    }

    public ClueCheckInfo getFeedBackClueStatus() {
        return feedBackClueStatus;
    }

    public void setFeedBackClueStatus(ClueCheckInfo feedBackClueStatus) {
        this.feedBackClueStatus = feedBackClueStatus;
    }
}
