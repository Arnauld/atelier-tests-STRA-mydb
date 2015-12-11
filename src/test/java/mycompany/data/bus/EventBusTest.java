package mycompany.data.bus;

import org.junit.Test;

import javax.swing.JPanel;

import java.io.Serializable;

import static org.assertj.core.api.Assertions.assertThat;

public class EventBusTest {

    @Test
    public void should_write_hex_in_console() {
        MyPanel panel = new MyPanel();
        EventBus.publish(panel);
    }

    public static class MyPanel extends JPanel implements Serializable {}
}