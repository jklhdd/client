package web.dto;

public class GiaoDichDto {
    // Nếu phương thức là gửi tiền vào tk > lưu mã thẻ vào tài khoản nhận
    // Nếu phương thức là rút tiền > lưu mã thẻ vào tk gửi
    // Nếu là trả nợ > mã thẻ vào tk gửi

    private String mathe_gui;
    private String mathe_nhan;
    private double soTien;
    private String phuongThuc;

    public GiaoDichDto() {
    }

    public String getMathe_gui() {
        return mathe_gui;
    }

    public void setMathe_gui(String mathe_gui) {
        this.mathe_gui = mathe_gui;
    }

    public String getMathe_nhan() {
        return mathe_nhan;
    }

    public void setMathe_nhan(String mathe_nhan) {
        this.mathe_nhan = mathe_nhan;
    }

    public double getSoTien() {
        return soTien;
    }

    public void setSoTien(double soTien) {
        this.soTien = soTien;
    }

    public String getPhuongThuc() {
        return phuongThuc;
    }

    public void setPhuongThuc(String phuongThuc) {
        this.phuongThuc = phuongThuc;
    }

    @Override
    public String toString() {
        return "GiaoDichDto{" +
                "mathe_gui='" + mathe_gui + '\'' +
                ", mathe_nhan='" + mathe_nhan + '\'' +
                ", soTien=" + soTien +
                ", phuongThuc='" + phuongThuc + '\'' +
                '}';
    }
}
