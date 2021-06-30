package model.services;

import model.dao.DaoFactory;
import model.dao.SellerDao;
import model.entities.Seller;

import java.util.List;

public class SellerService {

    private SellerDao dao = DaoFactory.createSellerDao();

    public List<Seller> findAll(){
        return dao.findAll();
    }

    //Método para salvar dados no DB
    public void seveOrUpdate(Seller obj){
        if (obj.getId() == null){
            dao.insert(obj);
        }
        else {
            dao.update(obj);
        }
    }

    public void remove(Seller obj){
        dao.deleteById(obj.getId());
    }
}
