module inventory {
    requires javafx.graphics;
    requires javafx.fxml;
    requires javafx.controls;

    opens inventory.model to javafx.base;
    exports inventory.model;
    opens inventory to javafx.fxml;
    exports inventory;
    opens inventory.controller to javafx.fxml;
    exports inventory.controller;
    opens inventory.repository to org.junit.jupiter.engine;
    exports inventory.repository;
    opens inventory.service to org.junit.jupiter.engine;
    exports inventory.service;
}