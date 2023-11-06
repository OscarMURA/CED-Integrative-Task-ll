module com.example.romeandvikings {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires com.almasb.fxgl.all;
    requires junit;

    opens com.example.romeandvikings to javafx.fxml;
    exports com.example.romeandvikings;
    exports com.example.romeandvikings.controls;
    opens com.example.romeandvikings.controls to javafx.fxml;
    exports com.example.romeandvikings.test;


}