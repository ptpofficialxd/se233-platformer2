package se233.chapter4.model;

import javafx.scene.input.KeyCode;
import java.util.HashMap;

public class Keys {
    private HashMap<KeyCode, Boolean> keys ;

    public Keys() {
        keys = new HashMap<>();
    }

    public void add(KeyCode key) {
        this.keys.put(key, true);
    }

    public void remove(KeyCode key) {
        this.keys.put(key, false);
    }

    public boolean isPressed(KeyCode key) { return this.keys.getOrDefault(key,false) ; }
}