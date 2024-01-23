package life.light.apa.referentiel.dto.expression;

import life.light.apa.referentiel.dto.Produit;

public class NomExpression extends TerminalExpression {
    protected String nom;

    public NomExpression(String nom) {
        this.nom = nom;
    }

    @Override
    public boolean interpret(Produit context) {
        return context.getNom().equalsIgnoreCase(nom);
    }

    @Override
    public String toString() {
        return "NomExpression [nom=" + nom + "]";
    }
}
