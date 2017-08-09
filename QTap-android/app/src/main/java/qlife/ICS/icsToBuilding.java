package qlife.ICS;

/**
 * Created by Alex on 4/6/2017.
 */

public class icsToBuilding {
    public static double[] getAddress(String loc) {
        double[] adr = new double[2];

        if (loc.contains("WALTER LIGHT")) {
            adr[0] = 44.227973;
            adr[1] = -76.491807;
        } else if (loc.contains("MCLAUGHLIN")) {
            adr[0] = 44.223761;
            adr[1] = -76.495409;
        } else if (loc.contains("BIOSCI")) {
            adr[0] = 44.226486;
            adr[1] = -76.491286;
        } else if (loc.contains("KINES & HLTH")) {
            adr[0] = 44.228575;
            adr[1] = -76.493408;
        } else if (loc.contains("KINGSTON")) {
            adr[0] = 44.225628;
            adr[1] = -76.494859;
        }
        else if (loc.contains("JEFFERY")){
            adr[0] = 44.225885;
            adr[1] = -76.496158;
        }
        else if (loc.contains("BEAMISH-MUNRO")){
            adr[0] = 44.228192;
            adr[1] = -76.492399;
        }
        else if (loc.contains("DUNNING")){
            adr[0] = 44.227382;
            adr[1] = -76.496058;
        }
        else if (loc.contains("STIRLING")){
            adr[0] = 44.224582;
            adr[1] = -76.497718;
        }
        else if (loc.contains("CHERNOFF")){
            adr[0] = 44.224244;
            adr[1] = -76.498919;
        }
        else if (loc.contains("ELLIS")){
            adr[0] = 44.226318;
            adr[1] = -76.496336;
        }
        else {
            adr[0] = 44.225233;
            adr[1] = 76.495163;
        }
        return adr;
    }
}
