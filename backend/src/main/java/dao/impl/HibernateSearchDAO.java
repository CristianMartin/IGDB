package dao.impl;

import dao.interf.SearchDAO;
import model.Genre;
import model.Game;
import model.Platform;
import org.hibernate.Session;
import service.TransactionRunner;

import javax.persistence.GeneratedValue;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HibernateSearchDAO implements SearchDAO {

    
    @Override
    public List<Game> searchAll(String name, Genre genre, Platform platform) {

        Set<Game> games = new HashSet<>();

        if (name.equals("") && genre.equals(Genre.Any) && platform.equals(Platform.Any)) {
            Session session = TransactionRunner.getCurrentSession();

            String hql = "SELECT g from Game as g ";

            return session.createQuery(hql, Game.class).getResultList();
        }

        if (!genre.equals(Genre.Any)) {
            games.addAll(this.searchByGenre(genre));
        }
        if (!platform.equals(Platform.Any)) {
            games.addAll(this.searchByPlatform(platform));
        }
        if (!name.equals("")) {
            List<Game> filterGame = this.searchByName(name);

            if (!games.isEmpty()) {
                games.retainAll(filterGame);
            } else {
                games.addAll(filterGame);
            }
        }

        return new ArrayList<>(games);
    }



    @Override
    public List<Game> searchByGenre(Genre genre) {
        Session session = TransactionRunner.getCurrentSession();

        String hql = " from Game as g " +
                     " where g.genre = :genre";

        return session.createQuery(hql, Game.class).setParameter("genre", genre).getResultList();



    }

    @Override
    public List<Game> searchByPlatform(Platform platform) {
        Session session = TransactionRunner.getCurrentSession();

        String hql = "from Game as g " +
                "where g.platform = :platform ";

        return session.createQuery(hql, Game.class).setParameter("platform", platform).getResultList();
    }

    @Override
    public List<Game> searchByName(String nombre) {
        Session session = TransactionRunner.getCurrentSession();

        String hql = "from Game as g " +
                      "where g.name  LIKE CONCAT('%',?1,'%')";

        return session.createQuery(hql, Game.class).setParameter(1, nombre).getResultList();
    }


}
