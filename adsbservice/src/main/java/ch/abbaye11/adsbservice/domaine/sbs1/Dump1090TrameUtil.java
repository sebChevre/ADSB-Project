package ch.abbaye11.adsbservice.domaine.sbs1;

import java.util.Arrays;
import java.util.Objects;

public class Dump1090TrameUtil {

    private final static int TRAME_SPLIIT_TOKEN_MIN = 10;

    public static Boolean validatDump1090TCPTrame(String tcpTrame){

        try{
            Objects.requireNonNull(tcpTrame,"Trame can't be null");

            if(tcpTrame.isEmpty()) throw new IllegalArgumentException("Trame cannot be empty");

            if(tcpTrame.split(",",-1).length < 10) throw new TcpTrameEception("The tokenized chain dont containss enough token");

            SBS1MessageType sbs1MessageType = Arrays.stream(SBS1MessageType.values()).filter(messageType -> {
                return tcpTrame.split(",")[0].equals(messageType.toString());
            }).findFirst().orElseThrow(()->
                    new TcpTrameEception("Cannot find message type correlation : " + tcpTrame.split(",",-1)[0]));

        }catch (Exception e) {

            return false;
        }

        return true;
    }

}
