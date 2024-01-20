import java.sql.Connection;
import java.sql.Date;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.time.LocalDate;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import java.sql.ResultSet;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.temporal.ChronoUnit;
import java.util.Optional;
import javafx.event.ActionEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.util.Callback;
public class ProjeYonetimiUygulamasi extends Application {
    private Stage primaryStage;
    private TableView<String> gorevTable;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        showLoginPage();
    }
    private void showLoginPage() {
        Stage loginStage = new Stage();
        loginStage.setTitle("Giriş");
        VBox loginLayout = new VBox(10);
        loginLayout.setPadding(new Insets(20, 20, 20, 20));
        loginLayout.setStyle("-fx-background-color: #D2E3C8; "
                + "-fx-font-weight: bold;"); 
        Label welcomeLabel = new Label("Yönetim Sistemine Hoşgeldiniz..");
        welcomeLabel.setStyle("-fx-text-fill: #1B5E20; -fx-font-size: 24; "
                + "-fx-font-weight: bold;");
        TextField usernameField = new TextField();
        usernameField.setStyle("-fx-text-fill: #86A789; -fx-font-weight: bold;");
        PasswordField passwordField = new PasswordField();
        Button loginButton = new Button("Giriş");
        loginButton.setStyle("-fx-background-color: #739072; "
                + "-fx-text-fill: #EBE3D5; -fx-font-weight: bold;");
        Button createAccountButton = new Button("Hesap Oluştur");
        createAccountButton.setStyle("-fx-background-color: #739072; "
                + "-fx-text-fill: #EBE3D5; -fx-font-weight: bold;");
        loginButton.setOnAction(e -> loginAction(usernameField.getText(),
                passwordField.getText()));
        createAccountButton.setOnAction(e -> showCreateAccountPage());
        loginLayout.getChildren().addAll(welcomeLabel,
                new Label("Kullanıcı Adı:"), usernameField,
                new Label("Şifre:"), passwordField, loginButton, createAccountButton);
        Scene loginScene = new Scene(loginLayout, 500, 300);
        loginStage.setScene(loginScene);
        loginStage.show();
    }

    private void showCreateAccountPage() {
        Stage createAccountStage = new Stage();
        createAccountStage.setTitle("Hesap Oluştur");
        VBox createAccountLayout = new VBox(10);
        createAccountLayout.setPadding(new Insets(20, 20, 20, 20));
        createAccountLayout.setStyle("-fx-background-color: #D2E3C8;");
        TextField nameField = new TextField();
        TextField surnameField = new TextField();
        TextField positionField = new TextField();
        TextField newUsernameField = new TextField();
        PasswordField newPasswordField = new PasswordField();
        Button createAccountButton = new Button("Oluştur");
        createAccountButton.setStyle("-fx-background-color: #86A789; -fx-text-fill:"
                + " #EEF0E5;-fx-font-weight: bold;");
        createAccountButton.setOnAction(e -> createAccountAction(
                nameField.getText(),
                surnameField.getText(),
                positionField.getText(),
                newUsernameField.getText(),
                newPasswordField.getText()));
        Label nameLabel = new Label("Ad:");
        nameLabel.setStyle("-fx-text-fill: #4F6F52; -fx-font-weight: bold;");
        Label surnameLabel = new Label("Soyad:");
        surnameLabel.setStyle("-fx-text-fill: #4F6F52; -fx-font-weight: bold;");
        Label positionLabel = new Label("Pozisyon:");
        positionLabel.setStyle("-fx-text-fill: #4F6F52; -fx-font-weight: bold;");
        Label usernameLabel = new Label("Kullanıcı Adı:");
        usernameLabel.setStyle("-fx-text-fill: #4F6F52; -fx-font-weight: bold;");
        Label passwordLabel = new Label("Şifre:");
        passwordLabel.setStyle("-fx-text-fill: #4F6F52; -fx-font-weight: bold;");
        createAccountLayout.getChildren().addAll(
                nameLabel, nameField,
                surnameLabel, surnameField,
                positionLabel, positionField,
                usernameLabel, newUsernameField,
                passwordLabel, newPasswordField,
                createAccountButton);
        Scene createAccountScene = new Scene(createAccountLayout, 600, 500);
        createAccountStage.setScene(createAccountScene);
        createAccountStage.show();
    }

    private void createAccountAction(String name, String surname, String position, String username, String password) {
        insertNewAccount(name, surname, position, username, password);
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Hesap Oluşturuldu");
        alert.setHeaderText(null);
        alert.setContentText("Yeni hesap oluşturuldu. Şimdi giriş yapabilirsiniz.");
        alert.showAndWait();
    }

    private void insertNewAccount(String name, String surname, String position, String username, String password) {
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "INSERT INTO kullanici (ad, soyad, pozisyon, kullanici_adi, sifre) VALUES (?, ?, ?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, name);
                preparedStatement.setString(2, surname);
                preparedStatement.setString(3, position);
                preparedStatement.setString(4, username);
                preparedStatement.setString(5, password);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loginAction(String username, String password) {
        if (login(username, password)) {
            showMainPage();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Giriş Hatası");
            alert.setHeaderText(null);
            alert.setContentText("Geçersiz kullanıcı adı veya şifre.");
            alert.showAndWait();
        }
    }

    public void showMainPage() {
        primaryStage.setTitle("Proje Yönetimi");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setStyle("-fx-background-color: #D2E3C8;");
        Label baslıkLabel = new Label("Girmek istediğiniz menüyü seçiniz:");
        baslıkLabel.setStyle("-fx-text-fill: #1B5E20; -fx-font-size: 20; "
                + "-fx-font-weight: bold;");

        // Projeleri Görüntüle butonu
        Button projelerButton = new Button("Projeler    ");
        projelerButton.setOnAction(e -> projeleriGoruntuleAction());
        projelerButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52;"
                + " -fx-font-size: 18pt;-fx-font-weight: bold;");
        // Çalışanlar butonu
        Button calisanlariGoruntuleButton = new Button("Çalışanlar   ");
        calisanlariGoruntuleButton.setOnAction(e -> calisanlariGoruntuleAction());
        calisanlariGoruntuleButton.setStyle("-fx-background-color: #FAFAFA; "
                + "-fx-text-fill: #4F6F52; -fx-font-size: 16pt;-fx-font-weight: bold;");
        //Görevler butonu-+
        Button gorevlerButton = new Button("Görevler   ");
        gorevlerButton.setOnAction(e -> gorevleriGoruntuleAction());
        gorevlerButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52;"
                + " -fx-font-size: 17pt;-fx-font-weight: bold;");
        layout.getChildren().addAll(baslıkLabel,projelerButton,
                calisanlariGoruntuleButton, gorevlerButton);
        Scene scene = new Scene(layout, 500, 300);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private boolean login(String username, String password) {
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT * FROM kullanici WHERE kullanici_adi = ? AND sifre = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    return resultSet.next();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void showMessage(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
      private ObservableList<CalisanTask> getAssignedTasks(Calisan calisan) {
        ObservableList<CalisanTask> assignedTasks = FXCollections.observableArrayList();
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT t.task_adi, p.proje_adi, p.durum AS proje_durumu "
                    + "FROM tasks t "
                    + "LEFT JOIN projeler p ON t.proje_adi = p.proje_adi "
                    + "WHERE t.assigned_employee = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, calisan.getAd());
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String taskName = resultSet.getString("task_adi");
                        String projectName = resultSet.getString("proje_adi");
                        String projectStatus = resultSet.getString("proje_durumu");

                        assignedTasks.add(new CalisanTask(taskName, projectName, projectStatus));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return assignedTasks;
    }

    private void calisanlariGoruntuleAction() {
        Stage calisanlarPenceresi = new Stage();
        calisanlarPenceresi.setTitle("Çalışanları Görüntüle");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setStyle("-fx-background-color: #D2E3C8;");

        TableView<Calisan> calisanTable = new TableView<>();
        TableColumn<Calisan, String> adColumn = new TableColumn<>("Ad");
        TableColumn<Calisan, String> soyadColumn = new TableColumn<>("Soyad");
        TableColumn<Calisan, String> pozisyonColumn = new TableColumn<>("Pozisyon");
        Pane tableBackgroundPane = new Pane(calisanTable);
        tableBackgroundPane.setStyle("-fx-background-color: #D2E3C8;");

        adColumn.setCellValueFactory(new PropertyValueFactory<>("ad"));
        soyadColumn.setCellValueFactory(new PropertyValueFactory<>("soyad"));
        pozisyonColumn.setCellValueFactory(new PropertyValueFactory<>("statu"));

        calisanTable.getColumns().addAll(adColumn, soyadColumn, pozisyonColumn);

        calisanTable.setItems(getCalisanListesi());
        TableColumn<Calisan, Void> detailColumn = new TableColumn<>("Detay");
        detailColumn.setCellFactory(new Callback<TableColumn<Calisan, Void>, TableCell<Calisan, Void>>() {
            @Override
            public TableCell<Calisan, Void> call(TableColumn<Calisan, Void> param) {
                return new TableCell<Calisan, Void>() {
                    private final Button detailButton = new Button("Detay");

                    {
                        detailButton.setOnAction(event -> {
                            Calisan calisan = getTableView().getItems().get(getIndex());
                            showCalisanDetails(calisan);
                        });
                    }

                    @Override
                    protected void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(detailButton);
                        }
                    }
                };
            }
        });

        calisanTable.getColumns().add(detailColumn);

        Button kapatButton = new Button("Kapat");
        kapatButton.setOnAction(e -> calisanlarPenceresi.close());
        Button silButton = new Button("Çalışan Sil");
        silButton.setOnAction(e -> calisanSilAction(calisanTable));
        Button ekleButton = new Button("Çalışan Ekle");
        ekleButton.setOnAction(e -> calisanEkleAction(calisanTable));
        Button guncelleButton = new Button("Çalışan Güncelle");
        guncelleButton.setOnAction(e -> calisanGuncelleAction(calisanTable));
        guncelleButton.setPrefWidth(200);
        kapatButton.setPrefWidth(200);
        silButton.setPrefWidth(200);
        ekleButton.setPrefWidth(200);

        kapatButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52; "
                + "-fx-font-size: 14pt;-fx-font-weight: bold;");
        silButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52;"
                + " -fx-font-size: 14pt;-fx-font-weight: bold;");
        ekleButton.setStyle("-fx-background-color: #FAFAFA; "
                + "-fx-text-fill: #4F6F52; -fx-font-size: 14pt;-fx-font-weight: bold;");
        guncelleButton.setStyle("-fx-background-color: #FAFAFA; -"
                + "fx-text-fill: #4F6F52; -fx-font-size: 14pt;-fx-font-weight: bold;");

        calisanTable.setPrefWidth(800);
        calisanTable.setPrefHeight(800);

        layout.getChildren().addAll(calisanTable, new Separator(), ekleButton, silButton,
                new Separator(), guncelleButton, new Separator(), kapatButton);
        Scene scene = new Scene(layout, 600, 800);
        calisanlarPenceresi.setScene(scene);

        calisanlarPenceresi.show();
    }

    private void calisanGuncelleAction(TableView<Calisan> calisanTable) {
        Calisan seciliCalisan = calisanTable.getSelectionModel().getSelectedItem();

        if (seciliCalisan != null) {
            // Create a TextInputDialog to get updated employee information
            TextInputDialog dialog = new TextInputDialog();
            dialog.setTitle("Çalışan Güncelle");
            dialog.setHeaderText(null);
            dialog.setContentText("Yeni Bilgileri Girin:\nAd Soyad Pozisyon");
            dialog.getEditor().setText(seciliCalisan.getAd() + " " + seciliCalisan.getSoyad() + " " + seciliCalisan.getStatu());
            // Set background color of the dialog
            DialogPane dialogPane = dialog.getDialogPane();
            dialogPane.setStyle("-fx-background-color: #D2E3C8;");

            // Show the dialog and wait for the user's input
            Optional<String> result = dialog.showAndWait();

            // Process the user's input
            result.ifPresent(updatedEmployeeInfo -> {
                String[] infoArray = updatedEmployeeInfo.split("\\s+");
                if (infoArray.length == 3) {
                    String yeniAd = infoArray[0];
                    String yeniSoyad = infoArray[1];
                    String yeniStatu = infoArray[2];

                    // Update the employee in the database
                    updateEmployeeInDatabase(seciliCalisan, yeniAd, yeniSoyad, yeniStatu);

                    // Update the TableView
                    seciliCalisan.setAd(yeniAd);
                    seciliCalisan.setSoyad(yeniSoyad);
                    seciliCalisan.setStatu(yeniStatu);

                    // Refresh the table view
                    calisanTable.refresh();
                } else {
                    showMessage("Hata", "Geçersiz giriş. Lütfen doğru bilgileri girin.");
                }
            });
        } else {
            showMessage("Uyarı", "Lütfen bir çalışan seçin.");
        }
    }

    private void updateEmployeeInDatabase(Calisan seciliCalisan, String yeniAd, String yeniSoyad, String yeniStatu) {
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "UPDATE calisanlar SET ad=?, soyad=?, statu=? WHERE ad=? AND soyad=? AND statu=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, yeniAd);
                preparedStatement.setString(2, yeniSoyad);
                preparedStatement.setString(3, yeniStatu);
                preparedStatement.setString(4, seciliCalisan.getAd());
                preparedStatement.setString(5, seciliCalisan.getSoyad());
                preparedStatement.setString(6, seciliCalisan.getStatu());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception according to your application's requirements
        }
    }

    private void showCalisanDetails(Calisan calisan) {
        Stage calisanDetayPenceresi = new Stage();
        calisanDetayPenceresi.setTitle("Çalışan Detayları");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setStyle("-fx-background-color: #D2E3C8;");
        ObservableList<CalisanTask> assignedTasks = getAssignedTasks(calisan);
        TableView<CalisanTask> tasksTableView = new TableView<>(assignedTasks);
        TableColumn<CalisanTask, String> taskNameColumn = new TableColumn<>("Görev Adı");
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("task_adi"));
        TableColumn<CalisanTask, String> projectNameColumn = new TableColumn<>("Proje Adı");
        projectNameColumn.setCellValueFactory(new PropertyValueFactory<>("proje_adi"));
        TableColumn<CalisanTask, String> projectStatusColumn = new TableColumn<>("Proje Durumu");
        projectStatusColumn.setCellValueFactory(new PropertyValueFactory<>("durum"));
        tasksTableView.getColumns().addAll(taskNameColumn, projectNameColumn, projectStatusColumn);
        long completedTasksCount = assignedTasks.stream()
                .filter(task -> "tamamlandı".equalsIgnoreCase(task.getDurum()))
                .count();

        Label completedTasksLabel = new Label("Tamamlanan Görev Sayısı: " + completedTasksCount);
        layout.getChildren().addAll(new Label("Çalışan: " + calisan.getAd() + " " +
                calisan.getSoyad()), tasksTableView, completedTasksLabel);
        Scene scene = new Scene(layout, 600, 400);
        calisanDetayPenceresi.setScene(scene);
        calisanDetayPenceresi.show();
    }
  

    private void calisanEkleAction(TableView<Calisan> calisanTable) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Çalışan Ekle");
        dialog.setHeaderText(null);
        dialog.setContentText("Çalışan Bilgilerini Girin:\nAd Soyad Pozisyon");
        dialog.getEditor().setText("Duygu Kaya Stajyer");
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #D2E3C8;");
        TextField editor = dialog.getEditor();
        editor.setStyle("-fx-text-fill: #739072;-fx-font-weight: bold;");
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(employeeInfo -> {
            String[] infoArray = employeeInfo.split("\\s+");
            if (infoArray.length == 3) {
                String ad = infoArray[0];
                String soyad = infoArray[1];
                String statu = infoArray[2];
                saveEmployeeToDatabase(ad, soyad, statu);
                calisanTable.getItems().add(new Calisan(ad, soyad, statu));
            } else {
                showMessage("Hata", "Geçersiz giriş. Lütfen doğru bilgileri girin.");
            }
        });
    }

    private void calisanSilAction(TableView<Calisan> calisanTable) {
        Calisan seciliCalisan = calisanTable.getSelectionModel().getSelectedItem();
        if (seciliCalisan != null) {
            try (Connection connection = DataBaseConnection.connect()) {
                String query = "DELETE FROM calisanlar WHERE ad=? AND soyad=? AND statu=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, seciliCalisan.getAd());
                    preparedStatement.setString(2, seciliCalisan.getSoyad());
                    preparedStatement.setString(3, seciliCalisan.getStatu());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            calisanTable.getItems().remove(seciliCalisan);
        } else {
            showMessage("Uyarı", "Lütfen bir çalışan seçin.");
        }
    }

    private void saveEmployeeToDatabase(String ad, String soyad, String statu) {
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "INSERT INTO calisanlar (ad, soyad, statu) VALUES (?, ?, ?)";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, ad);
                preparedStatement.setString(2, soyad);
                preparedStatement.setString(3, statu);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private ObservableList<Calisan> getCalisanListesi() {
        ObservableList<Calisan> calisanListesi = FXCollections.observableArrayList();
        // Veritabanından çalışanları çek ve listeye ekle
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT c.ad, c.soyad, c.statu, p.proje_adi, t.task_adi "
                    + "FROM calisanlar c "
                    + "LEFT JOIN projeler p ON c.proje_adi = p.proje_adi "
                    + "LEFT JOIN tasks t ON c.task_adi = t.task_adi";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String ad = resultSet.getString("ad");
                        String soyad = resultSet.getString("soyad");
                        String statu = resultSet.getString("statu");
                        String projeAdi = resultSet.getString("proje_adi");
                        String taskAdi = resultSet.getString("task_adi");

                        Calisan calisan = new Calisan(ad, soyad, statu, projeAdi, taskAdi);
                        calisanListesi.add(calisan);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return calisanListesi;
    }

    private void projeleriGoruntuleAction() {
        Stage projelerPenceresi = new Stage();
        projelerPenceresi.setTitle("Projeleri Görüntüle");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(40, 40, 40, 40));
        layout.setStyle("-fx-background-color: #D2E3C8;");
        TableView<Proje> projeTable = new TableView<>();
        TableColumn<Proje, String> projeAdiColumn = new TableColumn<>("Proje Adı");
        TableColumn<Proje, LocalDate> baslangicTarihiColumn = new TableColumn<>("Başlangıç Tarihi");
        TableColumn<Proje, LocalDate> bitisTarihiColumn = new TableColumn<>("Bitiş Tarihi");
        projeAdiColumn.setCellValueFactory(new PropertyValueFactory<>("projeAdi"));
        baslangicTarihiColumn.setCellValueFactory(new PropertyValueFactory<>("baslangicTarihi"));
        bitisTarihiColumn.setCellValueFactory(new PropertyValueFactory<>("bitisTarihi"));
        TableColumn<Proje, String> durumColumn = new TableColumn<>("Durum");
        durumColumn.setCellValueFactory(new PropertyValueFactory<>("durum"));
        Button durumBelirleButton = new Button("Durum Belirle");
        durumBelirleButton.setOnAction(e -> durumBelirleAction(projeTable));
        durumBelirleButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52;"
                + "-fx-font-weight: bold;");
        projeTable.getColumns().addAll(projeAdiColumn, baslangicTarihiColumn,
                bitisTarihiColumn, durumColumn);
        projeTable.setItems(getProjeList());
        Button projeOlusturButton = new Button("Proje Oluştur");
        projeOlusturButton.setOnAction(e -> {
            projelerPenceresi.close();
            projeOlusturAction();         });
        projeOlusturButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52;"
                + "-fx-font-weight: bold;");
        Button silButton = new Button("Proje Sil");
        silButton.setOnAction(e -> projeSilAction(projeTable));
        silButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52;"
                + "-fx-font-weight: bold;");
        layout.getChildren().addAll(projeTable, new Separator(), projeOlusturButton, 
                new Separator(), silButton, new Separator(),
                durumBelirleButton, new Separator());
        Scene scene = new Scene(layout, 600, 600);
        projelerPenceresi.setScene(scene);
        projelerPenceresi.show();
    }

    private void projeOlusturAction() {

        Stage projeOlusturPenceresi = new Stage();
        projeOlusturPenceresi.setTitle("Proje Oluştur");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setStyle("-fx-background-color: #D2E3C8;");

        TextField projeAdiField = new TextField();
        projeAdiField.setPromptText("Proje Adı");
        DatePicker baslangicDatePicker = new DatePicker();
        baslangicDatePicker.setPromptText("Başlangıç Tarihi");
        DatePicker bitisDatePicker = new DatePicker();
        bitisDatePicker.setPromptText("Bitiş Tarihi");

        Button kaydetButton = new Button("Kaydet");
        kaydetButton.setStyle("-fx-background-color: #FAFAFA; "
                + "-fx-text-fill: #4F6F52;-fx-font-weight: bold;");
        kaydetButton.setOnAction(e -> {
            String projeAdi = projeAdiField.getText();
            LocalDate baslangicTarihi = baslangicDatePicker.getValue();
            LocalDate bitisTarihi = bitisDatePicker.getValue();
            try (Connection connection = DataBaseConnection.connect()) {
                String query = "INSERT INTO projeler (proje_adi, baslangic_tarihi, bitis_tarihi) VALUES (?, ?, ?)";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, projeAdi);
                    preparedStatement.setDate(2, java.sql.Date.valueOf(baslangicTarihi));
                    preparedStatement.setDate(3, java.sql.Date.valueOf(bitisTarihi));
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            projeOlusturPenceresi.close();
            projeleriGoruntuleAction();
        });

        layout.getChildren().addAll(projeAdiField, baslangicDatePicker, bitisDatePicker, kaydetButton);
        Scene scene = new Scene(layout, 400, 300);
        projeOlusturPenceresi.setScene(scene);
        projeOlusturPenceresi.show();
    }

    private void durumBelirleAction(TableView<Proje> projeTable) {
        Proje selectedProje = projeTable.getSelectionModel().getSelectedItem();
        if (selectedProje != null) {
            String selectedStatus = showDurumSelectionDialog();
            if (selectedStatus != null) {
                selectedProje.setDurum(selectedStatus);
                updateProjectStatusInDatabase(selectedProje.getProjeAdi(), selectedStatus);
                refreshProjeTable(projeTable);
            }
        } else {
            showMessage("Uyarı", "Lütfen bir proje seçin.");
        }
    }

    private String showDurumSelectionDialog() {
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Durum Seç");
        dialog.setHeaderText("Proje Durumunu Seçiniz");
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: #D2E3C8;");
        ComboBox<String> durumComboBox = new ComboBox<>();
        durumComboBox.getItems().addAll("Tamamlandı", "Tamamlanacak", "Devam Ediyor");
        gridPane.add(durumComboBox, 0, 0);
        dialog.getDialogPane().getButtonTypes().addAll(ButtonType.OK, ButtonType.CANCEL);
        dialog.getDialogPane().setContent(gridPane);
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == ButtonType.OK) {
                return durumComboBox.getValue();
            }
            return null;
        });
        dialog.showAndWait();
        return durumComboBox.getValue();
    }

    private void updateProjectStatusInDatabase(String projectName, String newStatus) {
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "UPDATE projeler SET durum = ? WHERE proje_adi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, newStatus);
                preparedStatement.setString(2, projectName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private void projeSilAction(TableView<Proje> projeTable) {
        Proje seciliProje = projeTable.getSelectionModel().getSelectedItem();
        if (seciliProje != null) {
            // Proje silme işlemlerini gerçekleştirin
            // Örneğin: Veritabanından proje silme
            try (Connection connection = DataBaseConnection.connect()) {
                String query = "DELETE FROM projeler WHERE proje_adi=?";
                try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                    preparedStatement.setString(1, seciliProje.getProjeAdi());
                    preparedStatement.executeUpdate();
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            projeTable.getItems().remove(seciliProje);
        } else {
            showMessage("Uyarı", "Lütfen bir proje seçin.");
        }
    }
     private ObservableList<String> getProjeAdlari() {
        ObservableList<String> projeAdlari = FXCollections.observableArrayList();

        // Veritabanından projeleri çek
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT proje_adi FROM projeler";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String projeAdi = resultSet.getString("proje_adi");
                        projeAdlari.add(projeAdi);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception according to your application's requirements
        }

        return projeAdlari;
    }
    // Görev durumunu güncelleme metodu

    // Proje bitiş tarihini güncelleme metodu
    private void updateProjectEndDate(String projectName) {
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT MAX(bitis_tarihi) AS latestEndDate FROM tasks WHERE proje_adi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, projectName);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        LocalDate latestEndDate = resultSet.getDate("latestEndDate").toLocalDate();
                        // Eğer görev bitiş tarihi, projenin mevcut bitiş tarihinden büyükse, projenin bitiş tarihini güncelle
                        if (latestEndDate.isAfter(getProjectEndDate(projectName))) {
                            updateProjectEndDateInDatabase(projectName, latestEndDate);
                        }
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Hatanın iş uygulamanıza göre ele alınması gerekiyor
        }
    }

    // Proje bitiş tarihini güncelleme sorgusu
    private void updateProjectEndDateInDatabase(String projectName, LocalDate newEndDate) {
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "UPDATE projeler SET bitis_tarihi = ? WHERE proje_adi = ?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setDate(1, Date.valueOf(newEndDate));
                preparedStatement.setString(2, projectName);
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Hatanın iş uygulamanıza göre ele alınması gerekiyor
        }
    }

    public ObservableList<Proje> getProjeList() {
        ObservableList<Proje> projeList = FXCollections.observableArrayList();
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT proje_adi, baslangic_tarihi, bitis_tarihi, durum FROM projeler";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String projectName = resultSet.getString("proje_adi");
                        LocalDate startDate = resultSet.getDate("baslangic_tarihi").toLocalDate();
                        LocalDate endDate = resultSet.getDate("bitis_tarihi").toLocalDate();
                        String durum = resultSet.getString("durum");

                        projeList.add(new Proje(projectName, startDate, endDate, durum));
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Hatanın iş uygulamanıza göre ele alınması gerekiyor
        }

        return projeList;
    }

    private void gorevleriGoruntuleAction() {
        Stage gorevlerPenceresi = new Stage();
        gorevlerPenceresi.setTitle("Görevler");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setStyle("-fx-background-color: #D2E3C8;");
        Label welcomeLabel = new Label("Görev eklemek için proje seçiniz:");
        welcomeLabel.setStyle("-fx-text-fill: #1B5E20; -fx-font-size: 20; "
                + "-fx-font-weight: bold;");
        ComboBox<String> projeComboBox = new ComboBox<>();
        projeComboBox.setPromptText("Proje Seç");
        projeComboBox.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52;-fx-font-weight: bold;");
        projeComboBox.getItems().addAll(getProjeAdlari());
        projeComboBox.setOnAction(e -> {
            String seciliProje = projeComboBox.getSelectionModel().getSelectedItem();
            if (seciliProje != null) {
                TableView<String> gorevTable = new TableView<>();
                TableColumn<String, String> gorevColumn = new TableColumn<>("Görev");
                gorevColumn.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue()));
                gorevTable.getColumns().add(gorevColumn);
                ObservableList<String> gorevListesi = getGorevListesi(seciliProje);
                gorevTable.setItems(gorevListesi);
                Button gorevEkleButton = new Button("Görev Ekle");
                gorevEkleButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52; -fx-font-weight: bold;");
                gorevEkleButton.setOnAction(event -> gorevEkleAction(seciliProje, gorevTable));
                layout.getChildren().setAll(projeComboBox, gorevTable, gorevEkleButton);
                
            }
        });
        Label welcome3Label = new Label(".................................................................");
        welcome3Label.setStyle("-fx-text-fill: #1B5E20; -fx-font-size: 15; "
                + "-fx-font-weight: bold;");
        Label welcome2Label = new Label("Tüm görevleri görmek için tıklayınız:");
        welcome2Label.setStyle("-fx-text-fill: #1B5E20; -fx-font-size: 20; "
                + "-fx-font-weight: bold;");
        Button tumGorevlerButton = new Button("Tüm Görevler");
        tumGorevlerButton.setStyle("-fx-background-color: #FAFAFA; -fx-text-fill: #4F6F52; -fx-font-weight: bold;");
        tumGorevlerButton.setOnAction(e -> tumGorevleriGoruntuleAction());
        gorevTable = new TableView<>();
        TableColumn<String, String> gorevColumn = new TableColumn<>("Görev");
        gorevColumn.setCellValueFactory(param -> new javafx.beans.property.SimpleStringProperty(param.getValue()));
        gorevColumn.setPrefWidth(300);
        gorevTable.getColumns().add(gorevColumn);
        layout.getChildren().addAll(welcomeLabel,projeComboBox,welcome3Label,welcome2Label,
                tumGorevlerButton);
        Scene scene = new Scene(layout, 500, 300);
        gorevlerPenceresi.setScene(scene);
        gorevlerPenceresi.show();
    }
    private void gorevEkleAction(String seciliProje, TableView<String> gorevTable) {
        Stage gorevEklePenceresi = new Stage();
        gorevEklePenceresi.setTitle("Görev Ekle");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setStyle("-fx-background-color: #D2E3C8;");
        TextField gorevAdiField = new TextField();
        gorevAdiField.setPromptText("Görev Adı");
        gorevAdiField.setStyle("-fx-background-color: #86A789;");
        ComboBox<String> assignedEmployeeComboBox = new ComboBox<>();
        assignedEmployeeComboBox.setPromptText("Çalışan Seç");
        assignedEmployeeComboBox.getItems().addAll(getExistingEmployees());
        assignedEmployeeComboBox.setEditable(true);
        assignedEmployeeComboBox.setStyle("-fx-background-color: #86A789;");
        DatePicker startDatePicker = new DatePicker();
        startDatePicker.setPromptText("Başlangıç Tarihi");
        DatePicker endDatePicker = new DatePicker();
        endDatePicker.setPromptText("Bitiş Tarihi");

        Button kaydetButton = new Button("Kaydet");
        kaydetButton.setStyle("-fx-background-color: #FAFAFA; "
                + "-fx-text-fill: #4F6F52;-fx-font-weight: bold;");
        kaydetButton.setOnAction((ActionEvent event) -> {
            String gorevAdi = gorevAdiField.getText();
            String assignedEmployee = assignedEmployeeComboBox.getValue();
            LocalDate startDate = startDatePicker.getValue();
            LocalDate endDate = endDatePicker.getValue();

            if (!gorevAdi.isEmpty() && !assignedEmployee.isEmpty()) {
                saveGorevToDatabase(seciliProje, gorevAdi, assignedEmployee,
                        startDate, endDate);
                ObservableList<String> updatedGorevListesi = getGorevListesi(seciliProje);
                gorevTable.setItems(updatedGorevListesi);
                gorevEklePenceresi.close();
                tumGorevleriGoruntuleAction();
            } else {
                showMessage("Hata", "Görev adı boş olamaz.");
            }
        });
        layout.getChildren().addAll(gorevAdiField, assignedEmployeeComboBox,
                startDatePicker, endDatePicker, kaydetButton);
        Scene scene = new Scene(layout, 600, 300);
        gorevEklePenceresi.setScene(scene);
        gorevEklePenceresi.show();
    }

    private ObservableList<String> getExistingEmployees() {
        ObservableList<String> employeeList = FXCollections.observableArrayList();
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT ad FROM calisanlar";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String employeeName = resultSet.getString("ad");
                        employeeList.add(employeeName);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception according to your application's requirements
        }
        return employeeList;
    }
    private ObservableList<String> getGorevListesi(String projeAdi) {
        ObservableList<String> gorevListesi = FXCollections.observableArrayList();
        // Veritabanından görevleri, başlangıç ve bitiş tarihleri ve atanan çalışanları çek
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT task_adi FROM tasks WHERE proje_adi=?";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                preparedStatement.setString(1, projeAdi);
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String gorevAdi = resultSet.getString("task_adi");
                        gorevListesi.add(gorevAdi); 
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); 
        }

        return gorevListesi;
    }
    private void tumGorevleriGoruntuleAction() {
        Stage tumGorevlerPenceresi = new Stage();
        tumGorevlerPenceresi.setTitle("Tüm Görevler");
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));
        layout.setStyle("-fx-background-color: #86A789;");

        TableColumn<ProjeGorev, String> projeColumn = new TableColumn<>("Proje");
        projeColumn.setCellValueFactory(new PropertyValueFactory<>("projeAdi"));
        TableColumn<ProjeGorev, String> gorevColumn = new TableColumn<>("Görev ");
        gorevColumn.setCellValueFactory(new PropertyValueFactory<>("gorevAdi"));
        TableColumn<ProjeGorev, String> assignedEmployeeColumn = new TableColumn<>("Atanan Çalışan");
        assignedEmployeeColumn.setCellValueFactory(new PropertyValueFactory<>("assignedEmployee"));
        TableColumn<ProjeGorev, LocalDate> startDateColumn = new TableColumn<>("Başlangıç Tarihi");
        startDateColumn.setCellValueFactory(new PropertyValueFactory<>("startDate"));
        TableColumn<ProjeGorev, LocalDate> endDateColumn = new TableColumn<>("Bitiş Tarihi");
        endDateColumn.setCellValueFactory(new PropertyValueFactory<>("endDate"));
        TableColumn<ProjeGorev, String> adamGunDegeriColumn = new TableColumn<>("Adam Gün Değeri");
        adamGunDegeriColumn.setCellValueFactory(new PropertyValueFactory<>("adamGunDegeri"));
        TableColumn<ProjeGorev, String> statusColumn = new TableColumn<>("Durum");
        statusColumn.setCellValueFactory(new PropertyValueFactory<>("durum"));
        TableColumn<ProjeGorev, Integer> gecikmeMiktariColumn = new TableColumn<>("Gecikme Miktarı");
        gecikmeMiktariColumn.setCellValueFactory(new PropertyValueFactory<>("gecikmeMiktari"));
        TableView<ProjeGorev> tumGorevlerTable = new TableView<>();
        tumGorevlerTable.getColumns().addAll(projeColumn, gorevColumn, assignedEmployeeColumn,
                startDateColumn, endDateColumn, adamGunDegeriColumn, statusColumn, gecikmeMiktariColumn);
        ObservableList<ProjeGorev> tumGorevlerListesi = getTumGorevlerListesi();
        tumGorevlerTable.setItems(tumGorevlerListesi);
        Button assignStatusButton = new Button("Durum Belirle");
        assignStatusButton.setStyle("-fx-background-color: #FAFAFA; "
                + "-fx-text-fill: #4F6F52; -fx-font-weight: bold;");
        assignStatusButton.setOnAction(e -> {
            ProjeGorev selectedGorev = tumGorevlerTable.getSelectionModel().getSelectedItem();
            if (selectedGorev != null) {
                showStatusSelectionDialog(selectedGorev);
            } else {
                showMessage("Hata", "Lütfen bir görev seçin.");
            }
        });

        layout.getChildren().addAll(tumGorevlerTable, assignStatusButton);

        Scene scene = new Scene(layout, 1000, 600);
        tumGorevlerPenceresi.setScene(scene);
        tumGorevlerPenceresi.show();
    }

    private void showStatusSelectionDialog(ProjeGorev selectedGorev) {
        Stage statusSelectionStage = new Stage();
        statusSelectionStage.setTitle("Durum Seç");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20, 20, 20, 20));

        ComboBox<String> statusComboBox = new ComboBox<>();
        statusComboBox.setPromptText("Durum Seç");
        statusComboBox.getItems().addAll("Tamamlandı", "Devam Ediyor", "Tamamlanacak");
        statusComboBox.setStyle("-fx-background-color: #86A789;");

        Button kaydetButton = new Button("Kaydet");
        kaydetButton.setStyle("-fx-background-color: #FAFAFA; "
                + "-fx-text-fill: #4F6F52;-fx-font-weight: bold;");
        kaydetButton.setOnAction((ActionEvent e) -> {
            String selectedStatus = statusComboBox.getValue();
            if (selectedStatus != null) {
                updateStatusInDatabase(selectedGorev, selectedStatus);
                statusSelectionStage.close();
                tumGorevleriGoruntuleAction();
            } else {
                showMessage("Hata", "Durum seçilmelidir.");
            }
        });

        layout.getChildren().addAll(statusComboBox, kaydetButton);
        Scene scene = new Scene(layout, 300, 150);
        statusSelectionStage.setScene(scene);
        statusSelectionStage.show();
    }

    private void updateStatusInDatabase(ProjeGorev selectedGorev, String newStatus) {
        // Update the 'status' in the 'tasks' table for the selectedGorev
        try (Connection connection = DataBaseConnection.connect()) {
            String updateQuery = "UPDATE tasks SET durum = ? WHERE proje_adi = ? AND task_adi = ?";
            try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery)) {
                updateStatement.setString(1, newStatus);
                updateStatement.setString(2, selectedGorev.getProjeAdi());
                updateStatement.setString(3, selectedGorev.getGorevAdi());
                updateStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception according to your application's requirements
        }
    }

    private ObservableList<ProjeGorev> getTumGorevlerListesi() {
        ObservableList<ProjeGorev> tumGorevlerListesi = FXCollections.observableArrayList();
        try (Connection connection = DataBaseConnection.connect()) {
            String query = "SELECT proje_adi, task_adi, assigned_employee,"
                    + " durum, baslangic_tarihi, bitis_tarihi, adamGunDegeri FROM tasks";
            try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    while (resultSet.next()) {
                        String projeAdi = resultSet.getString("proje_adi");
                        String gorevAdi = resultSet.getString("task_adi");
                        String assignedEmployee = resultSet.getString("assigned_employee");
                        String durum = resultSet.getString("durum");
                        LocalDate startDate = resultSet.getDate("baslangic_tarihi") != null
                                ? resultSet.getDate("baslangic_tarihi").toLocalDate()
                                : null;
                        LocalDate endDate = resultSet.getDate("bitis_tarihi") != null
                                ? resultSet.getDate("bitis_tarihi").toLocalDate()
                                : null;
                        String adamGunDegeri = resultSet.getString("adamGunDegeri");
                        ProjeGorev projeGorev = new ProjeGorev(projeAdi, gorevAdi, assignedEmployee, startDate, endDate, durum);
                        projeGorev.setGecikmeMiktari(projeGorev.calculateGecikmeMiktari());
                        projeGorev.setAdamGunDegeri(adamGunDegeri);
                        tumGorevlerListesi.add(projeGorev);
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace(); // Handle the exception according to your application's requirements
        }
        return tumGorevlerListesi;
    }

    

    private void saveGorevToDatabase(String projeAdi, String gorevAdi,
            String assignedEmployee, LocalDate startDate, LocalDate endDate) {        
        try (Connection connection = DataBaseConnection.connect()) {
            long daysBetween = ChronoUnit.DAYS.between(startDate, endDate);
            int adamGunDegeri = Math.toIntExact(daysBetween);
            String taskInsertQuery = "INSERT INTO tasks (proje_adi, "
                    + "task_adi, assigned_employee, baslangic_tarihi, "
                    + "bitis_tarihi, adamGunDegeri) VALUES (?, ?, ?, ?, ?, ?)";
            try (PreparedStatement taskInsertStatement
                    = connection.prepareStatement(taskInsertQuery)) {
                taskInsertStatement.setString(1, projeAdi);
                taskInsertStatement.setString(2, gorevAdi);
                taskInsertStatement.setString(3, assignedEmployee);
                taskInsertStatement.setObject(4, startDate);
                taskInsertStatement.setObject(5, endDate);
                taskInsertStatement.setInt(6, adamGunDegeri);
                taskInsertStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    


    public LocalDate getProjectEndDate(String projectName) {

        return LocalDate.now();
    }

    public LocalDate getProjeEndDate(String projectName) {

        return LocalDate.now();
    }

    private void refreshProjeTable(TableView<Proje> projeTable) {
        projeTable.setItems(getProjeList());
    }

    public static class Gorev {

        private String gorevAdi;

        public Gorev(String gorevAdi) {
            this.gorevAdi = gorevAdi;
        }

        public String getGorevAdi() {
            return gorevAdi;
        }
    }

    public class ProjeGorev {

        private String projeAdi;
        private String gorevAdi;
        private String assignedEmployee;
        private LocalDate startDate;
        private LocalDate endDate;
        private int gecikmeMiktari;
        private String durum;
        private String adamGunDegeri;

        public ProjeGorev(String projeAdi, String gorevAdi,
                String assignedEmployee, LocalDate startDate, LocalDate endDate,
                String durum) {
            this.projeAdi = projeAdi;
            this.gorevAdi = gorevAdi;
            this.assignedEmployee = assignedEmployee;
            this.startDate = startDate;
            this.endDate = endDate;
            this.durum = durum;
            this.gecikmeMiktari = calculateGecikmeMiktari();
            this.adamGunDegeri = adamGunDegeri;

        }

        public String getAdamGunDegeri() {
            return adamGunDegeri;
        }

        public void setAdamGunDegeri(String adamGunDegeri) {
            this.adamGunDegeri = adamGunDegeri;
        }

        public String getDurum() {
            return durum;
        }

        public void setDurum(String durum) {
            this.durum = durum;
        }

        public LocalDate getStartDate() {
            return startDate;
        }

        public LocalDate getEndDate() {
            return endDate;
        }

        public String getProjeAdi() {
            return projeAdi;
        }

        public void setProjeAdi(String projeAdi) {
            this.projeAdi = projeAdi;
        }

        public String getGorevAdi() {
            return gorevAdi;
        }

        public void setGorevAdi(String gorevAdi) {
            this.gorevAdi = gorevAdi;
        }

        public String getAssignedEmployee() {
            return assignedEmployee;
        }

        public void setAssignedEmployee(String assignedEmployee) {
            this.assignedEmployee = assignedEmployee;
        }

        public int getGecikmeMiktari() {
            return gecikmeMiktari;
        }

        public void setGecikmeMiktari(int gecikmeMiktari) {
            this.gecikmeMiktari = gecikmeMiktari;
        }

        public int calculateGecikmeMiktari() {
            if (endDate != null && LocalDate.now().isAfter(endDate)) {
                return (int) startDate.until(endDate, ChronoUnit.DAYS);
            } else {
                return 0;
            }
        }
    }

    public class CalisanTask {

        private String task_adi;
        private String proje_adi;
        private String durum;

        public CalisanTask(String task_adi, String proje_adi, String durum) {
            this.task_adi = task_adi;
            this.proje_adi = proje_adi;
            this.durum = durum;
        }

        public String getTask_adi() {
            return task_adi;
        }

        public void setTask_adi(String task_adi) {
            this.task_adi = task_adi;
        }

        public String getProje_adi() {
            return proje_adi;
        }

        public void setProje_adi(String proje_adi) {
            this.proje_adi = proje_adi;
        }

        public String getDurum() {
            return durum;
        }

        public void setDurum(String durum) {
            this.durum = durum;
        }
    }
}
