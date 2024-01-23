package life.light.apa.referentiel.dto.expression;

import life.light.apa.referentiel.dto.Produit;

public interface Expression {

    boolean interpret(Produit context);

}
