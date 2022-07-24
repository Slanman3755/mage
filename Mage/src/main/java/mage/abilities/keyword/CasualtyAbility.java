package mage.abilities.keyword;

import mage.abilities.StaticAbility;
import mage.abilities.costs.common.CasualtyCost;
import mage.abilities.effects.common.InfoEffect;
import mage.cards.Card;
import mage.constants.Zone;

/**
 * @author TheElk801
 */
public class CasualtyAbility extends StaticAbility {

    public CasualtyAbility(Card card, int number) {
        super(Zone.ALL, new InfoEffect(
                "Casualty " + number + " <i>(As you cast this spell, " +
                        "you may sacrifice a creature with power " + number +
                        " or greater. When you do, copy this spell.)</i>"
        ));
        card.getSpellAbility().addCost(new CasualtyCost(number));
        this.setRuleAtTheTop(true);
    }

    private CasualtyAbility(final CasualtyAbility ability) {
        super(ability);
    }

    @Override
    public CasualtyAbility copy() {
        return new CasualtyAbility(this);
    }
}