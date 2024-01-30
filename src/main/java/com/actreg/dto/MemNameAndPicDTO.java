package com.actreg.dto;

public class MemNameAndPicDTO {
    private String memName;
    private byte[] memPic;

    private Integer memId;

    public String getMemName() {
        return memName;
    }

    public void setMemName(String MemName) {
        this.memName = MemName;
    }

    public byte[] getMemPic() {
        return memPic;
    }

    public void setMemPic(byte[] memPic) {
        this.memPic = memPic;
    }

    public Integer getMemId() {
        return memId;
    }

    public void setMemId(Integer memId) {
        this.memId = memId;
    }
}
