package web.model;


import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class HangMua {
	private int id;
	private double tien;
	private String tenMatHang;

}
