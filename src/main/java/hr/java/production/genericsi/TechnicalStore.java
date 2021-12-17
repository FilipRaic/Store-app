package hr.java.production.genericsi;

import hr.java.production.model.Item;
import hr.java.production.model.Store;
import hr.java.production.model.Technical;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TechnicalStore <T extends Technical> extends Store {

    private List<T> listOfTechnic;

    public TechnicalStore(Long id, String name, String webAddress, Set<Item> items, List<T> listOfTechnic) {
        super(id, name, webAddress, items);
        this.listOfTechnic = listOfTechnic;
    }

    public TechnicalStore(Long id, String name, String webAddress, List<T> listOfTechnic) {
        super(id, name, webAddress);
        this.listOfTechnic = listOfTechnic;
    }

    public List<T> getListOfTechnic() {
        return listOfTechnic;
    }

    public void setListOfTechnic(List<T> listOfTechnic) {
        this.listOfTechnic = listOfTechnic;
    }

    @Override
    public Set<Item> getItems() {
        Set<Item> tmpSet = new HashSet<>();
        tmpSet.addAll((Collection<? extends Item>) listOfTechnic);
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
