package api_rest;

import dao.impl.HibernateDataDAO;
import dao.impl.HibernateJuegoDAO;
import dao.impl.HibernateSearchDAO;
import dao.interf.DataDAO;
import io.javalin.Context;
import io.javalin.json.JavalinJson;
import model.Genero;
import model.Juego;
import model.Plataforma;
import service.impl.DataServiceImpl;
import service.impl.JuegoServiceImpl;
import service.impl.SearchService;
import service.interf.DataService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class AppController {
    public JuegoServiceImpl juegoService = new JuegoServiceImpl(new HibernateJuegoDAO());
    public SearchService searchService = new SearchService(new HibernateSearchDAO());

    public Context buscarJuegoPorNombre(Context ctx){
        return ctx.json(juegoService.buscarJuego(ctx.pathParam("searchvalue")));
    }

    public Context buscarPorNombre(Context ctx){
        return ctx.json(searchService.busquedaPorNombre(ctx.pathParam("nombre")));
    }

    public Context buscarPorGenero(Context ctx){
        return ctx.json(searchService.busquedaPorgenero(Genero.valueOf(ctx.pathParam("gender"))));
    }

    public Context buscarPorPlataforma(Context ctx){
        return ctx.json(searchService.busquedaPorPlataforma(Plataforma.valueOf(ctx.pathParam("platform"))));
    }

    public Context buscarJuegosPorNombreGeneroPlataforma(Context ctx){
        String gameName = ctx.pathParam("name");
        String gameGenre = ctx.pathParam("genre");
        String gamePlatform = ctx.pathParam("platform");

        ArrayList<Juego> games = new ArrayList<>(searchService.busquedaPorNombre(gameName));
        if (!gameGenre.equals("Any")){
            games.addAll(searchService.busquedaPorgenero(Genero.valueOf(gameGenre)));
        }
        if (!gamePlatform.equals("Any")){
            games.addAll(searchService.busquedaPorPlataforma(Plataforma.valueOf(gamePlatform)));
        }

        return ctx.json(this.sinRepetidos(games));
    }

    private List<Juego> sinRepetidos(List<Juego> lista){
        List<Juego> nuevaLista = new ArrayList<>();
        List<String> listaDeNombres = lista.stream().map(Juego::getNombre).collect(Collectors.toList());
        for(int i = 0; i < lista.size(); i++){
            if(listaDeNombres.contains(lista.get(i).getNombre())){
                nuevaLista.add(lista.get(i));
                int finalI = i;
                listaDeNombres = listaDeNombres.stream().filter(n -> !n.equals(lista.get(finalI).getNombre())).collect(Collectors.toList());
            }
        }
        return nuevaLista;
    }

    public void initializeDatabase() {
        DataDAO dataDAO = new HibernateDataDAO();
        DataService dataService = new DataServiceImpl(dataDAO);
        dataService.eliminarDatos();
        dataService.crearDatosIniciales();
    }
}