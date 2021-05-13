package web.model;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class TaiKhoan {
	private int id;
	private String taiKhoan;
	private String matKhau;
	private String chucVu;

}
