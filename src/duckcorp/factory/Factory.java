package duckcorp.factory;

import duckcorp.duck.Duck;
import duckcorp.machine.Machine;
import duckcorp.order.Order;
import duckcorp.stats.ProductionStats;
import duckcorp.stock.Stock;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * L'usine du joueur. Gère le budget, les machines, le stock et la réputation.
 *
 * @author Roussille Philippe <roussille@3il.fr>
 */
public class Factory {

    private double budget;
    private double reputation;
    private final Stock<Duck> stock;
    private final List<Machine> machines;
    private final ProductionStats stats;

    public Factory(double initialBudget) {
        this.budget     = initialBudget;
        this.reputation = 100.0;
        this.stock      = new Stock<>();
        this.machines   = new ArrayList<>();
        this.stats      = new ProductionStats();
    }

    // --- Getters fournis ---

    public double          getBudget()     { return budget; }
    public double          getReputation() { return reputation; }
    public Stock<Duck>     getStock()      { return stock; }
    public List<Machine>   getMachines()   { return Collections.unmodifiableList(machines); }
    public ProductionStats getStats()      { return stats; }

    // --- Méthodes fournies ---

    /**
     * Signale qu'une commande a expiré : pénalise la réputation et met à jour les stats.
     * Appelée par Game à chaque commande expirée. Ne pas modifier.
     */
    public void notifyExpiredOrder() {
        reputation = Math.max(0, reputation - 5);
        // stats.recordExpiredOrder(); // Commenté car Bonus pas fait
    }

    /**
     * Calcule le score final du joueur.
     * Formule : budget + réputation × 80 + commandesHonorées × 200 − commandesExpirées × 100
     */
    public int computeScore() {
        // Adapté si ProductionStats n'est pas implémenté : renvoie juste un score partiel pour l'instant
        return (int) (budget + reputation * 80);
        
        /* // Code commenté car Bonus pas fait :
        return (int) (budget
                + reputation * 80
                + stats.getTotalOrders() * 200
                - stats.getOrdersExpired() * 100);
        */
    }

    // --- Méthodes implémentées (Ex5) ---

    /**
     * Achète une machine si le budget est suffisant.
     * Déduit le coût d'achat du budget et ajoute la machine à la liste.
     *
     * @return true si l'achat a réussi, false si budget insuffisant
     */
    public boolean buyMachine(Machine machine) {
        if (budget >= machine.getPurchaseCost()) {
            budget -= machine.getPurchaseCost();
            machines.add(machine);
            return true;
        }
        return false;
    }

    /**
     * Effectue la maintenance d'une machine si le budget est suffisant.
     * Déduit le coût de maintenance et appelle machine.maintain().
     *
     * @return true si la maintenance a réussi, false si budget insuffisant
     */
    public boolean maintainMachine(Machine machine) {
        if (budget >= machine.getMaintenanceCost()) {
            budget -= machine.getMaintenanceCost();
            machine.maintain();
            return true;
        }
        return false;
    }

    /**
     * Lance la production de toutes les machines pour ce tour.
     * Chaque machine produit autant de canards que sa capacité.
     * Les canards sont ajoutés au stock et retournés dans une liste.
     *
     * @return la liste de tous les canards produits ce tour
     */
    public List<Duck> runProduction() {
        List<Duck> producedThisTurn = new ArrayList<>();
        
        for (Machine machine : machines) {
            for (int i = 0; i < machine.getCapacity(); i++) {
                Duck duck = machine.produceDuck();
                stock.add(duck);
                producedThisTurn.add(duck);
            }
        }
        
        // stats.recordProduction(producedThisTurn); // TODO (Bonus 1)
        
        return producedThisTurn;
    }

    /**
     * Tente d'honorer une commande.
     * Si le stock est suffisant, retire les canards, crédite le budget, 
     * met à jour la réputation et marque la commande.
     *
     * @return true si la commande a été honorée, false sinon
     */
    public boolean fulfillOrder(Order order) {
        if (!order.canBeFulfilled(stock)) {
            return false;
        }

        // Retrait des canards (comportement standard de l'Ex3)
        List<Duck> shippedDucks = stock.remove(order.getDuckType(), order.getQuantity());

        // Calcul de la qualité moyenne
        int totalQuality = 0;
        for (Duck duck : shippedDucks) {
            totalQuality += duck.getQualityScore();
        }
        double avgQuality = shippedDucks.isEmpty() ? 0 : (double) totalQuality / shippedDucks.size();

        // Crédit du budget
        budget += order.getTotalValue();

        // Mise à jour de la réputation
        if (avgQuality >= 70) {
            reputation = Math.min(100.0, reputation + 3);
        } else if (avgQuality >= 50) {
            reputation = Math.min(100.0, reputation + 1);
        }

        // Marque la commande comme honorée
        order.fulfill();
        
        // stats.recordSale(order); // TODO (Bonus 1)

        return true;
    }

    // --- TODO (Bonus 1) ---

    /**
     * Fin de tour : dégrade toutes les machines.
     * Pour chaque machine en état critique après dégradation (needsMaintenance()),
     * pénalise la réputation de 5 points.
     */
    public void endTurn() {
        throw new UnsupportedOperationException("TODO : Factory.endTurn()");
    }
}