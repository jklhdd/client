package web.model;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class Vay {
	private int id;
	private double khoanVay;
	private Date ngayVay;
	private float laiSuat;
	private int status;

	private TaiKhoan tk;

	public Date getNgayVay() {
		return ngayVay;
	}

	public void setNgayVay(Date ngayVay) {
		this.ngayVay = ngayVay;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public double getKhoanVay() {
		return khoanVay;
	}

	public void setKhoanVay(double khoanVay) {
		this.khoanVay = khoanVay;
	}

	public float getLaiSuat() {
		return laiSuat;
	}

	public void setLaiSuat(float laiSuat) {
		this.laiSuat = laiSuat;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public TaiKhoan getTk() {
		return tk;
	}

	public void setTk(TaiKhoan tk) {
		this.tk = tk;
	}
}
