
package Controlador;

import Modelo.Producto;
import Vista.Grafica;
import java.io.IOException;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

public class ControlGrafica {
    private Grafica barras;
    private Grafica pastel;
    private JFreeChart graficaPastel;
    private JFreeChart graficaBarras;
    private DefaultCategoryDataset datosBarra;
    private DefaultPieDataset datosPastel;

    public ControlGrafica(ArrayList<Producto> productos, Grafica barras, Grafica pastel) {
        this.barras = barras;
        this.pastel = pastel;
        graficaPastel = crearGraficaPastel(productos);
        graficaBarras = crearGraficaBarras(productos);
        ChartPanel panel = new ChartPanel(this.graficaBarras);
        ChartPanel panel2 = new ChartPanel(this.graficaPastel);
       
        this.barras.setContentPane(panel);
        this.pastel.setContentPane(panel2);
        this.barras.pack();
        this.pastel.pack();
    }

    public void actualizarGrafica(ArrayList<Producto> productos) {
        productos.stream().forEach(e -> {
            datosBarra.setValue(e.getVotos(), e.getNombre(), "votos");
        });

        productos.stream().forEach(e -> {
            datosPastel.setValue(e.getNombre(), e.getVotos());
        });
    }

    public JFreeChart crearGraficaPastel(ArrayList<Producto> productos) {
        datosPastel = new DefaultPieDataset();
        
        productos.stream().forEach(e -> {
            datosPastel.setValue(e.getNombre(), e.getVotos());
        });
        
        JFreeChart pastel = ChartFactory.createPieChart("Grafica de Pastel", datosPastel, true, true, true);
        return pastel;
    }

    public JFreeChart crearGraficaBarras(ArrayList<Producto> productos) {
        datosBarra = new DefaultCategoryDataset();
        
        productos.stream().forEach(e -> {
            datosBarra.setValue(e.getVotos(), e.getNombre(), "votos");
        });
        
        JFreeChart barra = ChartFactory.createBarChart("Grafica de Barras", "Votos", "Productos", 
                datosBarra, PlotOrientation.VERTICAL, true, true, false);
        return barra;
    }
}
