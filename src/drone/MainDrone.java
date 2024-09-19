package drone;

import drone.sensores.DroneNorte;

public class MainDrone {
    public static void main(String[] args){
        DroneNorte droneNorte = new DroneNorte("224.0.0.1", 8888);

        droneNorte.run();
    }
}