package controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import application.Main;
import dao.QuanLyAccount;
import dao.QuanLyNhanVien;
import entities.Account;
import entities.NhanVien;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
public class DoiMatKhau implements Initializable{
	@FXML Label lblUsername;
	@FXML JFXPasswordField txtPassOld;
	@FXML JFXPasswordField txtPassNew;
	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}
	public void thongBaoKieuLoi(ActionEvent e, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.initOwner(((Node) (e.getSource())).getScene().getWindow());
		alert.showAndWait();
	}
	private double x, y;
	@FXML
	private void draged(MouseEvent event) {
		Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
		stage.setX(event.getScreenX() - x);
		stage.setY(event.getScreenY() - y);
	}

	@FXML
	private void pressed(MouseEvent event) {
		x = event.getSceneX();
		y = event.getSceneY();
	}
	public boolean kiemTraPasswordNew(ActionEvent e,String text) {
		String kiemTraText=text.trim();
		if(kiemTraText.isEmpty()==false) {
			if(kiemTraText.length()>=6) {
				if(kiemTraText.matches("((?=.*[A-Z])(?=.*[a-z])(?=.*[0-9])(?=.*[@#%])).{1,}")) {
					return true;
				}else {
					thongBaoKieuLoi(e, "yêu cầu mật khẩu mới có [A-Z] và [a-z] và [0-9] và @#%");
					return false;
				}
			}else {
				thongBaoKieuLoi(e, "Yêu cầu mật khẩu mới tối thiểu 6 ký tự");
				return false;
			}
		}else {
			thongBaoKieuLoi(e, "Mật khẩu mới chưa nhập");
			return false;
		}

	}
	public boolean kiemTraPasswordCu(ActionEvent e,String text) {
		String kiemTraText=text.trim();
		if(kiemTraText.isEmpty()==false) {
			return true;
		}else {
			thongBaoKieuLoi(e, "Mật khẩu cũ chưa nhập");
			return false;
		}

	}
	public void btnThayDoi(ActionEvent e) {
		try {
			boolean result=true;
			String passOld=txtPassOld.getText().toString();
			String passNew=txtPassNew.getText().toString();

			if(kiemTraPasswordCu(e, passOld)==false) {
				result=false;
				txtPassOld.requestFocus();
			}

			if(result==true) {
				if(kiemTraPasswordNew(e, passNew)==false) {
					result=false;
					txtPassNew.requestFocus();
				}
			}

			if(result==true) {
				NhanVien nv=QuanLyNhanVien.timMa2(lblUsername.getText().toString());
				Account acc=QuanLyAccount.timMa(nv.getAccount().getUserName());

				if(acc.getPassword().equals(passOld)) {
					if(passNew.isEmpty()==false) {
						Account acc2=new Account(lblUsername.getText().toString(), passNew, nv.getChucVu());
						if(QuanLyAccount.suaAcc(acc2)==true) {
							thongBaoKieuLoi(e, "Sửa thành công");
							((Node)(e.getSource())).getScene().getWindow().hide();  
						}else{
							thongBaoKieuLoi(e, "Sửa không thành công");
						};

					}else {
						thongBaoKieuLoi(e, "không được để rỗng");
						txtPassNew.requestFocus();
					}
				}else {
					thongBaoKieuLoi(e, "mật khẩu cũ không chính xác");
				}
			}


		} catch (Exception e2) {
			// TODO: handle exception
			System.out.println(e2.getMessage());
		}

	}
	public void btnXoaRong(ActionEvent e) {
		txtPassOld.setText("");
		txtPassNew.setText("");
	}
	public void thietLapTenNguoiDung(String userName) {
		lblUsername.setText(userName);
	}
	public void btnCLoseWindow(ActionEvent e) throws IOException {
		((Node)(e.getSource())).getScene().getWindow().hide();  
	}

}
