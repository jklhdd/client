package web.model;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class GiaoDich {
	private int id;
	private String diaChi;
	private double soTien;
	private String phuongThuc;

	private TaiKhoan tk;

}
