package web.model;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class DauTu {
	private int id;
	private String coPhieu;
	private double gia;
	private int soLuong;

}
