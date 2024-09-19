package drone.utils;


public class GeradorDeDados {

    public static String gerarDadosNorte() {
        double temperatura = 25 + (Math.random() * 15); // 25°C a 40°C
        double pressao = 1000 + (Math.random() * 100);  // 1000 a 1100 hPa
        double umidade = 60 + (Math.random() * 30);     // 60% a 90%
        double radiacaoSolar = 800 + (Math.random() * 400); // 800 a 1200 W/m²

        return formatarDados(temperatura, pressao, umidade, radiacaoSolar);
    }

    public static String gerarDadosSul() {
        double temperatura = 5 + (Math.random() * 25);  // 5°C a 30°C
        double pressao = 980 + (Math.random() * 60);    // 980 a 1040 hPa
        double umidade = 50 + (Math.random() * 40);     // 50% a 90%
        double radiacaoSolar = 500 + (Math.random() * 300); // 500 a 800 W/m²

        return formatarDados(temperatura, pressao, umidade, radiacaoSolar);
    }

    private static String formatarDados(double temperatura, double pressao, double umidade, double radiacaoSolar) {
        return String.format(
                "Temperatura: %.2f°C, Pressão: %.2f hPa, Umidade: %.2f%%, Radiação Solar: %.2f W/m²",
                temperatura, pressao, umidade, radiacaoSolar
        );
    }
}