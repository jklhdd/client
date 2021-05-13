package web.model;
import java.sql.Date;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class TheNganHang {
	private int id;
	private String maThe;
	private double soDu;
	private Date ngayMo;
	private double phiDuyTri;
	private int status;

	private TaiKhoan tk;


}
