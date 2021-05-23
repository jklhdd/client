package web.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class TheTinDung {
	private int id;
	private  String maThe;
	private int soNgayVay;
	private double tonNo;
	private double phiDuyTri;
	private Date ngayVay;
	private int status;

	private TaiKhoan tk;

	private GoiTinDung gtd;

	public GoiTinDung getGtd() {
		return gtd;
	}

	public void setGtd(GoiTinDung gtd) {
		this.gtd = gtd;
	}
	
}
