package mycompany.data.bus;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * @author <a href="http://twitter.com/aloyer">@aloyer</a>
 */
public class EventBus {
    public static void publish(Serializable message) {
        try {
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            ObjectOutputStream oout = new ObjectOutputStream(bout);
            oout.writeObject(message);

            System.out.print(">Connecting");
            for(int i=0;i<20;i++) {
                System.out.print('.');
                Thread.sleep(100);
            }

            System.out.println();
            int nb = 0;
            for (byte b : bout.toByteArray()) {
                String s = Integer.toHexString((int) b & 0xFF);
                if(s.length()<2)
                    System.out.print("0");
                System.out.print(s);
                System.out.print(' ');

                nb++;
                if(nb>0 && nb%16 == 0) {
                    System.out.println();
                }
            }
            System.out.println();
            System.out.println("Message sent");

        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
