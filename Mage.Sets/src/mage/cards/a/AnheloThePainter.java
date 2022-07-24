package mage.cards.a;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import mage.MageInt;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.CopyTargetSpellEffect;
import mage.abilities.keyword.CasualtyAbility;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.DeathtouchAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterInstantOrSorcerySpell;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.target.targetpointer.FixedTarget;
import mage.watchers.common.SpellsCastWatcher;

/**
 * Every work of art is a hit.
 * Based on Double Vision and Cut Your Losses
 *
 * @author Slanman3755
 */
public final class AnheloThePainter extends CardImpl {

    public AnheloThePainter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{U}{B}{R}");
        
        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Deathtouch
        this.addAbility(DeathtouchAbility.getInstance());

        // The first instant or sorcery spell you cast each turn has casualty 2.
        this.addAbility(new AnheloThePainterTriggeredAbility(), new SpellsCastWatcher());
    }

    private AnheloThePainter(final AnheloThePainter card) {
        super(card);
    }

    @Override
    public AnheloThePainter copy() {
        return new AnheloThePainter(this);
    }
}

class AnheloThePainterTriggeredAbility extends SpellCastControllerTriggeredAbility {

    AnheloThePainterTriggeredAbility() {
        super(null,false);
    }

    AnheloThePainterTriggeredAbility(AnheloThePainterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AnheloThePainterTriggeredAbility copy() {
        return new AnheloThePainterTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (super.checkTrigger(event, game)) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (isFirstInstantOrSorceryCastByPlayerOnTurn(spell, game)) {
                spell.addAbility(new CasualtyAbility(spell, 2));
                return true;
            }
        }
        return false;
    }

    private boolean isFirstInstantOrSorceryCastByPlayerOnTurn(Spell spell, Game game) {
        if (spell != null) {
            SpellsCastWatcher watcher = game.getState().getWatcher(SpellsCastWatcher.class);
            if (watcher != null) {
                List<Spell> eligibleSpells = watcher.getSpellsCastThisTurn(this.getControllerId())
                        .stream()
                        .filter(spell1 -> spell1.isInstantOrSorcery(game))
                        .collect(Collectors.toList());
                return eligibleSpells.size() == 1 && eligibleSpells.get(0).getId().equals(spell.getId());
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "The first instant or sorcery spell you cast each turn has casualty 2.";
    }
}
