package duckcorp.stock;

import duckcorp.duck.Duck;
import duckcorp.duck.DuckType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Stock générique de canards.
 *
 * @param <T> type de canard stocké (doit étendre Duck)
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class Stock<T extends Duck> {

    private final List<T> items = new ArrayList<>();

    // --- Méthodes fournies ---

    /** Ajoute un canard au stock. */
    public void add(T duck) {
        items.add(duck);
    }

    /** Retourne une vue non modifiable de tous les canards en stock. */
    public List<T> getAll() {
        return Collections.unmodifiableList(items);
    }

    /** Retourne le nombre total de canards en stock. */
    public int total() {
        return items.size();
    }

    // --- Méthodes implémentées ---

    /**
     * Retire exactement {@code count} canards du type {@code type} du stock
     * et les retourne dans une liste.
     *
     * @param type  le type de canard à retirer
     * @param count le nombre à retirer
     * @return la liste des canards retirés
     * @throws IllegalStateException si le stock ne contient pas assez de canards du type demandé
     */
    public List<T> remove(DuckType type, int count) {
        if (count <= 0) return new ArrayList<>();

        List<T> removedDucks = new ArrayList<>();
        Iterator<T> iterator = items.iterator();

        // Une seule passe sur la collection
        while (iterator.hasNext() && removedDucks.size() < count) {
            T duck = iterator.next();
            if (duck.getType() == type) {
                removedDucks.add(duck);
                iterator.remove(); // Retire l'élément en toute sécurité pendant l'itération
            }
        }

        // Si on n'a pas trouvé assez de canards, on annule (rollback) et on lève l'exception
        if (removedDucks.size() < count) {
            items.addAll(removedDucks);
            throw new IllegalStateException("Pas assez de canards en stock pour le type : " + type);
        }

        return removedDucks;
    }

    /**
     * Retourne le nombre de canards du type {@code type} présents dans le stock.
     *
     * @param type le type à compter
     */
    public int count(DuckType type) {
        int count = 0;
        for (T duck : items) {
            if (duck.getType() == type) {
                count++;
            }
        }
        return count;
    }

    /**
     * Retourne le nombre de canards défectueux dans le stock.
     * Un canard est défectueux si isDefective() retourne true.
     */
    public int countDefective() {
        int defectiveCount = 0;
        for (T duck : items) {
            if (duck.isDefective()) {
                defectiveCount++;
            }
        }
        return defectiveCount;
    }

    /**
     * Retourne une Map associant chaque DuckType au nombre de canards
     * de ce type présents dans le stock.
     */
    public Map<DuckType, Integer> countByType() {
        Map<DuckType, Integer> counts = new HashMap<>();
        
        // Initialise tous les types de canards possibles à 0
        for (DuckType type : DuckType.values()) {
            counts.put(type, 0);
        }

        // Met à jour les compteurs en une seule passe
        for (T duck : items) {
            counts.put(duck.getType(), counts.get(duck.getType()) + 1);
        }

        return counts;
    }
}