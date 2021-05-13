package web.model;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class RuiRo {
	private int id;
	private double chiPhi;
	private String phuongThuc;
	private String chiTiet;

}
