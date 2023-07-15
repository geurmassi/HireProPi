package edu.connection.gui;

import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

public class NoCopyPasteTextField extends TextField {

    public NoCopyPasteTextField() {
        disableCopyPasteCut();
        disableContextMenu();
    }

    private void disableCopyPasteCut() {
        this.setOnKeyPressed(event -> {
            if (event.isControlDown() && (event.getCode().equals(KeyCode.C) || event.getCode().equals(KeyCode.V) || event.getCode().equals(KeyCode.X))) {
                event.consume(); // Empêche la copie, le collage et la découpe
            }
        });
    }

    private void disableContextMenu() {
        this.addEventHandler(MouseEvent.MOUSE_RELEASED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                event.consume(); // Empêche l'apparition du menu contextuel (clic droit)
            }
        });
    }
}
