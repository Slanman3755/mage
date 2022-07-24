package mage.abilities.costs.common;

import mage.abilities.Ability;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.effects.common.CopySourceSpellEffect;
import mage.constants.ComparisonType;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterControlledPermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public class CasualtyCost extends SacrificeTargetCost {

    public CasualtyCost(int number) {
        super(new TargetControlledPermanent(0, 1, makeFilter(number), true));
        this.text = "";
    }

    private CasualtyCost(final CasualtyCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        if (!super.pay(ability, game, source, controllerId, noMana, costToPay)) {
            return false;
        }
        if (!getPermanents().isEmpty()) {
            game.fireReflexiveTriggeredAbility(new ReflexiveTriggeredAbility(
                    new CopySourceSpellEffect(), false, "when you do, copy this spell"
            ), source);
        }
        return true;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        return true;
    }

    @Override
    public CasualtyCost copy() {
        return new CasualtyCost(this);
    }

    private static FilterControlledPermanent makeFilter(int number) {
        FilterControlledPermanent filter = new FilterControlledCreaturePermanent(
                "creature with power " + number + " or greater"
        );
        filter.add(new PowerPredicate(ComparisonType.MORE_THAN, number - 1));
        return filter;
    }
}
