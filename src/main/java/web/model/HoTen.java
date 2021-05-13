package web.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class HoTen {
	private int id;
	private String ho;
	private String dem;
	private String ten;

	private TaiKhoan tk;

}
