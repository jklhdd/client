package web.model;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class SoTietKiem {
	private int id;
	private String maSo;
	private double soTien;
	private Date ngayMo;
	private int kyHan;
	private float laiSuat;
	private int soNgayGui;
	private int status;

	private TaiKhoan tk;

}
