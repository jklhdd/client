package web.dto;

import java.io.Serializable;

public class ThanhVien implements Serializable {
    private int id;
    private String taikhoan;
    private String chucvu;
    private String ho;
    private String dem;
    private String ten;
    private String sdt;
    private String cmt;

    public ThanhVien(int id, String taikhoan, String chucvu, String ho, String dem, String ten, String sdt, String cmt) {
        this.id = id;
        this.taikhoan = taikhoan;
        this.chucvu = chucvu;
        this.ho = ho;
        this.dem = dem;
        this.ten = ten;
        this.sdt = sdt;
        this.cmt = cmt;
    }

    public ThanhVien() {
		// TODO Auto-generated constructor stub
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTaikhoan() {
        return taikhoan;
    }

    public void setTaikhoan(String taikhoan) {
        this.taikhoan = taikhoan;
    }

    public String getHo() {
        return ho;
    }

    public void setHo(String ho) {
        this.ho = ho;
    }

    public String getDem() {
        return dem;
    }

    public void setDem(String dem) {
        this.dem = dem;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getCmt() {
        return cmt;
    }

    public void setCmt(String cmt) {
        this.cmt = cmt;
    }

    public String getChucvu() {
        return chucvu;
    }

    public void setChucvu(String chucvu) {
        this.chucvu = chucvu;
    }
}
