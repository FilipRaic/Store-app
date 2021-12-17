package hr.java.production.genericsi;

import hr.java.production.model.Edible;
import hr.java.production.model.Item;
import hr.java.production.model.Store;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class FoodStore <T extends Edible> extends Store {

    private List<T> listOfEdibles;

    public FoodStore(Long id, String name, String webAddress, List<T> listOfEdibles) {
        super(id, name, webAddress);
        this.listOfEdibles = listOfEdibles;
    }

    public List<T> getListOfEdibles() {
        return listOfEdibles;
    }

    public void setListOfEdibles(List<T> listOfEdibles) {
        this.listOfEdibles = listOfEdibles;
    }

    @Override
    public Set<Item> getItems() {
        Set<Item> tmpSet = new HashSet<>();
        tmpSet.addAll((Collection<? extends Item>) listOfEdibles);
        return tmpSet;
    }

    @Override
    public String toString() {
        String list = "";

        for (Item item : getItems())
            list += item.getName() + "\n";

        return list;
    }
}
