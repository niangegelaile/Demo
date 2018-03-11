package com.ange.demo.http;

import java.util.List;

/**
 * Created by niangegelaile on 2018/3/11.
 */

public class AdvertResponse {
    private boolean status;
    private String code;
    /**
     * id : 1
     * title : 测试
     * picUrl : 无图
     * validDate : 1464579192000
     * serialNumber : 1
     * lastDate : 1464751507000
     * jumpPage : www.163.com
     * createDate : 1464751504000
     * invalidDate : 1467257595000
     */

    private List<ResultEntity> result;

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setResult(List<ResultEntity> result) {
        this.result = result;
    }



    public String getCode() {
        return code;
    }

    public List<ResultEntity> getResult() {
        return result;
    }

    public static class ResultEntity {
        private int id;
        private String title;
        private String picUrl;
        private long validDate;
        private int serialNumber;
        private long lastDate;
        private String jumpPage;
        private long createDate;
        private long invalidDate;
        private String typeName;
        private String typeManager;
        private Integer typeId;

        public void setId(int id) {
            this.id = id;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setPicUrl(String picUrl) {
            this.picUrl = picUrl;
        }

        public void setValidDate(long validDate) {
            this.validDate = validDate;
        }

        public void setSerialNumber(int serialNumber) {
            this.serialNumber = serialNumber;
        }

        public void setLastDate(long lastDate) {
            this.lastDate = lastDate;
        }

        public void setJumpPage(String jumpPage) {
            this.jumpPage = jumpPage;
        }

        public void setCreateDate(long createDate) {
            this.createDate = createDate;
        }

        public void setInvalidDate(long invalidDate) {
            this.invalidDate = invalidDate;
        }

        public int getId() {
            return id;
        }

        public String getTitle() {
            return title;
        }

        public String getPicUrl() {
            return picUrl;
        }

        public long getValidDate() {
            return validDate;
        }

        public int getSerialNumber() {
            return serialNumber;
        }

        public long getLastDate() {
            return lastDate;
        }

        public String getJumpPage() {
            return jumpPage;
        }

        public long getCreateDate() {
            return createDate;
        }

        public long getInvalidDate() {
            return invalidDate;
        }

        public String getTypeName() {
            return typeName;
        }

        public void setTypeName(String typeName) {
            this.typeName = typeName;
        }

        public String getTypeManager() {
            return typeManager;
        }

        public void setTypeManager(String typeManager) {
            this.typeManager = typeManager;
        }

        public Integer getTypeId() {
            return typeId;
        }

        public void setTypeId(Integer typeId) {
            this.typeId = typeId;
        }
    }
}
