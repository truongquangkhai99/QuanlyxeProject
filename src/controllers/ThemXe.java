package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import application.Main;
import dao.QuanLyLoaiXe;
import dao.QuanLyXe;
import entities.Loaixe;
import entities.Xe;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.scene.control.Alert;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
public class ThemXe implements Initializable{
	private double xOffset = 0;
	private double yOffset = 0;
	@FXML public BorderPane mainBd;
	@FXML JFXTextField txtMaXe;
	@FXML JFXTextField txtDonViTinh;
	@FXML JFXTextField txtMoTa;
	@FXML JFXTextField txtNhaSX;
	@FXML JFXTextField txtSL;
	@FXML JFXTextField txtDG;
	@FXML ChoiceBox<String> choiceBoxTenXe = new ChoiceBox<String>(); 
	@FXML ChoiceBox<String> choiceBoxLoaiXe = new ChoiceBox<String>(); 
	@FXML ChoiceBox<String> choiceBoxMauXe = new ChoiceBox<String>(); 
	@FXML JFXTextField txtThongTinBaoHanh;
	@FXML Label lblTitle;
	@FXML ImageView img;
	@FXML JFXButton btnOK;
	@FXML JFXButton btnXoa;
	@FXML JFXButton btnXoaRong;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		makeStageDrageable();
		List<String> lx=QuanLyLoaiXe.showLoaiXe();
		lx.forEach(t->{
			choiceBoxLoaiXe.getItems().add(t);
		});
		List<String> lxTenXe=new ArrayList<String>();
		List<String> lxMauXe=new ArrayList<String>();
		//loaixe
		choiceBoxLoaiXe.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 
			public void changed(ObservableValue ov, Number value, Number new_value) 
			{ 
				choiceBoxTenXe.getItems().clear();
				choiceBoxMauXe.getItems().clear();
				lxMauXe.clear();
				lxTenXe.clear();
				List<String> ListTenXe=QuanLyLoaiXe.showTenXe(lx.get((int)new_value));
				ListTenXe.forEach(t->{
					choiceBoxTenXe.getItems().add(t);
					//set ngoài
					lxTenXe.add(t);
				});
				choiceBoxTenXe.setValue(ListTenXe.get(0).toString());
				List<String> listMauXe=QuanLyLoaiXe.showMauXeCuaTenXe(lx.get((int) new_value),choiceBoxTenXe.getValue().toString());
				listMauXe.forEach(t->{
					choiceBoxMauXe.getItems().add(t);
					//set ngoài 
					lxMauXe.add(t);
				});
				choiceBoxMauXe.setValue(listMauXe.get(0).toString());
				List<String> listHinhAnhXe=QuanLyLoaiXe.showHinhAnhCuaXe(lx.get((int) new_value), choiceBoxTenXe.getValue().toString(), choiceBoxMauXe.getValue().toString());;
				System.out.println(listHinhAnhXe.get(0).toString());
				File currentDirFile = new File("");
				String helper = currentDirFile.getAbsolutePath();
				String begin=kiemTraChuoi(helper);
				System.out.println("file:///"+begin+"/"+listHinhAnhXe.get(0).toString());
				Image image = new Image("file:///"+begin+"/src/"+listHinhAnhXe.get(0).toString());
				img.setImage(image);
			}
		});
		//tenxe
		choiceBoxTenXe.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 
			public void changed(ObservableValue ov, Number value, Number new_value) 
			{ 
				lxMauXe.clear();
				choiceBoxMauXe.getItems().clear();
				List<String> listMauXe=QuanLyLoaiXe.showMauXeCuaTenXe(choiceBoxLoaiXe.getValue(), lxTenXe.get((int)new_value));
				listMauXe.forEach(t->{
					choiceBoxMauXe.getItems().add(t);
				});
				choiceBoxMauXe.setValue(listMauXe.get(0).toString());
				List<String> listHinhAnhXe=QuanLyLoaiXe.showHinhAnhCuaXe(choiceBoxLoaiXe.getValue(), lxTenXe.get((int)new_value), choiceBoxMauXe.getValue().toString());
				System.out.println(listHinhAnhXe.get(0).toString());
				File currentDirFile = new File("");
				String helper = currentDirFile.getAbsolutePath();
				String begin=kiemTraChuoi(helper);
				System.out.println("file:///"+begin+"/"+listHinhAnhXe.get(0).toString());
				Image image = new Image("file:///"+begin+"/src/"+listHinhAnhXe.get(0).toString());
				img.setImage(image);
			}
		});
		//mauxe
		choiceBoxMauXe.getSelectionModel().selectedIndexProperty().addListener(new ChangeListener<Number>() { 
			public void changed(ObservableValue ov, Number value, Number new_value) 
			{ 

				List<String> listHinhAnhXe=QuanLyLoaiXe.showHinhAnhCuaXe(choiceBoxLoaiXe.getValue(), choiceBoxTenXe.getValue(), lxMauXe.get((int)new_value));
				System.out.println(listHinhAnhXe.get(0).toString());
				File currentDirFile = new File("");
				String helper = currentDirFile.getAbsolutePath();
				String begin=kiemTraChuoi(helper);
				System.out.println("file:///"+begin+"/"+listHinhAnhXe.get(0).toString());
				Image image = new Image("file:///"+begin+"/src/"+listHinhAnhXe.get(0).toString());
				img.setImage(image);
			}
		});
	}
	private static void khoiTaoChoiceBox(ActionEvent e) {

	}
	private static String kiemTraChuoi(String text) {
		String newTextResult="";
		for(int i=0;i<=text.length()-1;i++) {
			if((int)text.charAt(i)==92) {
				newTextResult+="/";
			}else {
				newTextResult+=text.charAt(i);
			}
		}
		return newTextResult;
	}
	public void btnCLoseWindow(ActionEvent e) throws IOException {
		((Node)(e.getSource())).getScene().getWindow().hide();  
	}
	public void thongBaoKieuLoi(ActionEvent e, String text) {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Information Dialog");
		alert.setHeaderText(null);
		alert.setContentText(text);
		alert.initOwner(((Node) (e.getSource())).getScene().getWindow());
		alert.showAndWait();
	}
	String fileHinhCapNhap="";
	String fileHinh="";
	public void btnChonHinh(ActionEvent e) {
		FileChooser fc = new FileChooser();
		fc.getExtensionFilters().add(new ExtensionFilter("PNG Files", "*.PNG"));
		List<File> f=fc.showOpenMultipleDialog(null);
		for(File file:f) {
			fileHinh=file.getAbsolutePath();
			Image image = new Image("file:///"+file.getAbsolutePath());
			img.setImage(image);
		}
	}
	private static void copyFileUsingStream(File source, File dest) throws IOException {
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		}finally {
			is.close();
			os.close();
		}
	}
	public boolean kiemTraMaXe(ActionEvent e,String text) {
		String textSearch=text.trim();
		if(textSearch.isEmpty()==false) {
			if(text.matches("^HX[0-9]+")==true) {
				return true;
			}else {
				thongBaoKieuLoi(e, "Mã xe không hợp lệ, yêu cầu kí tự đầu phải có HX,sau là ký số, vd:H0001");
			}
		}else {
			thongBaoKieuLoi(e, "Mã xe không được để trống");
		}
		return false;
	}
	public boolean kiemTraDonViTinh(ActionEvent e,String ma) {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			if(MaKT.matches("^[A-Za-z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ]+$")==true) {
				return true;
			}else {
				thongBaoKieuLoi(e, "Đơn vị tính không được nhập ký tự đặc biệt");
				return false;
			}
		}else {
			thongBaoKieuLoi(e, "Đơn vị tính không được để trống");
			return false;
		}
	}
	public boolean kiemTraMoTa(ActionEvent e,String ma) {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			if(MaKT.matches("^[A-Za-z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ]+$")==true) {
				return true;
			}else {
				thongBaoKieuLoi(e, "Mô tả tính không được nhập ký tự đặc biệt");
				return false;
			}
		}else {
			thongBaoKieuLoi(e, "Mô tả không được để trống");
			return false;
		}
	}
	public boolean kiemTraNhaSanXuat(ActionEvent e,String ma) {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			if(MaKT.matches("^[A-Za-z\\sÀÁÂÃÈÉÊÌÍÒÓÔÕÙÚÝàáâãèéêìíòóôõùúýĂăĐđĨĩŨũƠơƯưẠ-ỹ]+$")==true) {
				return true;
			}else {
				thongBaoKieuLoi(e, "Nhà sản xuất không được nhập ký tự đặc biệt");
				return false;
			}
		}else {
			thongBaoKieuLoi(e, "Nhà sản xuất không được để trống");
			return false;
		}
	}
	public boolean kiemTraSoLuong(ActionEvent e,String ma) {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			if(MaKT.matches("^[0-9]+")==true) {
				return true;
			}else {
				thongBaoKieuLoi(e, "Số lượng chỉ nhập số");
				return false;
			}
		}else {
			thongBaoKieuLoi(e, "Số lượng không được để trống");
			return false;
		}
	}
	public boolean kiemTraThongTinBaoHang(ActionEvent e,String ma) {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			if(MaKT.matches("^[0-9]+")==true) {
				return true;
			}else {
				thongBaoKieuLoi(e, "Thông tin bảo hành chỉ nhập số");
				return false;
			}
		}else {
			thongBaoKieuLoi(e, "Thông tin bảo hành không được để trống");
			return false;
		}
	}
	public boolean kiemTraDonGia(ActionEvent e,String ma) {
		String MaKT=ma.trim();
		if(MaKT.isEmpty()==false) {
			if(MaKT.matches("^[0-9]+")==true) {
				return true;
			}else {
				thongBaoKieuLoi(e, "Đơn giá chỉ nhập số");
				return false;
			}
		}else {
			thongBaoKieuLoi(e, "Đơn giá không được để trống");
			return false;
		}
	}


	public void btnCLickOk(ActionEvent e) {
		String maXe=txtMaXe.getText().toString();
		String donViTinh=txtDonViTinh.getText().toString();
		String moTa=txtMoTa.getText().toString();
		String nhaSX=txtNhaSX.getText().toString();
		String tenXe=choiceBoxTenXe.getValue();
		String loaiXe=choiceBoxLoaiXe.getValue();
		String mauXe=choiceBoxMauXe.getValue();
		String thongTinBaoHanh=txtThongTinBaoHanh.getText().toString();
		boolean contunies=true;
		if(kiemTraMaXe(e, maXe)==false) {
			contunies=false;
			txtMaXe.requestFocus();
		}
		if(contunies==true) {
			if(kiemTraDonViTinh(e, donViTinh)==false) {
				contunies=false;
				txtDonViTinh.requestFocus();
			}
		}
		if(contunies==true) {
			if(kiemTraMoTa(e, moTa)==false) {
				contunies=false;
				txtMoTa.requestFocus();
			}
		}
		if(contunies==true) {
			if(kiemTraNhaSanXuat(e, nhaSX)==false) {
				contunies=false;
				txtNhaSX.requestFocus();
			}
		}
		if(contunies==true) {
			if(kiemTraSoLuong(e, txtSL.getText().toString())==false) {
				contunies=false;
				txtSL.requestFocus();
			}
		}
		if(contunies==true) {
			if(choiceBoxLoaiXe.getValue()==null) {
				contunies=false;
				choiceBoxLoaiXe.requestFocus();
				thongBaoKieuLoi(e, "Loại xe chưa chọn");
			}
		}
		if(contunies==true) {
			if(kiemTraThongTinBaoHang(e, thongTinBaoHanh)==false) {
				contunies=false;
				txtThongTinBaoHanh.requestFocus();
			}
		}
		if(contunies==true) {
			if(kiemTraDonGia(e, txtDG.getText().toString())==false) {
				contunies=false;
				txtDG.requestFocus();
			}
		}
		
		if(contunies==true) {
			int soLuong=1;
			try {
				soLuong=Integer.parseInt(txtSL.getText().toString());
			} catch (Exception e2) {
				// TODO: handle exception
				System.out.println(e2.getMessage());
			}
			double donGia=1;
			try {
				donGia=Double.parseDouble(txtDG.getText().toString());
			} catch (Exception e2) {
				// TODO: handle exception
			}
			if(maXe.isEmpty()==false
					&& donViTinh.isEmpty()==false
					&& moTa.isEmpty()==false 
					&& nhaSX.isEmpty()==false
					&& thongTinBaoHanh.isEmpty()==false 
					) {
				if(lblTitle.getText().toString().equals("Cập nhập xe")) {
					List<Loaixe> ListLx=QuanLyLoaiXe.timMaTraVeLoaiXe(loaiXe, tenXe, mauXe);
					Loaixe lx=ListLx.get(0);
					Xe xe1=new Xe(maXe, lx, donViTinh, moTa, thongTinBaoHanh+" tháng", soLuong, donGia);
					if(QuanLyXe.suaXe(xe1)==true) {
						((Node) (e.getSource())).getScene().getWindow().hide();
					}else {
						thongBaoKieuLoi(e, "sửa không thành công");
					}

				}else {
					List<Loaixe> ListLx=QuanLyLoaiXe.timMaTraVeLoaiXe(loaiXe, tenXe, mauXe);
					Loaixe lx=ListLx.get(0);
					Xe xe1=new Xe(maXe, lx, donViTinh, moTa, thongTinBaoHanh+" tháng", soLuong, donGia);
					if(QuanLyXe.themXe(xe1)==true) {
						((Node) (e.getSource())).getScene().getWindow().hide();
					}else {
						thongBaoKieuLoi(e, "thêm không thành công");
					}
				}
			}else {
				thongBaoKieuLoi(e, "yêu cầu nhập đầy đủ và hợp lệ");
			}
		}



	}
	private void makeStageDrageable() {
		mainBd.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		mainBd.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {

				Main.primaryStage.setX(event.getScreenX() - xOffset);
				Main.primaryStage.setY(event.getScreenY() - yOffset);
				Main.primaryStage.setOpacity(0.7f);
			}
		});
		mainBd.setOnDragDone((e) -> {
			Main.primaryStage.setOpacity(1.0f);
		});
		mainBd.setOnMouseReleased((e) -> {
			Main.primaryStage.setOpacity(1.0f);
		});

	}
	@FXML
	private void btnXoaRong(ActionEvent e) {
		//		..\image\honda-power-products-vector-logo-small.png
		txtMaXe.setText("");
		txtDonViTinh.setText("");
		txtMoTa.setText("");
		txtNhaSX.setText("");
		img.setImage(new Image("/image/honda-power-products-vector-logo-small.png"));
	}

}
