package dao.impl;

import dao.interf.GameDAO;
import model.Game;
import org.hibernate.Session;
import org.hibernate.query.Query;
import service.TransactionRunner;

public class HibernateGameDAO extends HibernateDAO<Game> implements GameDAO {

    public HibernateGameDAO(){
        super(Game.class);

    }

    @Override
    public Game recuperarJuegoPorNombre(String nombre) {
        Session session = TransactionRunner.getCurrentSession();
        String hql = "from Game g " + "where g.nombre =  :nombre";
        Query<Game> query = session.createQuery(hql, Game.class);
        query.setParameter("nombre", nombre);

        return this.validacion(query.uniqueResult());
    }


}