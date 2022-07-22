package mage.abilities.effects.common;

import mage.constants.Outcome;

/**
 *
 * @author Slanman3755
 */
public class CasualtyEffect extends CopyTargetSpellEffect {
    private final int casualtyCost;

    public CasualtyEffect(int casualtyCost) {
        this(false, casualtyCost);
    }

    public CasualtyEffect(boolean useLKI, int casualtyCost) {
        this(false, useLKI, casualtyCost);
    }

    public CasualtyEffect(boolean useController, boolean useLKI, int casualtyCost) {
        this(useController, useLKI, true, casualtyCost);
    }

    public CasualtyEffect(boolean useController, boolean useLKI, boolean chooseTargets, int casualtyCost) {
        super(useController, useLKI, chooseTargets);
        this.casualtyCost = casualtyCost;
    }

    public CasualtyEffect(final CasualtyEffect effect) {
        super(effect);
        this.casualtyCost = effect.casualtyCost;
    }

}
