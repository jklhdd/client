package web.model;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class DoanhSo {
	private int id;
	private double tien;
	private String mota;
	private Date ngayTao;

}
