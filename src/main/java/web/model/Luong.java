package web.model;
import java.sql.Date;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class Luong {
	private int id;
	private double luong;
	private int nvid;
	private double thuong;
	private double phat;

	private TaiKhoan tk;
}
