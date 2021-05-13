package web.model;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class ChiTieuKH {
	private int id;
	private double soTien;
	private String ten;

	private TaiKhoan tk;
}
